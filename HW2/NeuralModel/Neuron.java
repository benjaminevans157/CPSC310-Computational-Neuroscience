import java.awt.Color;
import java.util.ArrayList;

public class Neuron {

	

	Color c = Color.GRAY;
	double cellType = 0;
	double xOrigin = 0;
	double yOrigin = 0;

	//graph the stellate cell
	public void stellate(double xorigin, double yorigin, SwingGraphics grapher) {
		ArrayList<double[]> segments = new ArrayList<double[]>();
		double[] xsegment1 = { xorigin, xorigin + .03 };
		double[] ysegment1 = { yorigin, yorigin + .02 };

		double[] xsegment2 = { xorigin, xorigin - .04 };
		double[] ysegment2 = { yorigin, yorigin + .03 };

		double[] xsegment3 = { xorigin, xorigin + .01 };
		double[] ysegment3 = { yorigin, yorigin + .03 };

		double[] xsegment4 = { xorigin, xorigin + .02 };
		double[] ysegment4 = { yorigin, yorigin + .03 };

		double[] xsegment5 = { xorigin, xorigin - .04 };
		double[] ysegment5 = { yorigin, yorigin - .03 };

		double[] xsegment6 = { xorigin, xorigin };
		double[] ysegment6 = { yorigin, yorigin + .05 };
		
		double[] xsegment7 = { xorigin, xorigin +.01 };
		double[] ysegment7 = { yorigin, yorigin - .05 };

		grapher.graph(xsegment1, ysegment1, Color.GRAY);
        grapher.graph(xsegment2, ysegment2, Color.GRAY);
        grapher.graph(xsegment3, ysegment3, Color.GRAY);
        grapher.graph(xsegment4, ysegment4, Color.GRAY);
        grapher.graph(xsegment5, ysegment5, Color.GRAY);
        grapher.graph(xsegment6, ysegment6, Color.GRAY);
        grapher.graph(xsegment7, ysegment7, Color.GRAY);
        
	}

	//graph the pyramidal cell
	public void pyramidal(double xorigin, double yorigin, SwingGraphics grapher, double rotation, int num) {
		
		if (num == 3 || num == 9 || num == 13 || num == 14) {
			//rotation is correct
			
		} else {
		 rotation += Math.PI;
		}
		 double[] xsegment1 = { xorigin, (xorigin) };
	        double[] ysegment1 = { yorigin, (yorigin + .15) };
	        rotateSegment(xsegment1, ysegment1, xorigin, yorigin, rotation);

	        double[] xsegment2 = { (xorigin -0.03), (xorigin + .001) };
	        double[] ysegment2 = { yorigin, yorigin + .02 };
	        rotateSegment(xsegment2, ysegment2, xorigin, yorigin, rotation);

	        double[] xsegment3 = { xorigin, xorigin- 0.02};
	        double[] ysegment3 = { yorigin+.04, yorigin +0.05};
	        rotateSegment(xsegment3, ysegment3, xorigin, yorigin, rotation);

	        double[] xsegment4 = { xorigin-0.02, xorigin -0.028 };
	        double[] ysegment4 = { yorigin+0.05, yorigin  +.16};
	        rotateSegment(xsegment4, ysegment4, xorigin, yorigin, rotation);

	        double[] xsegment5 = { xorigin-0.02, xorigin -.015 };
	        double[] ysegment5 = { yorigin+0.05, yorigin +0.13 };
	        rotateSegment(xsegment5, ysegment5, xorigin, yorigin, rotation);

	        double[] xsegment6 = { xorigin, xorigin+0.025 };
	        double[] ysegment6 = { yorigin+0.04, yorigin+0.04 };
	        rotateSegment(xsegment6, ysegment6, xorigin, yorigin, rotation);

	        double[] xsegment7 = { xorigin, xorigin+.02 };
	        double[] ysegment7 = { yorigin+0.125, yorigin + .14 };
	        rotateSegment(xsegment7, ysegment7, xorigin, yorigin, rotation);
		
		grapher.graph(xsegment1, ysegment1, Color.GRAY);
        grapher.graph(xsegment2, ysegment2, Color.GRAY);
        grapher.graph(xsegment3, ysegment3, Color.GRAY);
        grapher.graph(xsegment4, ysegment4, Color.GRAY);
        grapher.graph(xsegment5, ysegment5, Color.GRAY);
        grapher.graph(xsegment6, ysegment6, Color.GRAY);
        grapher.graph(xsegment7, ysegment7, Color.GRAY);
	
	}
	//rotate the pyramidal cell segments
	private void rotateSegment(double[] x, double[] y, double xorigin, double yorigin, double angle) {
        for (int i = 0; i < x.length; i++) {
            double dx = x[i] - xorigin;
            double dy = y[i] - yorigin;
            x[i] = dx * Math.cos(angle) - dy * Math.sin(angle) + xorigin;
            y[i] = dx * Math.sin(angle) + dy * Math.cos(angle) + yorigin;
        }
}}
