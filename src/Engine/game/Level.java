package Engine.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.vfx.ParticleManager;
import Engine.gfx.Image;
import audio.SoundClip;
import specialtiles.Grass;
import specialtiles.Mirror;
import specialtiles.Sign;



public class Level extends GameObject {
	
	private int[][] level;
	
	
	private int tilesize;
	private int draw;
	private GameObject[] pA;

	private boolean[][] inside;

	private ArrayList<GameObject> animatedTiles = new ArrayList<GameObject>(); 
	
	private ArrayList<GameObject> signs = new ArrayList<GameObject>(); 

	private ArrayList<GameObject> mirror = new ArrayList<GameObject>(); 
	
	private Image cross = new Image("/tiles/brickBWR.png");
	private Image air = new Image("/asd.png"); //blending stress test
	
	private Image rampright = new Image("/tiles/rampright.png");;
	private Image rampleft = new Image("/tiles/rampleft.png");
	private Image dirt = new Image("/tiles/dirt3.png");
	
	private Image crate  = new Image("/tiles/crate.png");
	private Image glass = new Image("/tiles/window.png");
	private Image lava = new Image("/tiles/lava.png");
	private Image lava2 = new Image("/tiles/lava2.png");
			
			
	private Image warning = new Image("/ui/warning.png");
	private Image debug = new Image("/tiles/1.png");
	private Image test = new Image("/debug.png");
	
	private Image sign = new Image("/tiles/sign.png");
	private Image terminal = new Image("/tiles/terminal.png");
	
	private SoundClip impact= new SoundClip("/audio/impact2.wav");
	private SoundClip impact2= new SoundClip("/audio/impact.wav"); 
	private SoundClip splash= new SoundClip("/audio/splash.wav"); 
	
	private float bounce = 1.5f;
	
	private float lavabounce = -20f;
	
	private float jumpPadBounce = -20f;
	
	public Level(String filepath, int tilesize,GameObject ... pA) throws IOException {
		
		
		this.impact.setVolume(-5f);
		
		this.impact2.setVolume(-5f);
		
		this.tag = "level";
		
		this.setTilesize(tilesize);
		
		InputStream in = this.getClass().getResourceAsStream(filepath);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		width = Integer.parseInt(st.nextToken());
		height = Integer.parseInt(st.nextToken());
		System.out.println("LEVEL "+width+" "+height);
		this.level = new int[height][width];
		
		for(int i=0;i<height;i++) {
			String a = br.readLine();
			//System.out.println(a);
			int j = 0;
			for (String s : a.split(",")) {
				//System.out.println(s);
				this.level[i][j]=Integer.parseInt(s);
				j++;
			}
			
		}
		System.out.println("done reading level");

		inside = new boolean[height][width];

		for(int i=0;i<height;i++) { //make special tiles
			//System.out.println(i);
			for(int j=0;j<width;j++) {
				
				switch(level[i][j]) {
				
				case 4:
					animatedTiles.add(new Grass(j*tilesize,i*tilesize,this,inside));
					break;
					
				case 6:
					particleManagers.add(new ParticleManager(this,j*tilesize,i*tilesize,0,2,2,5,1,3,0.2f,1,-90,40,0.1f,3f,0.5f,2.0f,true,0,0xffBF170E,0xff571412,0xffEC530E)); 
					break;
					
				case 8:
					signs.add(new Sign(j*tilesize,i*tilesize,this));
					break;
					
				case 9: //must be rectangle
					
					int Mwidth = j;
					int Mheight = i;
					int w = 0;
					int h = 0;
					
					while(level[i][Mwidth]==9 && Mwidth<width-1) {
						Mwidth++;
						
					}
					w=Mwidth-j;
					
					while(level[Mheight][j]==9 && Mheight<height-1) {
						Mheight++;
						
					}

					h=Mheight-i;
					
					System.out.println("MW "+Mwidth);
					System.out.println("MH "+Mheight);
					
					for(int k=i;k<Mheight;k++) {
						for(int l=j;l<Mwidth;l++) {
							level[k][l]=-1;
						}
					}
					
					//System.out.println(Arrays.toString(level[i]));
					
					mirror.add(new Mirror(j*tilesize,i*tilesize+1,w*tilesize,h*tilesize,this));
					break;
				
				}
				
				
			}
		}
		
		System.out.println("done creating objects");
		
		this.pA=pA;
		
		this.draw=tilesize/2;
		
	

		
	}

	
	
	float lavaT,lavaT2 = 0;
	boolean lavaTB = false;
	float lavaR = 2;
	
	
	
	public void update(GameContainer gc, float dt) {
		
		
		if(!pA[0].tag.equals("target"))
				pA[0].update(gc, dt);
		
		if(!lavaTB)
			lavaT+=dt;
		else
			lavaT2+=dt;
		
		
		
		if(lavaT>(lavaR)) { //(int)(Math.random() * (max - min + 1) + min) 
			lavaT=0;
			lavaTB=true;
	
		}
		
		if(lavaT2>(lavaR)) {
			lavaT2=0;
			lavaTB=false;
		}
		
		
		for (boolean[] row: inside)
		    Arrays.fill(row, false);

		for(GameObject p: pA) {
			collide(p,dt);

			if(!p.ground && !p.water)
				p.setAirtime(p.getAirtime()+dt);
			else
				p.setAirtime(0);
			
			if(p.ramp)
				p.setAirtime(0);
		}
		
		for(GameObject p : animatedTiles) {
			p.update(gc, dt);
		}
		
		for(GameObject p : signs) {
			p.update(gc, dt);
		}
		
		for(GameObject p : mirror) {
			p.update(gc, dt);
		}
	
		for(int i=0;i<particleManagers.size();i++) {
			
			particleManagers.get(i).update(gc, dt);
			for(int j=0;j<particleManagers.get(i).getParticles().size();j++) {
				collide(particleManagers.get(i).getParticles().get(j),dt);
			}
	
		}
		
		for(int i=0;i<particleManagers.size();i++) {
			if(!particleManagers.get(i).isRepeat() && particleManagers.get(i).getParticles().size()==0) {
				particleManagers.remove(i);
				if(i>0)
					i--;
				System.out.println("removed particle manager");
			}
			
		}
		
	
		
	}
	
	float airmax = 0.8f;
	
	public void collide(GameObject p,float dt) {

		p.ground=false;
		p.ramp=false;
		p.water=false;
		p.air=false;
		
		float pL = p.getPosX()-p.width;
		float pR = p.getPosX()+p.width;
		float pU = p.getPosY()-p.height;
		float pD = p.getPosY()+p.height;
		
		
		
		int xL = (int) (pL/getTilesize()+0.5);
		int xR = (int) (pR/getTilesize()+0.5);
		int yU = (int) (pU/getTilesize()+0.5);
		int yD = (int) (pD/getTilesize()+0.5);
		
		
		
		boolean  moved = false;
		
		
		for(int i=yD;i>=yU;i--) {
			
			int yUT = i*getTilesize()-draw;
			int yDT = i*getTilesize()+draw;

			for(int j=xL;j<=xR;j++) {
				if(i>=0 && i<getLevel().length && j>=0 && j<getLevel()[i].length) {
					
					int xLT = j*getTilesize()-draw;
					int xRT = j*getTilesize()+draw;
					
					int d = intersection(i,j,p,pL,pR,pU,pD,xLT,xRT,yUT,yDT);

					if(p.tag.equals("player")) {
							inside[i][j]=true;
					}

					switch(getLevel()[i][j]) {
					
					case -1:
						if(p.airtime!=0) {
							playerP(p,-90,getLevel()[i][j]);
							p.setVelocity(p.getVelocity().getX()/4, p.getVelocity().getY()/4);
							p.setGravity(p.getGravity()/4);
							
						}
						p.water=true;
						
						
						break;
					
					case 4:
					
					case 0:
						p.air=true;
						break;

					case 9:
					
					case 7: //crate
					
					case 5: //dirt
					
					case 1:
						
						
						
						switch(d) {
						case 0:
							break;
						case 1:
							if(p.getTag().equals("player")) {
								if(p.velocity.getY()!=0)
									impact.play();
								if(p.airtime>airmax) {
									playerP(p,-90,getLevel()[i][j]);
									p.setAirtime(0);
									impact2.play();
								}
							}
							
							
							p.setGround(true);
							p.setGravity(0);
				
							
							p.setPosY(yUT-p.height);
							p.setVelocity(p.getVelocity().getX(), 0);
							break;
						case 2:
							p.setGround(false);
							
							if(p.tag.equals("player")) {
								impact.play();
								if(p.airtime>airmax) {
									playerP(p,0,getLevel()[i][j]);
									p.setAirtime(0);
									impact2.play();
								}
							}
							
							
							p.setPosX(xRT+p.width);
							
							
							if(!moved) {
								p.setVelocity(-p.getVelocity().getX()/bounce,p.getVelocity().getY());
								
								moved=true;
							}
							if(p.velocity.getX()==0)
								p.velocity.setX(0.1);
							break;
						case 3:
							p.setPosY(yDT+p.height);
							p.setGround(false);
							if(p.tag.equals("player")) {
								impact.play();
								if(p.airtime>airmax) {
									playerP(p,90,getLevel()[i][j]);
									p.setAirtime(0);
									impact2.play();
								}
							}
							p.setVelocity(p.getVelocity().getX(), 0);

							break;
						case 4:
							p.setGround(false);
							if(p.tag.equals("player")) {
								impact.play();
								if(p.airtime>airmax) {
									playerP(p,180,getLevel()[i][j]);
									p.setAirtime(0);
									impact2.play();
								}
							}
							p.setPosX(xLT-p.width);
							
							
							if(!moved) {
								
								
								p.setVelocity(-p.velocity.getX()/bounce,p.getVelocity().getY());
								moved=true;
							}
							if(p.velocity.getX()==0)
								p.velocity.setX(-0.1);
							break;
						}
						
						
					
		
						break;
							
							
						
					case 2: //ramp right

						xLT = j*getTilesize()-draw;
						//xRT = j*getTilesize()+draw;
						yUT = i*getTilesize()-draw;
						//yDT = i*getTilesize()+draw;
	
						//check if inside ramp by calculating relative x and y to tile and adding them see if they are greater than tile width (this is based off lower right of character
						// so pD and pR. should just mod by tilesize
						
						//System.out.println("SR "+(pR+width));
						//System.out.println("SW "+(pD+height));
						
						float rX = pR-xLT;
						float rY = pD-yUT;
						
						
						//System.out.println("R "+rX+" "+rY);

						if(rX+rY-tilesize>0) { //inside
							
						
							
							if(p.getVelocity().getY()>6) {
								p.getVelocity().setY(0);
							}
							//System.out.println("inside");

							p.setGravity(0);
							//System.out.println("AYO "+-(rX-(tilesize-rY)));
							
							p.addPosY(-(rX-(tilesize-rY)));
							
							//p.setPosY(  yUT            -(rX-(tilesize-rY))             );
								
							
							p.addVelocity(-0.25, 0);
							p.setRamp(true);

							//p.setGround(true);

							
						}
						else {
							
							
							p.setRamp(false);
							
						}
						
	
						
						break;
						
						
					case 3: //ramp left
						
						//xLT = (j+1)*getTilesize()-draw;
						xRT = j*getTilesize()+draw;
						yUT = i*getTilesize()-draw;
						//yDT = i*getTilesize()+draw;
	
						//check if inside ramp by calculating relative x and y to tile and adding them see if they are greater than tile width (this is based off lower right of character
						// so pD and pR. should just mod by tilesize
						
						//System.out.println("SR "+(pR+width));
						//System.out.println("SW "+(pD+height));
						
						
						rX = xRT-pL;
						
						//rX = pL-xRT;
						
						rY = pD-yUT;
						
						
						//System.out.println("R "+rX+" "+rY);

						if(rX+rY>tilesize) { //inside
							
							
							if(p.getVelocity().getY()>6) {
								p.getVelocity().setY(0);
							}
							//System.out.println("INSIDE");

							p.setGravity(0);
							//System.out.println("AYO "+(-(rX-(tilesize-rY))));
							
							//p.setPosY(tilesize-rX+yDT);
							
							if(rX<tilesize-1)
								p.addPosY(-(rX-(tilesize-rY)));
							
							
							p.addVelocity(0.25, 0);
							p.setRamp(true);

							//p.setGround(true);

							
						}
						else {
							
							
							p.setRamp(false);
							
						}
						
	
						
						
						
						break;
						
					case 6: //lava
						if(p.tag.equals("player")) {
							
						switch(d) {
						case 0:
							break;
						case 1:
							lavaP(p,-90);
							impact.play();
							p.setAirtime(0);
							p.setVelocity((Math.random() * (8.5 - -8.5 + 1) + -8.5) , -8.5);
							p.setGravity(lavabounce);
							p.setPosY(yUT-p.height);
							break;
						case 2:
							p.setPosX(xRT+p.width);
							lavaP(p,0);
							p.setAirtime(0);	
							p.setVelocity(8.5,(Math.random() * (8.5 - -8.5 + 1) + -8.5));
							//p.setGravity(lavabounce);
							impact.play();
							break;
						case 3:
							p.setPosY(yDT+p.height);						
							lavaP(p,90);
							p.setAirtime(0);
							p.setVelocity((Math.random() * (8.5 - -8.5 + 1) + -8.5) , 8.5);
							p.setGravity(-lavabounce);
							impact.play();

							break;
						case 4:
							p.setPosX(xLT-p.width);
							//p.setVelocity(0,p.getVelocity().getY());
							lavaP(p,180);
							p.setAirtime(0);
							p.setVelocity(-8.5,(Math.random() * (8.5 - -8.5 + 1) + -8.5));
							//p.setGravity(lavabounce);
							impact.play();

							break;
						}
						
						}
						
						
					break;
						
					
					
					}
						
					}
					
					
				
				
			
				
				}
				
				
				
			}
			
		
	}
		

	
		
	
	
	public int intersection(int i,int j,GameObject p,float pL,float pR,float pU,float pD,int xLT,int xRT,int yUT,int yDT) {

		boolean top = false,right = false,bottom = false,left = false; // 1 2 3 4
	
		
		if(p.getPosY()<yUT && i-1>=0 && (getLevel()[i-1][j]==0 || getLevel()[i-1][j]==4 || getLevel()[i-1][j]==-1)) { //top
			top=true;
			//ret[0]=1;
		}
		if(p.getPosY()>yDT && i+1<getLevel().length && (getLevel()[i+1][j]==0 || getLevel()[i+1][j]==4 || getLevel()[i+1][j]==-1)) { //bottom
			bottom=true;
			//ret[1]=3;

		}
		if(p.getPosX()<xLT && j-1>=0 && (getLevel()[i][j-1]==0 || getLevel()[i][j-1]==4 || getLevel()[i][j-1]==-1)) { //left
			left=true;
			//ret[2]=4;
		}
		if(p.getPosX()>xRT && j+1<getLevel()[i].length && (getLevel()[i][j+1]==0 || getLevel()[i][j+1]==4 || getLevel()[i][j+1]==-1)) { //right
			right=true;
			//ret[3]=2;
		}
		
		double timeXCollisionR =  ((pL - xRT) / -p.getVelocity().getX()); //right side of tile
		double timeYCollisionD =  ((yDT - pU) / -p.getVelocity().getY());
		double timeXCollisionL =  ((xLT-pR) / -p.getVelocity().getX());
		double timeYCollisionU =  ((yUT-pD) / -p.getVelocity().getY());
		
		if(Double.isInfinite(timeXCollisionR) || Double.isNaN(timeXCollisionR))
			timeXCollisionR=0;
		
		if(Double.isInfinite(timeYCollisionD) || Double.isNaN(timeYCollisionD))
			timeYCollisionD=0;
		
		if(Double.isInfinite(timeXCollisionL) || Double.isNaN(timeXCollisionL))
			timeXCollisionL=0;
		
		if(Double.isInfinite(timeYCollisionU) || Double.isNaN(timeYCollisionU))
			timeYCollisionU=0;
		
		if(top && right && left && right) {
			System.out.println("bruh");
		}
	

		if(top && right) {
			
			if(timeXCollisionR<=timeYCollisionU && p.getVelocity().getX()<0) { //move right
				//System.out.println("RIGHT");
				//ret[0]=2;
				return 2;
			}
			else if(p.getVelocity().getY()>0){ //move up
				//System.out.println("UP");
				//ret[0]=1;
				return 1;
			}
			
		}
		
		if(top && left) {
			
			if(timeXCollisionL<timeYCollisionU && p.getVelocity().getX()>0) { //move left
				//System.out.println("LEFT");
				//ret[0]=4;
				return 4;
			}
			else if(p.getVelocity().getY()>0) { //move up
				//System.out.println("UP");
				//ret[0]=1;
				return 1;
			}
			
		}
		
		if(bottom && right) {
			
			if(timeXCollisionR<timeYCollisionD) { //move right
				//System.out.println("RIGHT");
				//ret[0]=2;
				
				
				return 2;
			}
			else if(p.getVelocity().getY()>0){ //move down
				//System.out.println("DOWN");
				//ret[0]=3;
				return 3;
			}
				
			
		}
		
		if(bottom && left) {
			if(timeXCollisionL<timeYCollisionD) { //move left
				//System.out.println("LEFT");
				//ret[0]=4;
				return 4;
			}
			else if(p.getVelocity().getY()>0) { //move down
				
				//System.out.println("DOWN");
				//ret[0]=3;
				return 3;
			}
			
		}
		
		
		if(top) {
			//System.out.println("ONLY UP");

			//ret[0]=1;
			
			return 1;
		}
		if(bottom) {
			//System.out.println("ONLY DOWN");
			//ret[0]=3;
			return 3;
		}
		if(left) {
			//System.out.println("ONLY LEFT");
			
			//ret[0]=4;
			
			
			return 4;
		}
		if(right) {
			//System.out.println("ONLY RIGHT");
	
			//ret[0]=2;
			return 2;
		}
		
		//ret[0]=-1;
		return 0;
		

	}

	@Override
	public void render(GameContainer gc, Renderer r) {

		
		
		
		int x,y,x1,y1;

		for (int i=0;i<getLevel().length;i++)
	      {
	         for (int j=0;j<getLevel()[i].length;j++)
	         {
	        	x=(j)*getTilesize();
	            y=(i)*getTilesize();
	            x1 = x-draw;
	            y1 = y-draw;
	            
	            
	        
	            
	            switch(getLevel()[i][j]) {
	            
	            case 0:
	            	x1++;
	            	y1++;
	            	//r.drawImage(air, xc+x1, yc+y1, false, 0);
	            	break;
	            case 1:      	
	            	r.drawImage(cross, xc+x1, yc+y1, false, 0);
	            	//r.drawImage(asd, xc+x1, yc+y1, false, 0);
	            	

	            	break;
	            	
	            case 2:
	            	
	            	r.drawImage(rampright, xc+x1, yc+y1, false, 0);
	            	
	            	break;
	            	
	            case 3:
	            	
	            	r.drawImage(rampleft, xc+x1, yc+y1, false, 0);
	            	
	            	break;
	            	
	            case 4: //grass
	            	
	            	break;
	            	
	            	
	            case 5:
	            	r.drawImage(dirt, xc+x1, yc+y1, false, 0);
	            	break;
	            	
	            case 6:
	            	
	            	if(lavaTB)
	            		r.drawImage(lava, xc+x1, yc+y1, false, 0);
	            	else
	            		r.drawImage(lava2, xc+x1, yc+y1, false, 0);
	            	break;
	            
	            case 7: //crate
	            	
	            	r.drawImage(crate, xc+x1, yc+y1, false, 0);
	            	
	            	break;
	            	
	            case 8:
	            	r.drawImage(sign, xc+x1, yc+y1, false, 0);
	            	break;
	            	
	            case 10:
	            	r.drawImage(glass, xc+x1, yc+y1, false, 0);
	            	break;
	         
	          
	            	
	            	
	            }
	            
	            
	         }
	      }

		
		if(!pA[0].tag.equals("target"))
			pA[0].render(gc, r);
		
		for(GameObject j : animatedTiles) {
			j.render(gc, r);
		}
		
		
		for(ParticleManager p : particleManagers) {
			p.render(gc, r);
		}
		
		for(GameObject j : mirror) {
			j.render(gc, r);
		}
		
		for(GameObject j : signs) {
			j.render(gc, r);
		}
	
		
		int bruh = (int) (pA[0].getPosX()/getTilesize()+0.5);
		int bruh2 = (int) (pA[0].getPosY()/getTilesize()+0.5);
		int size = 20;
		int size2=7;
		
		for(int i=bruh2;i<bruh2+size;i++) {
			//if(level[i][bruh2]!=6 && level[i][bruh2]!=0) {
				//System.out.println("breaking");
			//	break;
			//}
			if(i>=level.length || i<0 || bruh<0 || bruh>=level[0].length || (level[i][bruh]!=0 && level[i][bruh]!=6) )
				break;
			
			if(level[i][bruh]==6 && i-bruh2>size2) {
				//System.out.println(level[i][bruh]);
				r.drawImage(warning, pA[0].getPpx()+xc-warning.getW()/2, gc.getHeight()-warning.getH()-2, false, 0);
				break;
			}
		}
		
		
		
	
	
	
	}
		
		
		
		
		
		

		
	

	public GameObject[] getP() {
		return pA;
	}


	public int[][] getLevel() {
		return level;
	}

	public void setLevel(int[][] level) {
		this.level = level;
	}

	public int getTilesize() {
		return tilesize;
	}

	public void setTilesize(int tilesize) {
		this.tilesize = tilesize;
	}



	public int getDraw() {
		return draw;
	}



	public void setDraw(int draw) {
		this.draw = draw;
	}
	
	public void playerP(GameObject p,float o,int i){
		switch(i) {
		
		case 1:
			particleManagers.add(new ParticleManager(this,p.posX,p.posY,1,1,7,12,1,3,0.2f,1,o,40,2,0.5f,1f,0,true,0.01f,0xffffffff)); 
			break;
			
		case -1:
			splash.setVolume(-(1/p.airtime)*5);
			splash.play();
			particleManagers.add(new ParticleManager(this,p.posX,p.posY,1,1,7,12,1,3*p.airtime,0.2f,p.airtime*1.5f,o,40,0.5f,10f,1f,0,true,0.01f,0xff3E80BD,0xff2F5A82)); 
			break;
			
		}
		 
		
	}
	
	public void lavaP(GameObject p, float o){
		particleManagers.add(new ParticleManager(this,p.posX,p.posY,1,1,5,10,1,3,0.2f,1,o,40,2,0.5f,0.25f, 0,true,0.01f,0xffBF170E,0xff571412,0xffEC530E)); //sparks
		particleManagers.add(new ParticleManager(this,p.posX,p.posY,2,4,6,10,0.2f,1.5f,0.1f,0.2f,o,35,0.2f,2f,-0.1f, 0,false,0.01f,0xff505050,0xff707070,0xff909090)); //smoke
		if(o==0)
			o=180;
		else if(o==180)
			o=0;
		p.particleManagers.add(new ParticleManager(p,p.posX,p.posY,1,3,1,1,0.2f,1.5f,0.5f,1f,o,35,3f,2f,-0.1f, 0,false,1,0xff505050,0xff707070,0xff909090));
	}



}
