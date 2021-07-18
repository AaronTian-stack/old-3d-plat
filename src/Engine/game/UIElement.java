package Engine.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.gfx.Image;
import audio.SoundClip;
import objparser.Obj;

public class UIElement extends GameObject {
	
	private Obj test; //visual
	
	private int color, colorO, colorC;
	
	private int action;
	
	
	private boolean draw;
	private boolean clickable;
	
	private String text;
	
	private int iX, iY, vX, vY, width,height,textX, textY;
	
	private int nw, nh;
	
	private UIPackage child;
	private UIPackage parent;
	
	private TitleScreen cam;
	
	private SoundClip select= new SoundClip("/audio/select.wav");
	
	private SoundClip error= new SoundClip("/audio/error.wav");
	
	private SoundClip click = new SoundClip("/audio/click.wav");
	
	public UIElement(String text, int iX, int iY, int width, int height, int vX, int vY, int textX, int textY, Obj test, int colorO, int colorC, boolean click, int action) {
		
		this.action=action;
		this.clickable=click;
		this.colorO=colorO;
		this.colorC=colorC;
		this.test = test;

		this.iX=iX;
		this.iY=iY;
		this.vX=vX;
		this.vY=vY;
		this.textX=textX;
		this.textY=textY;
		//this.children=children;
		
		this.width=width;
		this.height=height;
		
		this.text=text;
		this.select.setVolume(-5f);
		
		this.nw=width;
		this.nh=height;
		
	}
	
	public UIElement(String text, int iX, int iY, int width, int height, int vX, int vY, int textX, int textY, Obj test, int colorO, int colorC, boolean click, int action,TitleScreen cam) {
		
		this.action=action;
		this.clickable=click;
		this.colorO=colorO;
		this.colorC=colorC;
		this.test = test;
		this.select.setVolume(-10f);
		this.iX=iX;
		this.iY=iY;
		this.vX=vX;
		this.vY=vY;
		this.textX=textX;
		this.textY=textY;
		//this.children=children;
		
		this.width=width;
		this.height=height;
		
		this.text=text;
		
		
		this.nw=width;
		this.nh=height;
		this.cam=cam;
	}
	

	boolean ERR = false;
	public void update(GameContainer gc, float dt) {
		
	
		if(test!=null)
			test.getPoly().rotate(1,1,1,test.getPoly().getInflection().x,test.getPoly().getInflection().y,test.getPoly().getInflection().z);
		
		
		
		if(gc.getInput().getMouseX()>=iX-width && gc.getInput().getMouseX()<=iX+width
				&& gc.getInput().getMouseY()>=iY-height && gc.getInput().getMouseY()<=iY+height && clickable) {
			
			if(color!=colorC)
				select.play();
			
			this.width = nw+2;
			this.height = nh+2;
			
			
			
			this.setColor(colorC);
			
			//check for input. if clicked, then must show the children instead 
			
			if(gc.getInput().isButtonDown(1)) {
				
				if(this.child!=null) { //host button
					click.play();
					parent.setPresent(false);
					child.setPresent(true);
					
				}
				else { //this button actually changes a setting. 
					
					//float l = gc.getScale();
					//int h = gc.getHeight();
					
					//PrintWriter pw = null;
					//try {
					//	
					//	pw = new PrintWriter(new BufferedWriter(new FileWriter(gc.getConfig())));
					//} catch (IOException e) {
					//	// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
					
					
					switch(action) {
					case -2: //quit program
						
						System.exit(0);
						
						break;
					case -1: //quit to menu
						click.play();
						cam.reset();
						gc.setGameState(3);
						

						break;
					case 0: //switch between windowed, borderless
						click.play();
						if(gc.isBorderless()) {
							gc.getWindow().setborder(gc, false);
							gc.setBorderless(false);
							this.setText("windowed");
							this.setTextX(19);
						}
						else {
							gc.getWindow().setborder(gc, true);
							gc.setBorderless(true);
							this.setText("borderless");
							this.setTextX(21);
						}
						
						
						
						break;
					case 1: //increase res
						
						if(gc.getScale()*gc.getHeight()<gc.getWindow().getScreenHeight()) {
		
							//gc.getWindow().resize(gc,(gc.getScale()*gc.getHeight()+72)/gc.getHeight());
							click.play();
							gc.getWindow().resize(gc,1);
	
						}
						else {
							error.play();
							ERR = true;
							
						}
						
						//pw.write(gc.getWidth()+" "+gc.getHeight()+"\n");
						//pw.println(gc.getScale());
						//pw.println(gc.isBorderless());
						//pw.close();
						
	
						break;
					case 2: //decrease res
						
						if(gc.getScale()>1) {

							//gc.getWindow().resize(gc,(gc.getScale()*gc.getHeight()-72)/gc.getHeight());
							click.play();
							gc.getWindow().resize(gc,-1);

						}
						else {
							error.play();
							ERR = true;
							
						}
						
						//pw.write(gc.getWidth()+" "+gc.getHeight()+"\n");
						//pw.println(gc.getScale());
						//pw.println(gc.isBorderless());
						//pw.close();

						break;
					case 3: //increase aud
						
						if(gc.getMastervolume()<10) {
							click.play();
							gc.addMastervolume(1);
						}
						else {
							error.play();
							ERR = true;
							
						}
						
						break;
					case 4: //decrease aud
						
						if(gc.getMastervolume()>0) {
							click.play();
							gc.addMastervolume(-1);
						}
						else {
							error.play();
							ERR = true;
							
						}
						
						
						break;
					
					case 5: //menu play
						click.play();
						System.out.println("playing...");
						gc.setGameState(1);
						
						break;
						
					case 6: //credits
						
						break;
						
					}
					
				

					
					
					
					
				}
				
				
			}
			
			}
			
			
		
		
		else { //mouse is not inside button
			

			this.width = nw;
			this.height = nh;
			
			
			
			this.setColor(colorO);
		}
		
		if(ERR) {
			temp+=dt;
			if(temp>0.2) {
				temp=0;
				ERR=false;
			}
				
		}
	
		
	}

	int b = 1;
	public void render(GameContainer gc, Renderer r) {
		
		
		
		//r.setPixel(iX, iY, color);
		if(this.test!=null) {
			if(gc.getInput().getMouseX()>=iX-width && gc.getInput().getMouseX()<=iX+width
			&& gc.getInput().getMouseY()>=iY-height && gc.getInput().getMouseY()<=iY+height) {
				r.drawPoly(vX, vY, test.getPoly(), test, color);
				this.draw=false;
			}
			else {
				this.draw=true;
			}
		}
		
		if(ERR) {
			b*=-1;
		
			
			r.fillSquare(iX+b, iY, width, height, 0xff010101);
			r.drawText(text, iX-textX+b, iY-textY, color);
			r.drawSquare(iX+b, iY, width, height, color);

		}
		else {
			r.fillSquare(iX, iY, width, height, 0xff010101);
			r.drawText(text, iX-textX, iY-textY, color);
			r.drawSquare(iX, iY, width, height, color);
		}
		

	}
	
	

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public UIPackage getChild() {
		return child;
	}

	public void setChild(UIPackage child) {
		this.child = child;
	}

	public UIPackage getParent() {
		return parent;
	}

	public void setParent(UIPackage parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public int getTextX() {
		return textX;
	}


	public void setTextX(int textX) {
		this.textX = textX;
	}


	public int getTextY() {
		return textY;
	}


	public void setTextY(int textY) {
		this.textY = textY;
	}


	public int getiX() {
		return iX;
	}


	public void setiX(int iX) {
		this.iX = iX;
	}


	public int getiY() {
		return iY;
	}


	public void setiY(int iY) {
		this.iY = iY;
	}


	public int getvX() {
		return vX;
	}


	public void setvX(int vX) {
		this.vX = vX;
	}


	public int getvY() {
		return vY;
	}


	public void setvY(int vY) {
		this.vY = vY;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}

}
