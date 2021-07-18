package Engine.game.vfx;

import java.util.ArrayList;
import java.util.Arrays;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Level;

public class Lightmap extends GameObject {
	
	private ArrayList<Light> lights = new ArrayList<Light>();
	//private float[][] lightmap;
	
	
	
	public Lightmap(Level Level) {
		
		
		//this.lightmap = new float[Level.getLevel().length*split][Level.getLevel()[0].length*split];
		
		for (int i=0;i<Level.getLevel().length;i++) {
	         for (int j=0;j<Level.getLevel()[i].length;j++) {
	        	 if(Level.getLevel()[i][j]==11) {
	        		 
	        		
	        		 
	        		 //System.out.println(i*Level.getTilesize());
	        		 //System.out.println(j*Level.getTilesize());
	        		 
	        		 lights.add(new Light(j*Level.getTilesize(),i*Level.getTilesize(),60,2,360,0xffffffff,Level,this));
	        		 
	        		
	        	 }
	         }
	      }
		
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		//for (int i=0;i<lightmap.length;i++) {
		//	Arrays.fill(lightmap[i], 0f);
		//}
		

		for(Light li : lights) {
			li.update(gc, dt);
		}

		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
	
		for(Light li : lights) {
			
			li.render(gc, r);

		}

	}


}
