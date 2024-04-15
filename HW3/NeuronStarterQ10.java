// Chris Fietkiewicz. Starter code for modeling a simple neuron with capacitance, similar to an "integrate and fire" model.
// NOTE: Requires SwingGraphics.java for graphing.
import java.awt.*;

public class NeuronStarter {
	private double threshold = -59.0; // Minimum voltage for action potential (mV)
	private static double restingPotential = -65; // Resting voltage (mV)
	private double V = restingPotential; // Membrane voltage at any moment (mV)
	private double R = 5; // Resistance (ohms/cm^2)
	private double C = 1.0; // Capacitance (pF)
	private double dt = 0.1; // Integration time step
	static double stimulusDuration = 0.5; // Stimulus duration in ms
    static double timeBetweenPulses = 1.0; // Time between pulses in ms
    
	// Returns new membrane voltage after a simulated time step.
	// Receives a stimulus as a voltage which is added directly to membrane voltage.
	public double calculateNextTimeStep(double stimulus) {
		// Compute new membrane voltage
		double dV = (-V  + restingPotential + stimulus) / (R * C) * dt;
		V = V + dV; // Discharge towards resting voltage
		
		
		// Stimulus case: check for action potential (above threshold)
		 if (V >= threshold) {
		        V = restingPotential; // Set voltage to resting potential
		        return 30.0; // Return peak value of 30 mV
		    } else {
		        return V; // When sub-threshold, just show the new voltage
		    }
	}
	
	// Get dt value. Can be used when calculating exact times.
	public double getdt() {
		return dt;
	}
	
	// Main method that runs the simulation
	public static void main(String[] args) {
		int N = 70; // Number of integration time steps for each simulation
		
		// Simulation example with multiple stimuli
		NeuronStarter n1 = new NeuronStarter(); // Create a single neuron
        double[] t1 = new double[N];
        double[] Vm1 = new double[N];
		double amplitude = 1.5; // Stimulus amplitude
		int num = 0; // number of stimuli
		double timeSinceLastStimulus = Double.POSITIVE_INFINITY; // time since the last stimulus
	    double pulseDuration = 0.5; // duration of each stimulus pulse in ms
	    double timeBetweenPulses = 1.5; // time between pulses in ms
	    double stimulusThreshold = 1.5; // mV increase in voltage per pulse
	    
	    System.out.println("Simulation using amplitude = " + amplitude + "...");
	    for (int i = 0; i < N; i++) {
	        t1[i] = i * n1.getdt();
	        // Set stimulus amplitude based on time
	        if (t1[i] >= 0 && t1[i] <= 0.5 || t1[i] >= 1 && t1[i] <= 1.5 || t1[i] >= 2 && t1[i] <= 2.5) {
	            amplitude = 21.2;
	        } else {
	            amplitude = 0.0;
	        }
	        if (t1[i] >= 5) {
	            amplitude = 0.0;
	        }
	        Vm1[i] = n1.calculateNextTimeStep(amplitude);
	   
			System.out.println("t1[i] "+t1[i] + " V1[i]: " + Vm1[i]);
	    
		}

        NeuronStarter n2 = new NeuronStarter(); // Create a second neuron
        double[] t2 = new double[N];
        double[] Vm2 = new double[N];
        double amplitude2 = 2.1; // Stimulus amplitude for neuron 2
        System.out.println("Simulation using amplitude = " + amplitude2 + "...");
        for (int i = 0; i < N; i++) {
            t2[i] = i * n2.getdt();
            if (t2[i] >= 0 && t2[i] <= 0.5 || t2[i] >= 1 && t2[i] <= 1.5 || t2[i] >= 2 && t2[i] <= 2.5) {
	            amplitude = 21.1;
	        } else {
	            amplitude = 0.0;
	        }
	        if (t1[i] >= 5) {
	            amplitude = 0.0;
	        }
            Vm2[i] = n2.calculateNextTimeStep(amplitude2);
        }
        SwingGraphics grapher = new SwingGraphics(); // For graphing
        grapher.graph(t1, Vm1, Color.BLUE); // Graph neuron #1
        grapher.graph(t2, Vm2, Color.RED); // Graph neuron #2
        grapher.display();
	}
}
