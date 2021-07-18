package Engine.game;

import java.awt.event.KeyEvent;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.gfx.Image;

public class Camera extends GameObject {
	
	private Level l;
	private GameObject target;
	
	private GameObject[] objects;
	//private String targetTag;
	private int offX, offY,speed;
	
	private Image right = new Image("/ui/right.png");
	private Image left = new Image("/ui/left.png");
	private Image up = new Image("/ui/up.png");
	private Image down = new Image("/ui/down.png");
	
	public Camera(Level l,int speed,GameObject...gameObjects) {
		
		
		this.objects=gameObjects;
		
		this.l = l;
		
		
		this.target = l.getP()[0];
		this.speed=speed;
		
		
		this.offX-=384/2-target.getPpx();
		this.offY-=216/2-target.getPpy();
	}
	

	
	int look = 100;
	int scroll=0;
	boolean ret = false;
	public void update(GameContainer gc, float dt) {

		scroll=0;

		int targetX = gc.getWidth()/2-target.getPpx();
		int targetY = gc.getHeight()/2-target.getPpy();
		
		target.looking=0;
		target.looking2=0;
		if(target.isGround()) {
			
			if(gc.getInput().isKey(KeyEvent.VK_A)) {
				targetX+=look;
				target.looking2=1;
			}
			
			
			else if(gc.getInput().isKey(KeyEvent.VK_D)) {
				targetX-=look;
				target.looking2=2;
			}
			
			if(gc.getInput().isKey(KeyEvent.VK_W)) {
				targetY+=look;
				target.looking=1;
			}
			
			
			else if(gc.getInput().isKey(KeyEvent.VK_S)) {
				targetY-=look;
				target.looking=2;
			}
			
			
			
		}
		
		
		offX-=dt*(offX-targetX)*speed;
		offY-=dt*(offY-targetY)*speed;

		target.xc=offX;
		target.yc=offY;
		
		l.xc=offX;
		l.yc=offY;
		
		for(GameObject j : objects) {
			
			j.xc=offX;
			j.yc=offY;
		
		}
		
		
		if(gc.getInput().getScroll()==1) {
			scroll=1;
		}
		if(gc.getInput().getScroll()==-1) {
			scroll=-1;
		}
		
		if(!target.ground)
			temp=0;
		
		if(gc.getZoom()==0)
			temp=0;
		
		if(gc.getZoom()>0 && target.velocity.getLength()<1) {
			temp+=dt;
			if(temp>5) {
				ret=true;
				temp=0;
			}
		}
		
	
	}

	
	public void render(GameContainer gc, Renderer r) {
		
		//r.fontText(""+gc.getZoom(), 100, 100);
		if(target.tag.equals("player")) {
		
			if(scroll==1) {
				r.scale(gc,gc.getZoomScale());
				
			}
			if(scroll==-1) {
				r.scale(gc,1/gc.getZoomScale());
				
			}
			
			if(ret) {
				r.scale(gc,1/gc.getZoomScale());
				if(gc.getZoom()==0)
					ret=false;
			}
		
		}
		
		
		if(target.looking==1)
			r.drawImage(up, gc.getWidth()/2, 2, false, 0);
		
		if(target.looking==2)
			r.drawImage(down, gc.getWidth()/2, gc.getHeight()-down.getH()-2, false, 0);
		
		if(target.looking2==1)
			r.drawImage(left, 2, gc.getHeight()/2, false, 0);
		
		if(target.looking2==2)
			r.drawImage(right, gc.getWidth()-right.getW()-2, gc.getHeight()/2, false, 0);
		
	}

}
