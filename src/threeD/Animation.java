package threeD;

import java.util.ArrayList;
import java.util.Arrays;

import Engine.GameContainer;

public class Animation {
	
	private Mesh model;
	
	private double[][][] animation;
	
	private int keyframe;
	
	public Animation(Mesh model,double[][][] testanimation) {

		this.model=model;
		this.animation = testanimation;
		keyframe = 0;
		
	}
	
	public void interpolate(float temp, int k) {
		
		//System.out.println("t"+temp);
		//System.out.println(animation.length);
		
		MyPolygon[] polygons = model.getPolygons();

		
		
		//double time = animation[keyframe][k][0];
		//System.out.println("t"+time);
		
		if(temp==0) {
			keyframe=0;
		}
		
		if(temp>=animation[keyframe][k][0]) {
			
			keyframe++;
			//System.out.println(keyframe);
			for(int i = 0;i<polygons.length;i++) {
				MyPolygon poly = polygons[i];
				int[] children = poly.getChildren(); //integer list of children indices
				poly.setRotation(animation[keyframe][poly.getAn()][0], animation[keyframe][poly.getAn()][1], animation[keyframe][poly.getAn()][2],poly.getInflection().x,poly.getInflection().y,poly.getInflection().z,poly.getAn());
				if(children!=null) {
					
					
					
					
					for(int j = 0;j<children.length;j++) {
						
						
						
						
						//for(int l = 0;l<poly.getRealpos().length;l++) {
						//if(j!=polygons[children[j]].getAn())
						polygons[children[j]].setCRotation(animation[keyframe][poly.getAn()][0], animation[keyframe][poly.getAn()][1], animation[keyframe][poly.getAn()][2],poly.getInflection().x,poly.getInflection().y,poly.getInflection().z,j);
						//ISSUE, when setting it also does it around top of hierarchy since prxyz are affected by top
						
					
					}
				}
				
			}
			
			
			//System.out.println(animation[keyframe][animation[keyframe].length-1][0]);
			
		}
		
		boolean ge = keyframe<animation.length-1;
		
		
		for(int i = 0;i<polygons.length;i++) {
			//System.out.println("animating polygon ");
			
			MyPolygon poly = polygons[i];
			int[] children = poly.getChildren(); //integer list of children indices
			
			double xc;
			double yc;
			double zc;
			
			double in;

			if(ge) {
				//in = (animation[keyframe+1][k][0]-animation[keyframe][k][0]) * 60;
				
				in=60;
				//System.out.println(in);
				xc = (animation[keyframe+1][poly.getAn()][0]-animation[keyframe][poly.getAn()][0]) / in; //needs to be 60 dividied by how many seconds u got
				yc = (animation[keyframe+1][poly.getAn()][1]-animation[keyframe][poly.getAn()][1]) / in;
				zc = (animation[keyframe+1][poly.getAn()][2]-animation[keyframe][poly.getAn()][2]) / in;
			}
			else {
				
				//in = (animation[keyframe][k][1]) * 60;
				in=60;
				//System.out.println(in);
				
				xc = (animation[keyframe][poly.getAn()][0]-animation[0][poly.getAn()][0]) / in;
				yc = (animation[keyframe][poly.getAn()][1]-animation[0][poly.getAn()][1]) / in;
				zc = (animation[keyframe][poly.getAn()][2]-animation[0][poly.getAn()][2]) / in;
			}

		//	System.out.println(xc+" "+yc+" "+zc);
			poly.rotate(xc, yc, zc,poly.getInflection().x,poly.getInflection().y,poly.getInflection().z); //rotate around point doesn't work anymore?
			//poly.rotate(xc, yc, zc);
			
			
			if(children!=null) {
				//polygons[children[0]].rotateChild(xc, yc, zc,poly.getInflection().x,poly.getInflection().y,poly.getInflection().z); //dont uncomment this
				
				for(int j = 0;j<children.length;j++) { 
					
					//for(int l = 0;l<poly.getRealpos().length;l++) {
					//System.out.println();
					//if(j!=polygons[children[j]].getAn())
					polygons[children[j]].rotateChild(xc, yc, zc,poly.getInflection().x,poly.getInflection().y,poly.getInflection().z,j);
					//}
				
				}
			}
	
		}
		
	
		
		
	}
	
	public void rotate(double x,double y,double z) {
		for(MyPolygon p : model.getPolygons()) {
			p.rotate(x, y, z);
		}
	}
	
	public void setR(double x,double y,double z) { //debugging function
		for(MyPolygon poly : model.getPolygons()) {
			poly.NsetRotation(x,y,z);
		}
	}
	

	public Mesh getModel() {
		return model;
	}

	public void setModel(Mesh model) {
		this.model = model;
	}

	public double[][][] getAnimation() {
		return animation;
	}

	public void setAnimation(double[][][] animation) {
		this.animation = animation;
	}

	
	
	
	

}
