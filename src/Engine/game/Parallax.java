package Engine.game;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.gfx.Image;

public class Parallax extends GameObject{
	
	private Image image;
	
	private GameObject target;
	
	private int scale;
	
	public Parallax(GameObject target,String path,int posX,int posY,int scale) {
		//this.posX=posX;
		//this.posY=posY;
		
		this.ppx=posX;
		this.ppy=posY;
		
		this.image = new Image(path);
		this.scale=scale;
		this.target=target;
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		xc=target.getXc()/scale-ppx;
		yc=target.getYc()/scale-ppy;
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		r.drawImage(image, xc, yc, false, 0);
		
	}


}
