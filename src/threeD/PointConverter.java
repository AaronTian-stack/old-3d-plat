package threeD;

import java.awt.Point;

import Engine.GameContainer;



public class PointConverter {
	
	private static double scale = 1;
	private final static double ZoomFactor=1.2;
	private double theta2 = Math.PI/180;

	public static Point convertPoint(MyPoint point3D) {
		double x3d = point3D.y*scale;
		double y3d = point3D.z*scale;
		double depth = point3D.x*scale;
		double[] newVal = scale(x3d,y3d,scale);
		int x2d = (int) (newVal[0]);
		int y2d = (int) (-newVal[1]);
		
		Point point2D= new Point(x2d,y2d);
		return point2D;
	}
	

	private static double[] scale(double x3d,double y3d,double depth) {
		double dist = Math.sqrt(Math.pow(x3d, 2)+Math.pow(y3d, 2));
		double theta = Math.atan2(y3d, x3d);
		double depth2= 15-depth;
		double localScale = Math.abs(14000/(depth2+14000));
		//dist*=localScale;
		double[]newVal=new double[2];
		newVal[0]=dist*Math.cos(theta);
		newVal[1]=dist*Math.sin(theta);
		return newVal;
		
	}
	
	public static void rotateAxisX(MyPoint p, double degrees) {
		double radius = Math.sqrt(Math.pow(p.y, 2)+Math.pow(p.z, 2));
		double theta = Math.atan2(p.z, p.y);
		theta+=2*Math.PI/360*degrees;
		p.y=radius*Math.cos(theta);
		p.z=radius*Math.sin(theta);
	}
	
	public static void rotateAxisY(MyPoint p, double degrees) {
		double radius = Math.sqrt(Math.pow(p.x, 2)+Math.pow(p.z, 2));
		double theta = Math.atan2(p.x, p.z);
		theta+=2*Math.PI/360*degrees;
		p.x=radius*Math.sin(theta);
		p.z=radius*Math.cos(theta);
	}
	
	public static void rotateAxisZ(MyPoint p, double degrees) {
		double radius = Math.sqrt(Math.pow(p.y, 2)+Math.pow(p.x, 2));
		double theta = Math.atan2(p.y, p.x);
		theta+=2*Math.PI/360*degrees;
		p.y=radius*Math.sin(theta);
		p.x=radius*Math.cos(theta);
	}
	
	public static void rotateAxisX(MyPoint p, double degrees,double x,double y,double z) {
		//System.out.println("before");
		//System.out.println("X"+p.x);	System.out.println("Y"+p.y);	System.out.println("Z"+p.z);
		p.x-=x;
		p.y-=y;
		p.z-=z;
		//System.out.println("after");
		//System.out.println("X"+p.x);	System.out.println("Y"+p.y);	System.out.println("Z"+p.z);
		
		
		double radius = Math.sqrt(Math.pow(p.y, 2)+Math.pow(p.z, 2));
		double theta = Math.atan2(p.z, p.y);
		theta+=2*Math.PI/360*degrees;
		p.y=radius*Math.cos(theta);
		p.z=radius*Math.sin(theta);
		
		p.x+=x;
		p.y+=y;
		p.z+=z;
		
		
	}
	
	public static void rotateAxisY(MyPoint p, double degrees,double x,double y,double z) {
		p.x-=x;
		p.y-=y;
		p.z-=z;

		double radius = Math.sqrt(Math.pow(p.x, 2)+Math.pow(p.z, 2));
		double theta = Math.atan2(p.x, p.z);
		theta+=2*Math.PI/360*degrees;
		p.x=radius*Math.sin(theta);
		p.z=radius*Math.cos(theta);
		

		p.x+=x;
		p.y+=y;
		p.z+=z;
		
	}
	
	public static void rotateAxisZ(MyPoint p, double degrees,double x,double y,double z) {
		
		p.x-=x;
		p.y-=y;
		p.z-=z;
		
		double radius = Math.sqrt(Math.pow(p.y, 2)+Math.pow(p.x, 2));
		double theta = Math.atan2(p.y, p.x);
		theta+=2*Math.PI/360*degrees;
		p.y=radius*Math.sin(theta);
		p.x=radius*Math.cos(theta);
		

		p.x+=x;
		p.y+=y;
		p.z+=z;
		
	}
	
	public static void translateAxisX(MyPoint p, double distanceX) {
		p.y+=distanceX;
		//System.out.println(p.x+" "+p.y+" "+p.z);
	}

	public static void translateAxisY(MyPoint p, double distanceY) {
		p.x+=distanceY;
		//System.out.println(p.x+" "+p.y+" "+p.z);
		
	}

	public static void translateAxisZ(MyPoint p, double distanceZ) {
		p.z+=distanceZ;
		//System.out.println(p.x+" "+p.y+" "+p.z);
		
	}

	public static void zoomOut() {
		scale/=ZoomFactor;
		
	}

	public static void zoomIn() {
		scale*=ZoomFactor;
		
	}
}