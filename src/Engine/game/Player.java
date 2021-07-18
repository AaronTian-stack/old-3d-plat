package Engine.game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.vfx.ParticleManager;
import Engine.gfx.Image;
import audio.SoundClip;
import objparser.Obj;
import threeD.Animation;
import threeD.Mesh;
import threeD.MyPoint;
import threeD.MyPolygon;

public class Player extends GameObject {
	
	private int speed;
	private float maxspeed;
	private int accel;
	
	private int color;
	
	
	//private Mesh pmodel;
	//private Obj cube; 
	//private Obj cube2;
	//private Obj cube3;
	//private Obj test;
	//private Animation anim;
	//private double[][][] testanimation;
	//private Vec2 velocity;
	//private float gravity;
	//private boolean ground;
	private boolean drawL;
	
	private Image image = new Image("/player3/face.png");
	private Image face = new Image("/player3/face.png");
	private Image faceup = new Image("/player3/faceup.png");
	private Image facedown = new Image("/player3/facedown.png");
	private Image facefocus = new Image("/player3/facefocusS.png");
	private Image faceblink = new Image("/player3/faceblink.png");
	private Image facestretch = new Image("/player3/facestretch.png");
	
	
	
	
	private int oldmx;
	private int oldmy;
	
	private SoundClip jump  = new SoundClip("/audio/jump.wav"); 
	private SoundClip jumpW  = new SoundClip("/audio/jumpW.wav"); 
	
	public Player(int posX, int posY,int height,int width,int color) {
		this.tag="player";
		this.posX=posX; //* by size?
		this.posY=posY;
		
		this.width=width/2;
		this.height=height/2;
		this.looking = 0;
		this.velocity= new Vec2(0,0);
		
		this.speed=15;
		this.maxspeed=7.5f;
		this.accel=10;
		//this.gravity=0;
		this.color=color;
		
		//this.ground=false;
		
		//int[] cubechildren = new int[] {1,2};
		//int[] conechildren = new int[] {2};

		
		this.jump.setVolume(-5f); //musut be multiplied by scale factor (10 is nothing, decreases as lower)
		this.jumpW.setVolume(-5f);
		System.out.println(color);
		
		//testParticle = (new ParticleManager(this,posX,posY,2,3,5,10,0.2f,1.5f,0.1f,0.2f,-90,35,0.2f,2f,-0.1f, true,2,false,0xff505050,0xff707070,0xff909090)); //0xff801100,0xfffc6400,0xfffac000 //ParticleManager(GameObject source, 
		
		//float posX, float posY, int widthHeightMin, int widthHeightMax, int numMin, int numMax,float minX, float maxX, float minY, float maxY, float angle, float angleRange, float timeLimit,
		//float gravity,boolean repeat,boolean shape,int ... color) {
		
		
		//try {
			//cube = new Obj("src/Engine/game/model/cube.txt",scale,0,0,0, color,0,0,0,cubechildren,0,1); //you need to define name, scale, initial xyz, color, inflection xyz, children array, animation number
																													//and number of inflection points
			//cube2 = new Obj("src/Engine/game/model/cube.txt",scale-10,0,0,-77, color,0,0,0,conechildren,1,2); //inflection not defined right?
			//cube3 = new Obj("src/Engine/game/model/cube.txt",scale-15,0,0,-140, color,0,0,-77,null,2,3);
			
			
			//test = new Obj("src/Engine/game/model/cube.txt",6,0,0,0, color,0,0,0,null,0,1);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		
		
		
		//pmodel = new Mesh(0,0,0,0xff00ff00,cube.getPoly(),cube2.getPoly(),cube3.getPoly());
	
		
		
		//testanimation = new double[][][] {
		//	{	{0,0,0}	,{0,0,0},{0,0,0}	,{1}	}	,	
		//	{	{50,0,0},{50,0,0},{50,0,0},{2}	},	
		///	{	{0,0,0},{0,0,0},{0,0,0},{3}	 }	,	
		//	{	{0,0,0},{0,0,0},{0,0,0},{4}	},
		//	{	{0,0,0},{0,0,0},{0,0,0},{5}	}	,
			
		//	}; //you have to have beginning keyframe copypasted at end
		
		
		//double[][] defPose = new double[][]{	{0,0,0}	,{0,0,0}	};
		
		//anim = new Animation(pmodel,testanimation);
		//anim.setR(0,0,25);
		
		//test.getPoly().rotate(90,0,90);
			
		
	}
	
	//float temp = 0;
	boolean blink = false;
	float bb = 0;
	float maxspeedC = maxspeed;
	boolean g,control=false;
	float maxJump = 7f;
	
	@Override
	public void update(GameContainer gc, float dt) {

		if(control)
			controller(gc,dt);

		if(gc.getInput().isButtonDown(1) && (ground || water)) { //left click
			//System.out.println("CLICK");
			//draw line in render method
			
			drawL=true;	
			
			oldmx = gc.getInput().getMouseX();
			oldmy = gc.getInput().getMouseY();
			
			//System.out.println(oldmx+" "+oldmy);
			
		}
		if(gc.getInput().isButtonUp(1) && (ground || water)) {
			//System.out.println("RELEASE");
			int dx = gc.getInput().getMouseX()-oldmx;
			int dy = gc.getInput().getMouseY()-oldmy;
			
			
			
			//if(!water)
				velocity.add(dx/22, dy/22);
			//else
				//velocity.add(dx/66, dy/66);
		
			
			if(velocity.getX()>maxJump)
				velocity.setX(maxJump);
			
			if(velocity.getX()<-maxJump)
				velocity.setX(-maxJump);
			
			if(velocity.getY()>maxJump)
				velocity.setY(maxJump);
			
			if(velocity.getY()<-maxJump)
				velocity.setY(-maxJump);
			
			
			
			//need to check if you actually left the ground.
			
			if(velocity.getY()!=0)
				if((ground && !water) && velocity.getY()>0 || air)
					jump.play();
				else
					jumpW.play();
			drawL=false;
		}
		
		
		
		if(gc.getInput().isKeyDown(KeyEvent.VK_L)) {
			g = !g;
			gravity=0;
			control=false;
			
		}
		
		if(gc.getInput().isKeyDown(KeyEvent.VK_P)) {
			velocity.set(0, 0);
			control=!control;
			gravity=0;
			
		}
		
		
		
		if(gc.getInput().isKeyDown(KeyEvent.VK_K)) {
			this.setPosX(47*17);
			this.setPosY(0);
		}
		
		if(water) {
			
			velocity.add((0-velocity.getX()/60*accel/6),gravity*dt);
			
			//velocity.add((0-velocity.getX()/60*accel/2),0);
			
			//posY+=gravity*dt/5;
			if(!air)
				maxspeedC=maxspeed/4;
			else
				maxspeedC=maxspeed;

			if(gravity<6) {
				gravity++;
			}

		}
		
		if(air && !ramp && !ground && !water && !g) {
			maxspeedC=maxspeed;
			velocity.add(0,gravity*dt);
			gravity+=1.5f;	
		}
		
		if(ground) {
			
			this.setImage(face);
			
			velocity.add((0-velocity.getX())/60*accel,0);
			gravity=0;
			
		}
		
		if(ramp) {
			this.setImage(face);
			
		}

		
		if(velocity.getX()>maxspeedC) {
			velocity.setX(maxspeedC);
		}
		if(velocity.getY()>maxspeedC) {
			velocity.setY(maxspeedC);
		}
		if(velocity.getX()<-maxspeedC) {
			velocity.setX(-maxspeedC);
		}
		if(velocity.getY()<-maxspeedC) {
			velocity.setY(-maxspeedC);
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
		
		
		int randomTime = (int)(Math.random() * (8 - 5 + 1) + 5);
		//System.out.println("RANDOMTIME"+randomTime);
		if(temp>randomTime) { 
			
			double rand = Math.random();
			//System.out.println("R"+rand);
			if(rand<=0.5) {
				blink=true;
				bb=0;
			}
			else {
				blink=false;
			}
			
			temp=0;
			
		}
		
		if(blink) {
			if(velocity.getY()==0 && velocity.getX()==0 && ground)
				this.setImage(faceblink);
			else {
				this.setImage(face);
			}
			bb+=(dt);
			if(bb>0.2) {
				bb=0;
				blink=false;
			}
		}
		
		
		if(velocity.getY()<0) {
			this.setImage(faceup);
		}
		if(velocity.getY()>1.8) {
			this.setImage(facedown);
		}
		
		
		posX+=velocity.getX();
		posY+=velocity.getY();
		
		//System.out.println(velocity);
		
		temp+=dt;
		//System.out.println("SIZE "+this.particleManagers.size());
		
		for(ParticleManager p : this.particleManagers) {
			
			p.setPosX(this.posX);
			p.setPosY(this.posY);
			
			p.update(gc, dt);
		}
		
		for(int i=0;i<particleManagers.size();i++) {
			if(!particleManagers.get(i).isRepeat() && particleManagers.get(i).getParticles().size()==0) {
				particleManagers.remove(i);
				if(i>0)
					i--;
				System.out.println("removed particle manager");
			}
			
		}
		
		
		//anim.interpolate(temp,3); //input end as k
		
		
		//test.getPoly().rotate(1,1,1,0,0,0);
		
		this.setPpx((int)posX);
		this.setPpy((int)posY);
		
		
	}

	int textcolor = 	0xff32A852;
	public void render(GameContainer gc, Renderer r) {
		
		
		
		for(ParticleManager p : this.particleManagers)
			p.render(gc, r);
		
		if(velocity.getY()>6 && !ramp) {
			this.setImage(facestretch);
			//r.drawImage(image, xc+ppx-width, yc+ppy-height-3, false, 0);
		}
		
		if(drawL && (ground || water)) {
			this.setImage(facefocus);
		
			r.drawLine(xc+ppx, yc+ppy, xc+ppx+gc.getInput().getMouseX()-oldmx, yc+ppy+gc.getInput().getMouseY()-oldmy, color,false,0);
			r.drawImage(image, xc+ppx-width, yc+ppy-height+(face.getH()-facefocus.getH()), false, 0);
			if(gc.getZoom()<3)
				r.drawLine(oldmx, oldmy, gc.getInput().getMouseX(), gc.getInput().getMouseY(), color,true,50);

		
		}
		else {
			if(looking==1)
				r.drawImage(faceup, xc+ppx-width, yc+ppy-height, false, 0);
			else if (looking==2)
				r.drawImage(facedown, xc+ppx-width, yc+ppy-height, false, 0);
			else
				r.drawImage(image, xc+ppx-width, yc+ppy-height, false, 0);
		}
		
		
		
		
		r.drawSquare(xc+ppx, yc+ppy, width, height, color);
		//r.drawPoly(xc+ppx, yc+ppy, test.getPoly(), test, color);
		//r.drawCircle(xc+ppx, yc+ppy, width, color);
		//r.drawText("AIR "+airtime, 0, 200, textcolor);
		//r.drawText("pos "+posX+" "+posY, 0, 173, textcolor);
		//r.drawText("controller "+control, 0, 191, textcolor);
		//r.drawText("gravity "+g, 0, 182, textcolor);
		//r.drawText("vel "+velocity.toString(), 0, 209, textcolor);
		
		//r.drawCircleFilled(xc+ppx, yc+ppy, width, color);
		
		
		//r.blur(90);
		
		
		
		
	}
	
	public void controller(GameContainer gc, float dt) {
		//acceleration
				
				if(gc.getInput().isKey(KeyEvent.VK_W)) {
					//velocity[1] -= speed*dt;
					
					velocity.add(0,-speed*dt);
					
					//posY+=velocity.getY();
					//posY-=dt*speed;
				}
				else {
					//velocity[1] += (0-velocity[1])*dt*accel;
					
					velocity.add(0,(0-velocity.getY())*dt*accel);
					
					//posY+=velocity.getY();
				}
				
				if(gc.getInput().isKey(KeyEvent.VK_S)) {
					//velocity[1] += speed*dt;
					
					velocity.add(0,speed*dt);
					
					//posY+=velocity.getY();
					//posY+=dt*speed;
				}
				else {
					//velocity[1] += (0-velocity[1])*dt*accel;
					
					velocity.add(0,(0-velocity.getY())*dt*accel);
					
					//posY+=velocity.getY();
				}
				
				if(gc.getInput().isKey(KeyEvent.VK_A)) {
					//velocity[0] -= speed*dt;
					
					velocity.add(-speed*dt,0);
					
					//posX+=velocity.getX();
					////posX-=dt*speed;
				}
				else {
					//velocity[0] += (0-velocity[0])*dt*accel;
					
					velocity.add((0-velocity.getX()*dt*accel),0);
					
					//posX+=velocity.getX();
				}
				
				if(gc.getInput().isKey(KeyEvent.VK_D)) {
					//velocity[0] += speed*dt;
					
					velocity.add(speed*dt,0);
					
					//posX+=velocity.getX();
				}
				else {
					//velocity[0] += (0-velocity[0])*dt*accel;
					
					velocity.add((0-velocity.getX())*dt*accel,0);
					//System.out.println(velocity);
					//posX+=velocity.getX();
				}
				
	}


	public float getMaxspeed() {
		return maxspeed;
	}

	public void setMaxspeed(int maxspeed) {
		this.maxspeed = maxspeed;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}


}
