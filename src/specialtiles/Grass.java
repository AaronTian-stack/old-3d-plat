package specialtiles;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Level;
import Engine.gfx.Image;

public class Grass extends GameObject {
	
	private Image grassStill, grassStillT, grassSway1, grassSway1T, grassSway2, grassSway2T;
	
	private int x1,y1,tileX,tileY;
	
	private boolean[][] inside;
	
	private Level level;
	
	
	
	public Grass(float posX, float posY,Level level,boolean[][] inside) {
		
		this.posX=posX;
		this.posY=posY;
		
		this.grassStill = new Image("/tiles/grassS.png");
		this.grassStillT = new Image("/tiles/grassST.png");
		this.grassSway1 = new Image("/tiles/grassSW1.png");
		this.grassSway1T = new Image("/tiles/grassSW1T.png");
		this.grassSway2 = new Image("/tiles/grassSW2.png");
		this.grassSway2T = new Image("/tiles/grassSW2T.png"); 
		
		this.tileX = (int) (posX/level.getTilesize());
		this.tileY = (int) (posY/level.getTilesize());
		
		//System.out.println("grass tile position "+tileX+" "+tileY);
		
		this.inside=inside;
	
		this.level = level;
		
		
	
		
	}

	float grassT,grassT2 = 0;
	boolean grassB,sprite = false;
	public void update(GameContainer gc, float dt) {
		
		//xc = level.getXc()+(int)posX-level.getDraw();
		//yc = level.getYc()+(int)posY-level.getDraw();
		
		if(onScreen(gc, level, xc, yc)) { //only update on screen

		//System.out.println("updating");

		if(!grassB)
			grassT+=dt;
		else
			grassT2+=dt;
		
		
		
		if(grassT>((Math.random() * (4) + 2))) { //(int)(Math.random() * (max - min + 1) + min) 
			grassT=0;
			grassB=true;
			
			if(Math.random()>=0.5)
				sprite=false;
			else
				sprite=true;
			
		}
		
		if(grassT2>((Math.random() * (4) + 2))) {
			grassT2=0;
			grassB=false;
			
			if(Math.random()>=0.5)
				sprite=false;
			else
				sprite=true;
			
				
		}
		
		}
		
	}
	
	
	public void render(GameContainer gc, Renderer r) {
		
		
		xc = level.getXc()+(int)posX-level.getDraw();
		yc = level.getYc()+(int)posY-level.getDraw();
		
		//r.drawImage(grassStill, level.getXc()+(int)posX-level.getDraw(), level.getYc()+(int)posY-level.getDraw(), false, 0);
		
		
		if(grassB)
    		if(inside[tileY][tileX]) {
    			r.drawImage(grassStillT, xc, yc, false, 0);
    		}
    			
    		else {
    			r.drawImage(grassStill, xc, yc, false, 0);
    		}
    			
    	else {
    		if(inside[tileY][tileX]) {
    			if(sprite)
    				r.drawImage(grassSway1T, xc, yc, false, 0);
    			else
    				r.drawImage(grassSway2T, xc, yc, false, 0);
    		}
    			
    		else {
    			if(sprite)
    				r.drawImage(grassSway1, xc, yc, false, 0);
    			else
    				r.drawImage(grassSway2, xc, yc, false, 0);
    		}
    			
    	}
    		
		
	}


}
