package Engine.game.vfx;

import java.util.ArrayList;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Vec2;

public class ParticleManager extends GameObject {
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	//private GameObject source;
	private boolean repeat;
	private GameObject source;
	private float angleRange;
	private float angle;
	private int widthHeightMin;
	private int widthHeightMax;
	private float maxX;
	private float minX;
	private float maxY;
	private float minY;
	private float rate;
	private boolean shape;
	private float timeLimit;
	private int[] colors;
	private int numMax,numMin;
	private float fadeoutRate;
	private float duration;
	
	public ParticleManager(GameObject source, float posX, float posY, int widthHeightMin, int widthHeightMax, int numMin, int numMax,float minX, float maxX, float minY, float maxY, float angle, float angleRange, float timeLimit,float fadeoutRate,float gravity,float rate,boolean shape,float duration,int ... color) {
		
		//this.source=source;
		this.posX=posX;
		this.posY=posY;
		this.colors=color;
		this.ground=false;
		this.tag="particleManager";
		
		
		if(duration<=0)
			repeat=true;
		else
			repeat=false;
		
		
		this.source=source;
		this.angle=angle;
		this.angleRange=angleRange;
		this.widthHeightMax = widthHeightMax;
		this.widthHeightMin = widthHeightMin;
		this.maxX=maxX;
		this.minX=minX;
		this.maxY=maxY;
		this.minY=minY;
		this.shape=shape;
		this.timeLimit=timeLimit;
		this.gravity=gravity;
		this.rate=rate;
		//randomize velocity
		this.numMax=numMax;
		this.numMin=numMin;
		this.fadeoutRate=fadeoutRate;
		this.duration=duration;
		/*
		if(!repeat) {
			
			int number = (int)(Math.random() * (numMax - numMin + 1) + numMin); //number of particles to create
			
			for(int i=0;i<number;i++) {
				int size = (int)(Math.random() * (widthHeightMax - widthHeightMin + 1) + widthHeightMin); //size of particle to create
				
				float strengthX = (float)(Math.random() * (maxX - minX + 1) + minX); //strength of particle to create
				float strengthY = (float)(Math.random() * (maxY - minY + 1) + minY); //strength of particle to create
				
				float angleP = (float)(	(Math.random() * ((angle+angleRange/2) - (angle-angleRange/2) + 1) + (angle-angleRange/2))	*Math.PI/180		); //angle of particle to create, MUST BE IN RADIANS
				
				float vX = (float)(Math.cos(angleP)*strengthX);
				
				float vY = (float)(Math.sin(angleP)*strengthY);
				
				int colorI = (int)(Math.random() * (colors.length));//pick random color
				
				//System.out.println(colorI);
				
				//System.out.println("G "+gravity );
				
				particles.add(new Particle(source,posX,posY,size,vX,vY,timeLimit,fadeoutRate,gravity,shape,colors[colorI])); //(GameObject source, int posX,int posY,int width, float vX, float vY, float timeLimit) {
			}
		
		}*/
		
		
	}

	float timer = 0;
	float bigTimer = 0;
	public void update(GameContainer gc, float dt) {
		
		timer+=dt;
		
		
		
		
		if(bigTimer<=duration) {
			
		
		if(timer>=rate) {
			int number = (int)(Math.random() * (numMax - numMin + 1) + numMin);
			for(int i=0;i<number;i++) {
				int size = (int)(Math.random() * (widthHeightMax - widthHeightMin + 1) + widthHeightMin); //size of particle to create
				
				float strengthX = (float)(Math.random() * (maxX - minX + 1) + minX); //strength of particle to create
				float strengthY = (float)(Math.random() * (maxY - minY + 1) + minY); //strength of particle to create
				
				float angleP = (float)(	(Math.random() * ((angle+angleRange/2) - (angle-angleRange/2) + 1) + (angle-angleRange/2))	*Math.PI/180		); //angle of particle to create, MUST BE IN RADIANS
				
				float vX = (float)(Math.cos(angleP)*strengthX);
				
				float vY = (float)(Math.sin(angleP)*strengthY);
				
				int colorI = (int)(Math.random() * (colors.length));//pick random color
				
				
				
				particles.add(new Particle(source,posX,posY,size,vX,vY,timeLimit,fadeoutRate,gravity,shape,colors[colorI]));
			}
			timer=0;
		}
		
		
		}
		

		if(duration>0)
			bigTimer+=dt;
		
		for(int i=0;i<particles.size();i++) {
			if(particles.get(i).isFinished()) {
				particles.remove(i);
				if(i>0)
					i--;
				//System.out.println("removed particle");
			}
			
		}
		
		for(int i=0;i<particles.size();i++) {
			particles.get(i).update(gc, dt);
		}
		
		
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		
		
		for(Particle p : particles)
			p.render(gc, r);
		
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

}
