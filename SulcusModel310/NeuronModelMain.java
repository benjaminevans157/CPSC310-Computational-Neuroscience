//Author: Benjamin Evans

import java.awt.Color; // Required for color settings
import java.awt.geom.AffineTransform;

public class NeuronModelMain {
    public static void main(String[] args){
        SwingGraphics grapher = new SwingGraphics(); // Create single Grapher for all lines
        Neuron neuron = new Neuron();
        
        //sulcus siding
        double[] xsegment1 = {.9,0};
        double[] ysegment1 = {0.5,1};
        
        double[] xsegment2 = {1.2,.9};
        double[] ysegment2 = {1.5,0.5};
        
        double[] xsegment3 = {.4,1.2};
        double[] ysegment3 = {2,1.5};
        
        double[] xsegment4 = {.6,.4};
        double[] ysegment4 = {2.4,2};
        
        double[] xsegment5 = {.9,.6};
        double[] ysegment5 = {2.2,2.4};
        
        double[] xsegment6 = {1.2,.9};
        double[] ysegment6 = {2.4,2.2};
        
        double[] xsegment7 = {1.7,1.2};
        double[] ysegment7 = {.2,2.4};
        
        double[] xsegment8 = {2,1.7};
        double[] ysegment8 = {1.5,.2};
        
        double[] xsegment9 = {1.7,2};
        double[] ysegment9 = {2,1.5};
        
        double[] xsegment10 = {1.9,1.7};
        double[] ysegment10 = {2.2,2};
        
        double[] xsegment11 = {2.6,1.9};
        double[] ysegment11 = {1.8,2.2};
        
        double[] xsegment12 = {3,2.6};
        double[] ysegment12 = {2.2,1.8};
        
        double[] xsegment13 = {3,2.2};
        double[] ysegment13 = {1,0};
       
        //sulcus middle
        double[] xmiddle1 = {3,0};
        double[] ymiddle1 = {0,0};
        
        double[] xmiddle2 = {.8,0};
        double[] ymiddle2 = {0,.8};
        
        double[] xmiddle3 = {1.35,.8};
        double[] ymiddle3 = {1.2,0};
        
        double[] xmiddle4 = {1.2,1.35};
        double[] ymiddle4 = {2,1.2};
        
        double[] xmiddle5 = {.6,1.24};
        double[] ymiddle5 = {2.2,1.8};
        
        double[] xmiddle6 = {2.4,1.8};
        double[] ymiddle6 = {1.4, 0};
        
        double[] xmiddle7 = {1.9,2.4};
        double[] ymiddle7 = {2,1.4};
        
        double[] xmiddle8 = {2.8,2.4};
        double[] ymiddle8 = {1.6,1.4};
        
        // Prepare all graphics
        grapher.graph(xsegment1, ysegment1, Color.BLACK);
        grapher.graph(xsegment2, ysegment2, Color.BLACK);
        grapher.graph(xsegment3, ysegment3, Color.BLACK);
        grapher.graph(xsegment4, ysegment4, Color.BLACK);
        grapher.graph(xsegment5, ysegment5, Color.BLACK);
        grapher.graph(xsegment6, ysegment6, Color.BLACK);
        grapher.graph(xsegment7, ysegment7, Color.BLACK);
        grapher.graph(xsegment8, ysegment8, Color.BLACK);
        grapher.graph(xsegment9, ysegment9, Color.BLACK);
        grapher.graph(xsegment10, ysegment10, Color.BLACK);
        grapher.graph(xsegment11, ysegment11, Color.BLACK);
        grapher.graph(xsegment12, ysegment12, Color.BLACK);
        grapher.graph(xsegment13, ysegment13, Color.BLACK);

        grapher.graph(xmiddle1, ymiddle1, Color.GREEN);
        grapher.graph(xmiddle2, ymiddle2, Color.GREEN);
        grapher.graph(xmiddle3, ymiddle3, Color.GREEN);
        grapher.graph(xmiddle4, ymiddle4, Color.GREEN);
        grapher.graph(xmiddle5, ymiddle5, Color.GREEN);
        grapher.graph(xmiddle6, ymiddle6, Color.GREEN);
        grapher.graph(xmiddle7, ymiddle7, Color.GREEN);
        grapher.graph(xmiddle8, ymiddle8, Color.GREEN);

        //Graph the stellate cell
        neuron.stellate(1, .6, grapher);
        neuron.stellate(0.2, .5, grapher);
        neuron.stellate(.5, .2, grapher);
        neuron.stellate(.8, 2.2, grapher);
        neuron.stellate(1.4, .5, grapher);
        neuron.stellate(2.3, 1.8, grapher);
        neuron.stellate(1.95, .9, grapher);
        neuron.stellate(2.3, .7, grapher);
        neuron.stellate(2.65, 1.3, grapher);
       
        //calculate the slope and intercept, then graph the pyramidal cells on the outside segments
        calculateSlopeAndIntercept(xsegment1[0], ysegment1[0], xsegment1[1], ysegment1[1], grapher, 1);        
        calculateSlopeAndIntercept(xsegment2[0], ysegment2[0], xsegment2[1], ysegment2[1], grapher, 2);        
        calculateSlopeAndIntercept(xsegment3[0], ysegment3[0], xsegment3[1], ysegment3[1], grapher, 3);
        calculateSlopeAndIntercept(xsegment4[0], ysegment4[0], xsegment4[1], ysegment4[1], grapher, 4);
        calculateSlopeAndIntercept(xsegment5[0], ysegment5[0], xsegment5[1], ysegment5[1], grapher, 5);
        calculateSlopeAndIntercept(xsegment6[0], ysegment6[0], xsegment6[1], ysegment6[1], grapher, 6);
        calculateSlopeAndIntercept(xsegment7[0], ysegment7[0], xsegment7[1], ysegment7[1], grapher, 7);
        calculateSlopeAndIntercept(xsegment8[0], ysegment8[0], xsegment8[1], ysegment8[1], grapher, 8);
        calculateSlopeAndIntercept(xsegment9[0], ysegment9[0], xsegment9[1], ysegment9[1], grapher, 9);
        calculateSlopeAndIntercept(xsegment10[0], ysegment10[0], xsegment10[1], ysegment10[1], grapher, 10);
        calculateSlopeAndIntercept(xsegment11[0], ysegment11[0], xsegment11[1], ysegment11[1], grapher, 11);
        calculateSlopeAndIntercept(xsegment12[0], ysegment12[0], xsegment12[1], ysegment12[1], grapher, 12);
        calculateSlopeAndIntercept(xsegment13[0], ysegment13[0], xsegment13[1], ysegment13[1], grapher, 13);
        
        //calculate the slope and intercept, then graph the pyramidal cells on the inside segments
        calculateSlopeAndIntercept(xmiddle1[0], ymiddle1[0], xmiddle1[1], ymiddle1[1], grapher, 14);        
        calculateSlopeAndIntercept(xmiddle2[0], ymiddle2[0], xmiddle2[1], ymiddle2[1], grapher, 15); 
        calculateSlopeAndIntercept(xmiddle3[0], ymiddle3[0], xmiddle3[1], ymiddle3[1], grapher, 16);
        calculateSlopeAndIntercept(xmiddle4[0], ymiddle4[0], xmiddle4[1], ymiddle4[1], grapher, 17);
        calculateSlopeAndIntercept(xmiddle5[0], ymiddle5[0], xmiddle5[1], ymiddle5[1], grapher, 18);
        calculateSlopeAndIntercept(xmiddle6[0], ymiddle6[0], xmiddle6[1], ymiddle6[1], grapher, 19);
        calculateSlopeAndIntercept(xmiddle7[0], ymiddle7[0], xmiddle7[1], ymiddle7[1], grapher, 20);
        calculateSlopeAndIntercept(xmiddle8[0], ymiddle8[0], xmiddle8[1], ymiddle8[1], grapher, 21);
        

        // Prepare all line graphics
        grapher.graph(xsegment1, ysegment1, Color.BLACK);
        grapher.graph(xsegment2, ysegment2, Color.BLACK);
        grapher.graph(xsegment3, ysegment3, Color.BLACK);
        grapher.graph(xsegment4, ysegment4, Color.BLACK);
        grapher.graph(xsegment5, ysegment5, Color.BLACK);
        grapher.graph(xsegment6, ysegment6, Color.BLACK);
        grapher.graph(xsegment7, ysegment7, Color.BLACK);
        grapher.graph(xsegment8, ysegment8, Color.BLACK);
        grapher.graph(xsegment9, ysegment9, Color.BLACK);
        grapher.graph(xsegment10, ysegment10, Color.BLACK);
        grapher.graph(xsegment11, ysegment11, Color.BLACK);
        grapher.graph(xsegment12, ysegment12, Color.BLACK);
        grapher.graph(xsegment13, ysegment13, Color.BLACK);

        grapher.graph(xmiddle1, ymiddle1, Color.GREEN);
        grapher.graph(xmiddle2, ymiddle2, Color.GREEN);
        grapher.graph(xmiddle3, ymiddle3, Color.GREEN);
        grapher.graph(xmiddle4, ymiddle4, Color.GREEN);
        grapher.graph(xmiddle5, ymiddle5, Color.GREEN);
        grapher.graph(xmiddle6, ymiddle6, Color.GREEN);
        grapher.graph(xmiddle7, ymiddle7, Color.GREEN);
        grapher.graph(xmiddle8, ymiddle8, Color.GREEN);
        
        
        grapher.display(); // Display all lines when ready
    }
    //calculate the slope and y intercept, then call the pyramidal method in neuron to graph the cells
    public static void calculateSlopeAndIntercept(double x1, double y1, double x2, double y2, SwingGraphics grapher, int num) {
        
    	double slope = (double) (y2 - y1) / (x2 - x1);
        double intercept = y1 - slope * x1;
        double xavg = (x1 - x2)/ 2;
        double yavg = (y1-y2)/2;
        Neuron neuron = new Neuron();
        
        // Call neuron.pyramidal method
        //the xavg+x2 and yavg +y2 is to place it in the middle of the line
        neuron.pyramidal(xavg+x2, yavg+y2, grapher, Math.atan(slope), num);
    }
}

