package Question1;
import java.awt.Color;
public class Lab5Q1Test {
  public static final int WIDTH = 1000, HEIGHT = 750;
  public static final int MIN_SIZE = 2;
  public static void main(String[] args){
    StdDraw.setCanvasSize(WIDTH,HEIGHT);
    StdDraw.show(0);
    recursiveRectangleFill(0,1,0,1);
    StdDraw.show(0);
  }
  
  public static void recursiveRectangleFill(double minX, double maxX,
                                   double minY, double maxY){

double w = (maxX-minX)*WIDTH;
double H = (maxY-minY)*HEIGHT;
	  
	  	if(w>MIN_SIZE && H>MIN_SIZE) {
//	  		double xc = (maxX+minX)/2;
//	  		double yc = (maxY+minY)/2;
//	  		double hw = (maxX-minX)/3;
//	  		double hh = (maxY-minY)/3;
	  		
	  		
	  		StdDraw.setPenColor((int) (Math.random()*255) , (int) (Math.random()*255) , (int) (Math.random()*255));
	  		StdDraw.filledRectangle((maxX+minX)/2,(maxY+minY)/2,(maxX-minX)/3/2,(maxY-minY)/3/2);
	  		
	  		for(int i = 0; i < 3;i++ ) {
	  			for(int j = 0; j < 3; j++) {
	  				double minx = minX + i*((maxX-minX)/3);
	  				double miny = minY + j*((maxY-minY)/3);
	  				if(i != 1 || j !=1)
	  				recursiveRectangleFill(minx, minx + ((maxX-minX)/3),miny, miny + ((maxY-minY)/3));
	  			}
	  		}
	  	}
	  
    //Write your code here
    
  }//rectangleFill
}
