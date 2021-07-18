package threeD;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyPolygon {
	
	private MyPoint[] points;
	private MyPoint inflection;
	private int color;
	
	private double rotX;
	private double rotY;
	private double rotZ;
	
	private double posX;
	private double posY;
	private double posZ;
	
	private double[][] realpos;
	
	
	
	private int[] children;
	
	private int an; //animation number
	//private double prZ;
	//private double prY;
	//private double prX;
	
	
	public MyPoint[] getMyPoint() {
		return getPoints();
		
	}
	
	public int getColor() {
		return this.color;
	}
	
	
	
	public MyPolygon(int color, double posX, double posY, double posZ, int an, double iX, double iY, double iZ, int[] children, int nop, MyPoint... points) {
		this.color=color;
		this.setPoints(new MyPoint[points.length]);
		for(int i=0; i<points.length; i++) {
			MyPoint p = points[i];
			this.getPoints()[i]=new MyPoint(posX+p.x,posY+p.y,posZ+p.z);
		}
		this.an=an;
		
		this.posX=posX;
		this.posY=posY;
		this.posZ=posZ;
		
		//this.prZ=0;
		//this.prY=0;
		//this.prX=0;
		
		this.rotX=0;
		this.rotY=0;
		this.rotZ=0;
		
		realpos = new double[nop][3]; //no of parts
		//System.out.println("R");
		//System.out.println(Arrays.deepToString(realpos));
	
		inflection = new MyPoint(iX,iY,iZ);
		this.children=children;
		
	}
	
	public MyPolygon(MyPoint... points) {
		color = 0xff00ff00;
		this.setPoints(new MyPoint[points.length]);
		for(int i=0; i<points.length; i++) {
			MyPoint p = points[i];
			this.getPoints()[i]=new MyPoint(p.x,p.y,p.z);
		}
	}
	
	public Polygon render() {
		Polygon poly = new Polygon();
		for(int i=0;i<getPoints().length;i++) {
			
			
			
			Point p = PointConverter.convertPoint(this.getPoints()[i]);
			
			//System.out.println(p.x);
			//System.out.println(p.y);
			
			poly.addPoint(p.x, p.y);
		}
		
		return poly;

	}
	
	public void rotate(double xDegrees,double yDegrees,double zDegrees) {
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, xDegrees);
			PointConverter.rotateAxisY(p, yDegrees);
			PointConverter.rotateAxisZ(p, zDegrees);
		}
		PointConverter.rotateAxisX(inflection, xDegrees);
		PointConverter.rotateAxisY(inflection, yDegrees);
		PointConverter.rotateAxisZ(inflection, zDegrees);
		rotX+=xDegrees;
		rotY+=yDegrees;
		rotZ+=zDegrees;
		rotX%=1800;
		rotY%=1800;
		rotZ%=1800;
		
	}
	
	public void rotate(double xDegrees,double yDegrees,double zDegrees,double x, double y,double z) {
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, xDegrees,x,y,z);
			PointConverter.rotateAxisY(p, yDegrees,x,y,z);
			PointConverter.rotateAxisZ(p, zDegrees,x,y,z);
		}
		PointConverter.rotateAxisX(inflection, xDegrees,x,y,z);
		PointConverter.rotateAxisY(inflection, yDegrees,x,y,z);
		PointConverter.rotateAxisZ(inflection, zDegrees,x,y,z);
		//System.out.println("IX"+inflection.x);System.out.println("IY"+inflection.y);System.out.println("IZ"+inflection.z);
		
		//realpos[partnumber][0]+=xDegrees;
		//realpos[partnumber][1]+=yDegrees;
		//realpos[partnumber][2]+=zDegrees;
		
		
		
		
		rotX+=xDegrees;
		rotY+=yDegrees;
		rotZ+=zDegrees;
		//System.out.println("EX"+rotX);System.out.println("EY"+rotY);System.out.println("EZ"+rotZ);
		rotX%=1800;
		rotY%=1800;
		rotZ%=1800;
	}
	
	public void rotateChild(double xDegrees,double yDegrees,double zDegrees,double x, double y,double z, int partnumber) {
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, xDegrees,x,y,z);
			PointConverter.rotateAxisY(p, yDegrees,x,y,z);
			PointConverter.rotateAxisZ(p, zDegrees,x,y,z);
		}
		PointConverter.rotateAxisX(inflection, xDegrees,x,y,z);
		PointConverter.rotateAxisY(inflection, yDegrees,x,y,z);
		PointConverter.rotateAxisZ(inflection, zDegrees,x,y,z);
		
		realpos[partnumber][0]+=xDegrees;
		realpos[partnumber][1]+=yDegrees;
		realpos[partnumber][2]+=zDegrees;
		
		//System.out.println(Arrays.deepToString(realpos));
		
		//prX+=xDegrees;
		//prY+=yDegrees;
		//prZ+=zDegrees;
		
		
		//prX%=1800;
		//prY%=1800;
		//prZ%=1800;

	}
	
	public void setRotation(double xDegrees,double yDegrees,double zDegrees) {
		//System.out.println("R "+rotX);
		double dx = xDegrees-rotX;
		double dy = yDegrees-rotY;
		double dz = zDegrees-rotZ;
		
		double iX=inflection.getX();
		double iY=inflection.getY();
		double iZ=inflection.getZ();
		
		
		
		
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, dx,iX,iY,iZ);
			PointConverter.rotateAxisY(p, dy,iX,iY,iZ);
			PointConverter.rotateAxisZ(p, dz,iX,iY,iZ);
		}
		
		PointConverter.rotateAxisX(inflection, dx,iX,iY,iZ);
		PointConverter.rotateAxisY(inflection, dy,iX,iY,iZ);
		PointConverter.rotateAxisZ(inflection, dz,iX,iY,iZ);
		
		rotX=xDegrees;
		rotY=yDegrees;
		rotZ=zDegrees;
		
		
	}
	
	public void NsetRotation(double xDegrees,double yDegrees,double zDegrees) { //doesnt modify fields
		//System.out.println("R "+rotX);
		double dx = xDegrees-rotX;
		double dy = yDegrees-rotY;
		double dz = zDegrees-rotZ;
		
		double iX=inflection.getX();
		double iY=inflection.getY();
		double iZ=inflection.getZ();
		
		
		
		
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, dx,iX,iY,iZ);
			PointConverter.rotateAxisY(p, dy,iX,iY,iZ);
			PointConverter.rotateAxisZ(p, dz,iX,iY,iZ);
		}
		
		PointConverter.rotateAxisX(inflection, dx,iX,iY,iZ);
		PointConverter.rotateAxisY(inflection, dy,iX,iY,iZ);
		PointConverter.rotateAxisZ(inflection, dz,iX,iY,iZ);
		
		
		
	}
	
	public void setRotation(double xDegrees,double yDegrees,double zDegrees,double iX,double iY,double iZ,int partnumber) {
		
		
		
		double dx = xDegrees-rotX;
		double dy = yDegrees-rotY;
		double dz = zDegrees-rotZ;
		
		
	
		
		
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, dx,iX,iY,iZ);
			PointConverter.rotateAxisY(p, dy,iX,iY,iZ);
			PointConverter.rotateAxisZ(p, dz,iX,iY,iZ);
		}
		
		PointConverter.rotateAxisX(inflection, dx,iX,iY,iZ);
		PointConverter.rotateAxisY(inflection, dy,iX,iY,iZ);
		PointConverter.rotateAxisZ(inflection, dz,iX,iY,iZ);
		
		realpos[partnumber][0] = xDegrees;
		realpos[partnumber][1] = yDegrees;
		realpos[partnumber][2] = zDegrees;
		
		rotX=xDegrees;
		rotY=yDegrees;
		rotZ=zDegrees;
		
		
	}
	
	
	public void setCRotation(double xDegrees,double yDegrees,double zDegrees,double iX,double iY,double iZ,int partnumber) {
		
		//double dx = xDegrees-prX; //where you want to be?
		//double dy = yDegrees-prY;
		//double dz = zDegrees-prZ;
		
		double dx = xDegrees-realpos[partnumber][0]; //where you want to be?
		double dy = yDegrees-realpos[partnumber][1];
		double dz = zDegrees-realpos[partnumber][2];
		
		
		
		for(MyPoint p : getPoints()) {
			PointConverter.rotateAxisX(p, dx,iX,iY,iZ);
			PointConverter.rotateAxisY(p, dy,iX,iY,iZ);
			PointConverter.rotateAxisZ(p, dz,iX,iY,iZ);
		}
		
		realpos[partnumber][0]=xDegrees;
		realpos[partnumber][1]=yDegrees;
		realpos[partnumber][2]=zDegrees;
		
		PointConverter.rotateAxisX(inflection, dx,iX,iY,iZ);
		PointConverter.rotateAxisY(inflection, dy,iX,iY,iZ);
		PointConverter.rotateAxisZ(inflection, dz,iX,iY,iZ);
		
		//prX=xDegrees;
		//prY=yDegrees;
		//prZ=zDegrees;
		
	}
	
	public void translate(double x,double y,double z) {
		for(MyPoint p : getPoints()) {
			PointConverter.translateAxisX(p, x);
			PointConverter.translateAxisY(p, y);
			PointConverter.translateAxisZ(p, z);
		}
		posX+=x;
		posY+=y;
		posZ+=z;
	}
	
	
	public double getAverageX() {
		double sum=0;
		for(MyPoint p:this.getPoints()) {
			sum+=p.x;

		}
		return sum/this.getPoints().length;
	}
	
	public void setColor(int color) {
		this.color=color;
	}
	
	public static MyPolygon[] sortPoligons(MyPolygon[] polygons) {
		List<MyPolygon>polygonsList = new ArrayList<MyPolygon>();
		for(MyPolygon poly:polygons) {
			polygonsList.add(poly);
		}
		
		Collections.sort(polygonsList,new Comparator<MyPolygon>(){
			public int compare(MyPolygon p1, MyPolygon p2) {
				return p1.getAverageX()-p2.getAverageX()<0?1:-1;
			}
		});
		
		for(int i=0;i<polygons.length;i++) {
			polygons[i]=polygonsList.get(i);
		}
		
		return polygons;
		
	}
	
	public double calculateNormals() {
		
			MyPoint[] normalList = getPoints();
			MyPoint vector1 = new MyPoint(normalList[1].x-normalList[0].x,normalList[1].y-normalList[0].y,normalList[1].z-normalList[0].z);
			MyPoint vector2 = new MyPoint(normalList[2].x-normalList[0].x,normalList[2].y-normalList[0].y,normalList[2].z-normalList[0].z);
			MyPoint norm = new MyPoint(vector1.y * vector2.z - vector1.z * vector2.y,
			         vector1.z * vector2.x - vector1.x * vector2.z,
			         vector1.x * vector2.y - vector1.y * vector2.x);
			
			double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
			    norm.x /= normalLength;
			    norm.y /= normalLength;
			    norm.z /= normalLength;
			    
			double angleCos = Math.abs(norm.x);
			
			return angleCos;
			
		

	}

	public MyPoint[] getPoints() {
		return points;
	}

	public void setPoints(MyPoint[] points) {
		this.points = points;
	}

	public double getRotX() {
		return rotX;
	}

	public void setRotX(double rotX) {
		this.rotX = rotX;
	}

	public double getRotY() {
		return rotY;
	}

	public void setRotY(double rotY) {
		this.rotY = rotY;
	}

	public double getRotZ() {
		return rotZ;
	}

	public void setRotZ(double rotZ) {
		this.rotZ = rotZ;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	public int getAn() {
		return an;
	}

	public void setAn(int an) {
		this.an = an;
	}


	public int[] getChildren() {
		return children;
	}

	public void setChildren(int[] children) {
		this.children = children;
	}
	
	public String toString() {
		return (rotX+" "+rotY+" "+rotZ);	
	}

	public MyPoint getInflection() {
		return inflection;
	}

	public void setInflection(MyPoint inflection) {
		this.inflection = inflection;
	}

	public double[][] getRealpos() {
		return realpos;
	}

	public void setRealpos(double[][] realpos) {
		this.realpos = realpos;
	}
	
	

	

}
