// V 2.0: Changed to work with exact solution given in instructions.
//        (1) Removed injected current. (2) Set V0 to 1.5 V.
// Chris Fietkiewicz. Models a capacitor. Starter code for assignment.
// NOTE: Requires SwingGraphics.java for graphing.
import java.awt.*;

public class CapacitorStarter2 {
	private double V; // Voltage (potential) at any moment
	private double R; // Resistance
	private double C; // Capacitance
	private double dt; // Integration time step
	
	// Constructor for initialization
	public CapacitorStarter2(double V0, double R, double C, double dt) {
		V = V0; // Initial voltage
		this.R = R;
		this.C = C;
		this.dt = dt;
	}
	
	// Method to update voltage for next time step
	public double calculateNextTimeStep() {
		double dV = (-V / (R * C)) * dt; // Compute change in potential
		V = V + dV; // Update V
		return V; // When sub-threshold, just show the new potential
	}
	
	// Get dt value. Can be used when calculating exact times.
	public double getdt() {
		return dt;
	}
	
	// Main method that runs the simulation
	public static void main(String[] args) {
		// Simulation
		double V0 = 1.5, R = 12600, C = 0.0001, dt = 0.001; // Neuron properties
		CapacitorStarter2 c1 = new CapacitorStarter2(V0, R, C, dt); // Create a single capacitor
		int N = 10000; // Number of integration time steps
        double[] t1 = new double[N]; // Array for time values
        double[] V1 = new double[N]; // Array for voltage values
		System.out.println("Running simulation using: V0 = " + V0 + " V, R = " + R + " ohms, C = " + C + " farads, dt = " + dt + " sec...");
		t1[0] = 0; // t = 0
		V1[0] = V0; // Initial voltage at t = 0
		// Loop through integration time steps
		for (int i = 1; i < N; i++) {
			t1[i] = i * c1.getdt(); // Get exact time
			V1[i] = c1.calculateNextTimeStep(); // Udpate and store voltage approximation
		}
		dt = 0.5;
		N = 20;
		double[] t2 = new double[N]; // Array for time values
        double[] V2 = new double[N]; // Array for voltage values
        t2[0] = 0;
        V2[0] = 0;		
		CapacitorStarter2 c2 = new CapacitorStarter2(V0, R, C, dt); // Create a single capacitor
		for (int i = 1; i < N; i++) {
			t2[i] = i * c2.getdt();
			V2[i] = c2.calculateNextTimeStep();
		}
		
		dt = 0.1;
		N = 100;
		double[] t3 = new double[N]; // Array for time values
        double[] V3 = new double[N]; // Array for voltage values
        t3[0] = 0;
        V3[0] = 0;		
		CapacitorStarter2 c3 = new CapacitorStarter2(V0, R, C, dt); // Create a single capacitor
		for (int i = 1; i < N; i++) {
			t3[i] = i * c3.getdt();
			V3[i] = c3.calculateNextTimeStep();
		}
		N = 100;
		double[] t4 = new double[N]; // Array for time values
        double[] V4 = new double[N]; // Array for voltage values
		t4[0] = 0;
		V4[0] = 0;
		for (int i = 1; i < N; i++) {
			t4[i] = i * c3.getdt();
			V4[i] = (V0 * Math.exp(-t4[i] / (R * C)));
		}
		// Graphing results
        SwingGraphics grapher = new SwingGraphics(); // Create single Grapher for all lines
        grapher.graph(t1, V1, Color.BLUE); // Prepare graph
        grapher.graph(t2, V2, Color.RED); // Prepare graph
        grapher.graph(t3, V3, Color.GREEN); // Prepare graph
        grapher.graph(t4, V4, Color.YELLOW); // Prepare graph
        grapher.display();                 // Display graph (do this only once)
	}
}
