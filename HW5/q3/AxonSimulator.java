package q3;
// Authors: Chris Fietkiewicz and Albright Dwarka. Requires SwingGraphics.java.
import java.awt.Color;

public class AxonSimulator {
	public static void main(String[] args) {
		int numNodes = 3; // Number of nodes in the simulation, will add myelin between each node
		double dt = 0.01; // Integration step size (msec)
		double duration = 10; // Duration of entire simulation (msec)
		double stimDuration = 0.5; // Duration of both stimuli (msec)
		double delay = 0.1; // Start time of both stimuli (msec)
		Axon axon = new Axon(numNodes, dt); // create axon

		// Experiment setup
		double intStimAmplitude = 20; // For internal (intracellular) stimulus
		double extStimAmplitude = 0; // For external (extracellular) stimulus
		int N = (int) (duration / dt);
		double[] t = new double[N];
		double[][] data = new double[axon.numCompartments][N];

		// Run simulation
		simulate(axon, dt, N, t, data, intStimAmplitude, extStimAmplitude, stimDuration, delay);

		// Graph results
		SwingGraphics grapher = new SwingGraphics(); // Create single Grapher for all lines
		grapher.graph(t, data[0], Color.GREEN);
		grapher.graph(t, data[1], Color.BLUE);
		grapher.graph(t, data[2], Color.RED);
		grapher.display();
	}

	// Method to run simulation
	public static void simulate(Axon axon, double dt, int N, double[] t, double[][] data, double intStimulus,
			double extStimulus, double stimDuration, double delay) {
		int intDelay = (int) Math.round(delay / dt);
		int first = (int) Math.round(delay / dt);
		int intStimDuration = (int) Math.round(stimDuration / dt) - 1; // Integer stimulus duration (in steps)
		System.out.println("Running...");
		// Main simulation loop
		for (int i = 0; i < N; i++) {
			t[i] = i * dt;
			// Calculate variable changes (dv) for all nodes.
			for (int j = 0; j < axon.numCompartments; j++) {
				// If stimuli are active...
				if (i >= first && i <= (first + intStimDuration)) {
					axon.getNode(j).calculate_dv(intStimulus, extStimulus);
				// ... otherwise, stimuli are not active (use zero amplitude)
				} else {
					axon.getNode(j).calculate_dv(0.0, 0.0);
				}
			}

			// Update state variables for all nodes
			for (int j = 0; j < axon.numCompartments; j++) {
				data[j][i] = axon.getNode(j).updateStateVariables();
			}
		}
	}
}

// Axon class: A group of NodeCompartments and MyelinCompartments connected in an altenating sequence.
class Axon {
	int intStimNodeIndex = 0; // Index of node that receives intracellular stimulus
	double electrodeX = 0; // x-coordinate of external electrode, relative to left-most node (millimeters)
	double electrodeY = 1; // y-coordinate of external electrode, relative to horizontal axon (millimeters)
	double internodalLength = 1; // Distance between nodes (millimeters)
	double Rext = 100; // Extracellular resistance (ohms)
	double Rint = 15; // Internodal (axial) resistance (ohms)
	double Ga = 1 / Rint; // Internodal (axial) conductance
	NodeCompartment[] allNodes;
	int numCompartments;

	// Creates an axon with the specifed number of nodes and myelin between them and
	// initialize the compartments
	public Axon(int numCompartments, double dt) {
		this.numCompartments = numCompartments;
		allNodes = new NodeCompartment[numCompartments];
		for (int i = 0; i < numCompartments; i++)
			allNodes[i] = new NodeCompartment(dt, i, this);
	}

	// Given a compartment Index, finds the node with the corresponding index. 
	public NodeCompartment getNode(int compartmentIndex) {
		for (int i = 0; i < allNodes.length; i++) {
			if (allNodes[i].compartmentIndex == compartmentIndex) {
				return allNodes[i];
			}
		}
		return null;
	}
}

// Class for a "node of Ranvier" that is a standard Hodgkin-Huxley model with the addition of axial currents
class NodeCompartment {
	double dt;
	double VeCoefficient; // Multiplier value for calculating extracellular voltage (Re / (4 * pi * r))
	int compartmentIndex; // Index of node in axon, relative to left-most node
	int leftIndex;
	int rightIndex;
	Axon axon; // Axon which contains this node

	// Constants
	double C = 1; // Capacitance
	double gBarK = 36; // mS/cm^2
	double gBarNa = 120; // mS/cm^2
	double gM = 0.3; // mS/cm^2
	double eK = -77; // mV
	double eNa = 50; // mV
	double vRest = -54.4; // mV

	// Initial conditions
	double v = -65;
	double n = 0.3177;
	double m = 0.0529;
	double h = 0.5961;

	// Calculated changes to state variables.
	double dv, dn, dm, dh;

	// Constructor
	public NodeCompartment(double dt, int nodeIndex, Axon axon) {
		this.dt = dt;
		this.leftIndex = nodeIndex - 1;
		this.compartmentIndex = nodeIndex;
		this.rightIndex = nodeIndex + 1;
		this.axon = axon;
		double r = Math.sqrt(Math.pow(Math.abs(axon.electrodeX - nodeIndex * axon.internodalLength), 2)
				+ Math.pow(axon.electrodeY, 2));
		VeCoefficient = axon.Rext / (4 * Math.PI * r);
	}

	// Calculates variable changes (dv) after a simulated time step.
	// Receives a stimulus as a voltage which is added directly to membrane
	// potential.
	public void calculate_dv(double intStimulus, double extStimulus) {
		// Potassium current
		double alphan = 0.01 * (v + 55) / (1 - Math.exp(-(v + 55) / 10));
		double betan = 0.125 * Math.exp(-(v + 65) / 80);

		// Sodium current
		double alpham = 0.1 * (v + 40) / (1 - Math.exp(-(v + 40) / 10));
		double betam = 4 * Math.exp(-(v + 65) / 18);
		double alphah = 0.07 * Math.exp(-(v + 65) / 20);
		double betah = 1 / (1 + Math.exp(-(v + 35) / 10));

		// Calculate axial current
		double Iaxial; // Internodal (axial) current
		double Ve = VeCoefficient * extStimulus; // This node's Extracellular voltage
		// Check for left-most node case
		if (compartmentIndex == 0) {
			double vRight = axon.getNode(rightIndex).v; // Voltage of right node
			double VeRight = axon.getNode(rightIndex).VeCoefficient * extStimulus; // Extracellular voltage of right node
			Iaxial = axon.Ga * (-v + vRight - Ve + VeRight);
		}
		// Check for right-most node case
		else if (compartmentIndex == axon.numCompartments - 1) {
			double vLeft = axon.getNode(leftIndex).v; // Voltage of right node
			double VeLeft = axon.getNode(leftIndex).VeCoefficient * extStimulus; // Extracellular voltage of right node
			Iaxial = axon.Ga * (vLeft - v + VeLeft - Ve);
		}
		// All other nodes
		else {
			double vRight = axon.getNode(rightIndex).v; // Voltage of right node
			double VeRight = axon.getNode(rightIndex).VeCoefficient * extStimulus; // Extracellular voltage of
																						// right node
			double vLeft = axon.getNode(leftIndex).v; // Voltage of right node
			double VeLeft = axon.getNode(leftIndex).VeCoefficient * extStimulus; // Extracellular voltage of right
																					// node
			Iaxial = axon.Ga * (vLeft - 2 * v + vRight + VeLeft - 2 * Ve + VeRight);
		}

		// Differential equations
		dn = (alphan * (1 - n) - betan * n);
		dm = (alpham * (1 - m) - betam * m);
		dh = (alphah * (1 - h) - betah * h);
		double INa = gBarNa * Math.pow(m, 3) * h * (v - eNa);
		double IK = gBarK * Math.pow(n, 4) * (v - eK);
		double Ileak = gM * (v - vRest);
		if (compartmentIndex == axon.intStimNodeIndex) // If this node gets intracellular stimulus...
			dv = (-INa - IK - Ileak + Iaxial + intStimulus) / C;
		else
			dv = (-INa - IK - Ileak + Iaxial) / C;
	}

	// Returns new membrane potential after updating state variables.
	// Should be executed after dv has been calculated for *all* nodes.
	public double updateStateVariables() {
		// Euler integration updates
		n = n + dn * dt;
		m = m + dm * dt;
		h = h + dh * dt;
		v = v + dv * dt;
		return v;
	}

	// Return integration step size if desired
	public double getdt() {
		return dt;
	}
}
