package specialtiles;

import java.awt.Color;
import java.awt.event.KeyEvent;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Level;
import Engine.gfx.Image;
import audio.SoundClip;

public class Sign extends GameObject {

	private Level level;
	
	private String[] text;
	
	private float speed = 2;
	
	private SoundClip textSound = new SoundClip("/audio/text2.wav");
	
	private Image arrow = new Image("/ui/arrow.png");
	private Image arrowS = new Image("/ui/arrowS.png");
	
	public Sign(float posX, float posY,Level level) {
		
		this.posX=posX;
		this.posY=posY;
		this.reading=false;
		
		this.width=1860;
		this.height=220;
		
		this.text = new String[]{"Welcome to the UNDULATE debug room.",

				"Click and drag your MOUSE in order to jump. Press ESCAPE to pause the game."
				
				,"I am a textbox. I display text. Press E to move the the next textbox."
				
				,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident."
				
				,"I can also display non-latin text."
				
				,"日経石惑躍全身馬懇惑年上近無別渾。" 
						+ "乗挙束庫渡磐泰鉄位状上病郵王生者原権聞神。 日経石惑躍全身馬懇惑年上近無別渾 乗挙束庫渡磐泰鉄位状上病郵王生者原権聞神。"
						
				,"Cool right?"};
				
		//};
		
		
				
	
		this.level = level;
		
		this.textSound.setVolume(-5f);
		
	
		
	}
	
	
	float iO,textBIndex = 0;
	int textIndex = 0;
	boolean textIn,direction,trans,transB = false;
	float moveX,moveY = 0;
	float trSpeed = 8.5f;
	float accelX,accelY=0;
	float AXL = 7.5f;
	public void update(GameContainer gc, float dt) {

		if((Math.abs(level.getP()[0].getPosX()-posX)<level.getTilesize()*5 && Math.abs(level.getP()[0].getPosY()-posY)<level.getTilesize()*5 && !trans && !transB) || reading) {
			textIn=true;

			if(direction)
				iO-=dt*2;
			else
				iO+=dt*2;
			
			if(iO>2.8)
				direction=true;
			if(iO<0)
				direction=false;
		
			if(gc.getInput().isKeyDown(KeyEvent.VK_E) && !reading) {
				textIndex=0;
				accelX=0; accelY=0;
				//reading=true;
				trans=true;
			}
			
			
			
		}
		else
			textIn=false;
		
		if(trans) {

			if(moveX<1) {
				moveX+=dt*trSpeed*accelX;
				accelX+=dt*AXL;
				if(moveX>1)
					moveX=1;
			}
			else {
				//moveX=1;
				moveY+=dt*trSpeed*accelY;
				accelY+=dt*AXL;
				if(moveY>=1) {
					moveY=1;
					trans=false;
					reading=true;
					accelX=0; accelY=0;
				}
					
			}
			
		}
		
		if(transB) {
			
			if(moveY>0) {
				moveY-=dt*trSpeed*accelX;
				accelX+=dt*AXL;
				if(moveY<0)
					moveY=0;
			}
			else {
				//moveY=0;
				moveX-=dt*trSpeed*accelY;
				accelY+=dt*AXL;
				if(moveX<=0) {
					moveX=0;
					transB=false;
					
				}
					
			}
			
		}
		
		
		
		if(Math.abs(level.getP()[0].getPosX()-posX)>level.getTilesize()*20) {
			
			if(reading || trans) {
				transB=true;		
			}
			reading=false;
			trans=false;
			textIndex=0;
			textBIndex=0;
		}
		
		if(reading) {
			if(gc.getInput().isKeyDown(KeyEvent.VK_E)) {
				textIndex++;
				textBIndex=0;
				if(textIndex>text.length-1) {
					textIndex=0;
					reading=false;
					trans=false;
					transB=true;
					//moveX=0;
					//moveY=0;
				}
			}
			if(textIndex>=0 && textBIndex<text[textIndex].length()) {
				if(textBIndex<text[textIndex].length()) {
					textBIndex+=speed;
				}
				else
					System.out.println();
				if((int)textBIndex%8==0)
					textSound.play();
				if(textBIndex>text[textIndex].length()) {
					textBIndex=text[textIndex].length();
				}
			}
		}

		
	}
	
	boolean done = false;
	
	
	int laO = 150;
	int la = laO;
	int arc = 20;
	int borderWO = 10;
	int borderW = borderWO;
	public void render(GameContainer gc, Renderer r) {
		
		
		xc = level.getXc()+(int)posX-level.getDraw();
		yc = level.getYc()+(int)posY-level.getDraw();
		
		//r.drawImage(sign, level.getXc()+(int)posX-level.getDraw(), level.getYc()+(int)posY-level.getDraw(), false, 0);
		
		if(textIn && !trans && !transB && !reading) {
			r.drawText("(E)", xc+3, yc-8+(int)iO, 0xffff4242);
		}
		
		if((trans || transB || reading) && !gc.isPaused()) {
			//r.fillSquare(gc.getWidth()/2, gc.getHeight()-la, (int)((gc.getWidth()/2-width)*moveX),(int)(height*moveY), 0xff010101);
			//r.drawSquare(gc.getWidth()/2, gc.getHeight()-la, (int)((gc.getWidth()/2-width)*moveX), (int)(height*moveY), 0xffffffff);
			
			int curx = (int)(width*moveX*(gc.getScale()/3));
			int cury = (int)(height*moveY*(gc.getScale()/3));
			
			borderW=(int) (borderWO*(gc.getScale()/3));
			la=(int) (laO*(gc.getScale()/3));
			
			r.box((int)(gc.getWidth()*gc.getScale()/2-(curx/2)), (int)(gc.getHeight()*gc.getScale()-(cury/2))-la, curx, cury+1, arc, arc,Color.WHITE);
			
			
			//r.box((int)(gc.getWidth()*gc.getScale()/2-(curx/2)), (int)(gc.getHeight()*gc.getScale()/2-(cury/2)), curx, cury+1, arc, arc,Color.WHITE);
			
			r.box((int)(gc.getWidth()*gc.getScale()/2-((curx-borderW)/2)), (int)(gc.getHeight()*gc.getScale()-((cury-borderW)/2))-la, curx-borderW, cury+1-borderW, arc, arc,Color.BLACK);
			

			if(textBIndex==text[textIndex].length()) {
				//r.drawImage(arrow, gc.getWidth()-width-arrow.getW()-1, gc.getHeight()-la+height-arrow.getH()-3+(int)(iO), false, 0);
				//arrow.setX((int) (1890*(gc.getScale()/3))); arrow.setY(100);
				if(gc.getScale()>=3) {
					arrow.setX((int) (gc.getWidth()*gc.getScale()-arrow.getW()-la/3.5f)); arrow.setY((int) (gc.getHeight()*gc.getScale()-la/2.5f-arrow.getH()+iO*(gc.getScale()+1)));
					r.swingImage(arrow);
				}
				else
				{
					arrowS.setX((int) (gc.getWidth()*gc.getScale()-arrowS.getW()-la/3.5f)); arrowS.setY((int) (gc.getHeight()*gc.getScale()-la/2.5f-arrowS.getH()+iO*(gc.getScale()+1)));
					r.swingImage(arrowS);
				}
				
			}
		}
		
		if(reading) {
			//r.drawText(text[textIndex].substring(0,(int)textBIndex), 14, gc.getHeight()-48, 0xffffffff); //screenbuffer
			
			r.fontText(text[textIndex].substring(0,(int)textBIndex), 50*gc.getScale()/3, 870*gc.getScale()/3,Color.WHITE);
		}
		
		
	}
	

}
