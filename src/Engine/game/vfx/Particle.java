package Engine.game.vfx;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Vec2;

public class Particle extends GameObject{
	
	private int alpha = 100;
	private float timeLimit;
	
	
	private float timer;
	private GameObject source;
	private boolean shape; //true for square false for circle
	private float maxspeed = 7;
	
	private boolean fadeout,finished;
	private float fadeoutRate;
	
	public Particle(GameObject source, float posX,float posY,int width, float vX, float vY, float timeLimit,float fadeoutRate,float gravity,boolean shape,int color) {
		
		this.tag="particle";
		this.posX=posX;
		this.posY=posY;
		this.width=width;
		this.height=width;
		this.color=color;
		this.timeLimit=timeLimit;
		this.ground=false;
		this.shape=shape;
		this.source=source;
		this.velocity=new Vec2(vX,vY);
		this.gravity=gravity;
		this.ramp=false;
		this.fadeoutRate=fadeoutRate;
		
		
		//System.out.println("G "+gravity);
		
	}

	float gravity2=0;
	public void update(GameContainer gc, float dt) {
		
		
		
		timer+=dt;
		
		
		
		if(alpha>0) {
	
		if(ground) {
			
			velocity.set((0-velocity.getX()/60*10),0);
			gravity2=0;
		}
		else {
			velocity.add(0,(gravity2*dt));
			if(!water)
				gravity2+=gravity;
			else {
				gravity2=10*dt/60;
				velocity.set(velocity.getX()/2,velocity.getY()/2);
			}
			//System.out.println("G "+gravity2);
		}
		
		if(water) {
			velocity.add((0-velocity.getX()/60*10),0);
			
		}
		
		if(velocity.getX()<0.1 && velocity.getX()>0) {
			velocity.setX(0);
		}
		if(velocity.getY()<0.1 && velocity.getY()>0) {
			velocity.setY(0);
		}
		if(velocity.getX()>-0.1 && velocity.getX()<0) {
			velocity.setX(0);
		}
		if(velocity.getY()>-0.1 && velocity.getY()<0) {
			velocity.setY(0);
		}
		

		
		
		if(velocity.getX()>maxspeed) {
			velocity.setX(maxspeed);
		}
		if(velocity.getY()>maxspeed) {
			velocity.setY(maxspeed);
		}
		if(velocity.getX()<-maxspeed) {
			velocity.setX(-maxspeed);
		}
		if(velocity.getY()<-maxspeed) {
			velocity.setY(-maxspeed);
		}
		
		
		
		if(timer>timeLimit) {
			fadeout=true;
			alpha-=dt*fadeoutRate;
			//System.out.println(alpha);
		}
		
		}
		
		else {
			//System.out.println("WERE DONE");
			finished=true;
		}
		
		
				
		
		posX+=velocity.getX();
		posY+=velocity.getY();
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		//System.out.println("drawing shapes");
		
		//System.out.println(shape);
		if(shape) {
			if(!fadeout) {
				r.fillSquare(source.getXc()+(int)posX, source.getYc()+(int)posY, width,height, color);
			}
			else {
				r.fillSquareT(source.getXc()+(int)posX, source.getYc()+(int)posY, width,height, color,alpha);
			}
		}
		else if(!shape) {
			if(!fadeout) {
				r.drawCircleFilled(source.getXc()+(int)posX, source.getYc()+(int)posY, width, color,false,0);
			}
			else {
				r.drawCircleFilled(source.getXc()+(int)posX, source.getYc()+(int)posY, width, color,true,alpha);
			}
		}
		
		
		
		
		//r.setPixel(source.getXc()+(int)posX, source.getYc()+(int)posY, 0xffff4242);

		
		
	}


	public float getTimer() {
		return timer;
	}


	public void setTimer(float timer) {
		this.timer = timer;
	}


	public boolean isFinished() {
		return finished;
	}


	public void setFinished(boolean finished) {
		this.finished = finished;
	}


}
