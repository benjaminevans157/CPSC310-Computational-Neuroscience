package Q2;
// Chris Fietkiewicz. Hodgkin-Huxley model of a neuron.
// NOTE: Requires SwingGraphics.java for graphing.
import java.awt.*;
import java.util.Scanner;

public class HH_NeuronStarter {
    double dt;

    // Constants
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

    // Constructor
    public HH_NeuronStarter(double dt) {
        this.dt = dt;
    }

    // Returns new membrane potential after a simulated time step.
    // Receives a stimulus as a voltage which is added directly to the membrane potential.
    public double calculateNextTimeStep(double stimulus) {
        // Potassium current
        double alphan = 0.01 * (v + 55) / (1 - Math.exp(-(v + 55) / 10));
        double betan = 0.125 * Math.exp(-(v + 65) / 80);

        // Sodium current
        double alpham = 0.1 * (v + 40) / (1 - Math.exp(-(v + 40) / 10));
        double betam = 4 * Math.exp(-(v + 65) / 18);
        double alphah = 0.07 * Math.exp(-(v + 65) / 20);
        double betah = 1 / (1 + Math.exp(-(v + 35) / 10));

        // Differential equations
        double dn = (alphan * (1 - n) - betan * n);
        double dm = (alpham * (1 - m) - betam * m);
        double dh = (alphah * (1 - h) - betah * h);
        double dv = (-gBarNa * Math.pow(m, 3) * h * (v - eNa) - gBarK * Math.pow(n, 4) * (v - eK) - gM * (v - vRest) + stimulus);

        // Euler integration updates
        n = n + dn * dt;
        m = m + dm * dt;
        h = h + dh * dt;
        v = v + dv * dt;

        return v;
    }

    public double getdt() {
        return dt;
    }

    public static void simulate(double dt, int N, double[] t, double[] Vm, double stimAmplitude, double stimDuration) {
        HH_NeuronStarter n = new HH_NeuronStarter(dt); // Create a neuron
        double firstMsec = 0.5; // Time for 1st stimulus
        int first = (int) Math.round(firstMsec / n.getdt());
        int intStimDuration = (int) Math.round(stimDuration / n.getdt()) - 1;

        System.out.println("Simulation settings: amplitude = " + stimAmplitude + ", dt = " + dt + " msec...");
        boolean apOccurred = false;
        for (int i = 0; i < N; i++) {
            t[i] = i * n.getdt();
            if (i >= first && i <= (first + intStimDuration)) {
                Vm[i] = n.calculateNextTimeStep(stimAmplitude);
                if (Vm[i] >= 0) {
                    apOccurred = true;
                }
            } else {
                Vm[i] = n.calculateNextTimeStep(0.0);
            }
        }
        if (apOccurred) {
            System.out.println("Action potential occurred at amplitude: " + stimAmplitude);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter starting amplitude: ");
        double startAmplitude = scanner.nextDouble();
        double dt = 0.01; // Time step duration
        double duration = 10.0; // Simulation duration
        double stimDuration = 0.1; // Stimulus duration
        int N = (int) (duration / dt);
        SwingGraphics grapher = new SwingGraphics(); // Begin graphing

        double threshold = -1;

        for (int i = 0; i < 10; i++) {
            double stimAmplitude = startAmplitude + i;
            double[] t = new double[N];
            double[] Vm = new double[N];
            simulate(dt, N, t, Vm, stimAmplitude, stimDuration);
            grapher.graph(t, Vm, Color.getHSBColor(i / 10.0f, 1, 1));

            if (threshold == -1 && Vm[N - 1] >= 0) {
                threshold = stimAmplitude;
            }
        }

        if (threshold != -1) {
            System.out.println("Input threshold for an action potential: " + threshold);
        } else {
            System.out.println("No stimulus generated an action potential.");
        }

        grapher.display();
    }
}
