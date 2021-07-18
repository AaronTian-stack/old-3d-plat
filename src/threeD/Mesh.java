package threeD;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Mesh {
	
	private MyPolygon[] polygons;
	
	private int color;
	
	private double rotX;
	private double rotY;
	private double rotZ;
	
	private double posX;
	private double posY;
	private double posZ;
	
	
	public Mesh(double x, double y, double z, int color, MyPolygon...polygons) {
		this.color=color;
		//System.out.println(color);
		this.polygons=polygons;
		this.posX=x;
		this.posY=y;
		this.posZ=z;
		
		//this.sortPolygons();
	}
	
	public Mesh(MyPolygon...polygons) {
		this.posX=0;
		this.posY=0;
		this.posZ=0;
		this.rotX=0;
		this.rotY=0;
		this.rotZ=0;
		color = 0xff00ff00;
		this.polygons=polygons;
		
	}
	
	public void render() {
		for(MyPolygon poly : this.polygons) {
			//poly.render();
		}
	}
	
	public void rotate(double xDegrees,double yDegrees,double zDegrees) {
		for(MyPolygon p : this.polygons) {
			p.rotate(xDegrees, yDegrees, zDegrees);
		}
		this.sortPolygons();
		rotX+=xDegrees;
		rotY+=yDegrees;
		rotZ+=zDegrees;
	}
	
	public void rotate(double xDegrees,double yDegrees,double zDegrees,double x,double y,double z) {
		for(MyPolygon p : this.polygons) {
			//p.rotate(xDegrees, yDegrees, zDegrees,x,y,z,p.getAn());
		}
		this.sortPolygons();
		rotX+=xDegrees;
		rotY+=yDegrees;
		rotZ+=zDegrees;
	}
	
	public void translate(double x, double y, double z) {
		for(MyPolygon p:this.polygons) {
			p.translate(x,y,z);
			
		}
		this.sortPolygons();
		posX+=x;
		posY+=y;
		posZ+=z;
	}
	
	private void sortPolygons() {
		MyPolygon.sortPoligons(this.polygons);
	}
	
	
	public static Color getShade(Color color, double shade) {
		
		
	    double redLinear = Math.pow(color.getRed(), 2.4) * shade;
	    double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
	    double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;
	    
	    //System.out.println("R: "+redLinear+" G: "+greenLinear+" B: "+blueLinear);
	   

	    int red = (int) Math.pow(redLinear, 1/2.4);
	    int green = (int) Math.pow(greenLinear, 1/2.4);
	    int blue = (int) Math.pow(blueLinear, 1/2.4);

	    //System.out.println("R: "+red+" G: "+green+" B: "+blue);

	    return new Color(red, green, blue);
	   
	}

	public MyPolygon[] getPolygons() {
		return polygons;
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
	
	

}
