package q3;
// ArrayList version of SwingGraphics.

// Swing-based 2-D graphics program. Does not require JavaFX.
// Written by Ian Waldschmidt at Case Western Reserve University
// and Chris Fietkiewicz at Hobart and William Smith Colleges. The "graph" method
// receives arrays for x-axis and y-axis values, plus a line color. You can graph
// multilple lines and then call the "display" method once to display all of the lines.
// A main() method is provided for demonstration, but the methods are public and can
// be used in other programs by declaring an instance of the Grapher class and calling
// the graph() and display() methods in the other program.
// Notes: (1) "graph" must be called for all data before "display".
//        (2) import java.awt.* to use the Color class in other programs.

import java.util.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import java.text.*; // For DecimalFormat
import java.util.ArrayList;

public class SwingGraphicsArrayList extends JPanel {
	JFrame f = new JFrame();
	private ArrayList<ArrayList<Double>> xdatas = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> ydatas = new ArrayList<ArrayList<Double>>();
	private ArrayList<Color> colors = new ArrayList<Color>();

	final int PAD = 30;

	public void graph(ArrayList<Double> xdata, ArrayList<Double> ydata, Color color) {
		if (xdata.size() != ydata.size()) {
			System.out.println("Input arrays must be the same length");
			return;
		}

		this.xdatas.add(xdata);
		this.ydatas.add(ydata);
		this.colors.add(color);
	}

	public void display() {
		f.add(this);
		f.setSize(1000, 600);
		f.setLocation(100, 100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure that program terminates when window is closed
		f.setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth();
		int h = getHeight();
		// Draw ordinate
		g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
		// Draw abcissa
		g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
		// Draw labels
		Font font = g2.getFont();
		FontRenderContext frc = g2.getFontRenderContext();
		LineMetrics lm = font.getLineMetrics("0", frc);
		float sh = lm.getAscent() + lm.getDescent();

		// Draw data
		double xMin = getMin(xdatas);
		double yMin = getMin(ydatas);
		double xRange = getMax(xdatas) - xMin;
		double yRange = getMax(ydatas) - yMin;
		double xScale = (double) (w - 2 * PAD) / Math.abs(xRange);
		double yScale = (double) (h - 2 * PAD) / Math.abs(yRange);
		// System.out.println(xMin + ", " + yMin + ", " + xRange + ", " + yRange + ", "
		// + xScale + ", " + yScale);
		for (int j = 0; j < xdatas.size(); j++) {
			ArrayList<Double> xdata = this.xdatas.get(j);
			ArrayList<Double> ydata = this.ydatas.get(j);
			Color color = this.colors.get(j);
			// Draw lines for each data set
			g2.setPaint(color);
			for (int i = 0; i < xdata.size() - 1; i++) {
				double x1 = PAD + xScale * (xdata.get(i) - xMin);
				double y1 = h - PAD - yScale * (ydata.get(i) - yMin);
				double x2 = PAD + xScale * (xdata.get(i + 1) - xMin);
				double y2 = h - PAD - yScale * (ydata.get(i + 1) - yMin);
				g2.draw(new Line2D.Double(x1, y1, x2, y2));
			}
			// Draw points for each data set
			g2.setPaint(color);
			for (int i = 0; i < xdata.size(); i++) {
				double x = PAD + xScale * (xdata.get(i) - xMin);
				double y = h - PAD - yScale * (ydata.get(i) - yMin);
				g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
			}
		}

		// Write x axis values
		g2.setPaint(Color.BLACK);
		double[] xaxis = new double[5];
		xaxis[0] = getMin(xdatas);
		xaxis[4] = getMax(xdatas);
		xaxis[1] = xRange / 4 + xaxis[0];
		xaxis[2] = xRange / 2 + xaxis[0];
		xaxis[3] = xRange * 3 / 4 + xaxis[0];
		int xcoord = PAD - 4;
		int ycoord = h - (PAD / 4);
		for (int i = 0; i < 5; i++) {
			DecimalFormat form = new DecimalFormat("0.#");
			String value = form.format(xaxis[i]) + "";
			g2.drawString(value, xcoord, ycoord);
			xcoord += (w - PAD) / 4 - PAD + 22;
		}

		// Write y axis values
		double[] yaxis = new double[5];
		yaxis[0] = getMin(ydatas);
		yaxis[4] = getMax(ydatas);
		yaxis[1] = yRange / 4 + yaxis[0];
		yaxis[2] = yRange / 2 + yaxis[0];
		yaxis[3] = yRange * 3 / 4 + yaxis[0];
		xcoord = PAD / 4;
		ycoord = h - PAD + 4;
		for (int i = 0; i < 5; i++) {
			DecimalFormat form = new DecimalFormat("0.#");
			String value = form.format(yaxis[i]) + "";
			g2.drawString(value, xcoord, ycoord);
			ycoord -= (h - PAD) / 4 - PAD + 22;
		}
	}

	private double getMin(ArrayList<ArrayList<Double>> data) {
		double min = Integer.MAX_VALUE;
		for (int j = 0; j < data.size(); j++) {
			for (int i = 0; i < data.get(j).size(); i++) {
				if (data.get(j).get(i) < min) {
					min = data.get(j).get(i);
				}
			}
		}
		return min;
	}

	private double getMax(ArrayList<ArrayList<Double>> data) {
		double max = -Integer.MAX_VALUE;
		for (int j = 0; j < data.size(); j++) {
			for (int i = 0; i < data.get(j).size(); i++) {
				if (data.get(j).get(i) > max) {
					max = data.get(j).get(i);
				}
			}
		}
		return max;
	}

	public static void main(String[] args) throws IOException {
	    SwingGraphicsArrayList grapher = new SwingGraphicsArrayList(); // Create single Grapher for all lines

	    FileInputStream fileInputStream = new FileInputStream("outputBinary.bin");
	    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
	    DataInputStream dis = new DataInputStream(bufferedInputStream);

	    ArrayList<Double> timeData = new ArrayList<>();
	    ArrayList<Double> vm0Data = new ArrayList<>();
	    ArrayList<Double> vm1Data = new ArrayList<>(); // Create a separate arraylist for Vm1 data

	    while (true) {
	        try {
	            timeData.add(dis.readDouble());
	            vm0Data.add(dis.readDouble());
	            vm1Data.add(dis.readDouble());
	        } catch (EOFException e) {
	            break;
	        }
	    }

	    dis.close();

	    grapher.graph(timeData, vm0Data, Color.BLUE);
	    grapher.graph(timeData, vm1Data, Color.RED); // Graph Vm1 data separately

	    grapher.display();
	}
}
