package Engine.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.gfx.Image;

public class TitleScreen extends GameObject {
	
	private Image logo = new Image("/logo.png");
	
	private int startX,startY;
	
	private boolean logoB = false;
	
	
	private ArrayList<UIPackage> menuButtons = new ArrayList<UIPackage>();
	
	
	private UIElement Play,Quit;
	private UIElement level1;
	
	
	
	private UIPackage menu;
	private UIPackage LevelSelect;
	
	public TitleScreen(int posX,int posY) {
		this.posX=posX;
		this.posY=posY;
		this.startX=posX;
		this.startY=posY;
		this.xc=0;
		this.yc=0;
		this.ppx=0;
		this.ppy=0;
		this.velocity= new Vec2(0,0);
		this.ground=false;
		this.tag="target";
		
		Play = new UIElement("Play",192,130,30,10,192,86,8,3,null,0xffffffff,0xffff4242,true,0);
		Quit = new UIElement("Quit",192,160,30,10,192,86,8,3,null,0xffffffff,0xffff4242,true,-2);
		level1 = new UIElement("click this to actually play :)",100,130,30,10,192,86,8,3,null,0xffffffff,0xffff4242,true,5);
		
		menu = new UIPackage(null,true,Play,Quit);
		LevelSelect = new UIPackage(menu,false,level1);
		
		Play.setParent(menu);
		Play.setChild(LevelSelect);
		LevelSelect.setParent(menu);
		
		menuButtons.add(menu);
		menuButtons.add(LevelSelect);
		
		
	}

	float logoS = 6;
	float logoD = 2.01f;
	boolean be = true;
	float v = -0.2f;
	public void update(GameContainer gc, float dt) {
		
		
		
		
		if(velocity.getX()<0.001 && velocity.getX()>0) {
			velocity.setX(0);
		}
		if(velocity.getY()<0.001 && velocity.getY()>0) {
			velocity.setY(0);
		}
		if(velocity.getX()>-0.001 && velocity.getX()<0) {
			velocity.setX(0);
		}
		if(velocity.getY()>-0.001 && velocity.getY()<0) {
			velocity.setY(0);
		}
		
		if(temp<logoS) {
			temp+=dt;
			logoB=true;
		}
		
		if(temp<logoS+logoD && temp>logoS) {
			logoB=false;
			velocity.add(0, 0.1);
			temp+=dt;
		}
		else {
			velocity.add(0,(0-velocity.getY())*dt);
		}
		
		
		
		
		
		if(velocity.getY()==0 && temp>logoS+logoD) {
			ground=true;
			//velocity.setX(0.2);
			
		}
		
		
		
		if(ground) {
			
			for(int i=0;i<menuButtons.size();i++) {
				menuButtons.get(i).update(gc, dt);
				if(gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
					if(!menuButtons.get(0).isPresent()) {
						menuButtons.get(1).setPresent(false);
						menuButtons.get(1).getParent().setPresent(true);
					}
					
					
				}
			}
		}
		
		
		posX+=velocity.getX();
		posY+=velocity.getY();
		
		
		this.setPpx((int)posX);
		this.setPpy((int)posY);
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		if(logoB) {
			r.drawImage(logo, 0, 0, false, 0);
		}
		
		if(ground) {
			for(int i=0;i<menuButtons.size();i++) {
				if(menuButtons.get(i).isPresent()) {
					menuButtons.get(i).render(gc, r);
				}	
			}
		}
		
	}
	
	
	public void reset() {
		velocity.set(0, 0);
		this.posX=startX;
		this.posY=startY;
		this.setPpx((int)posX);
		this.setPpy((int)posY);
		be=true;
		temp = 0;
		ground=false;
		logoB=false;
		menuButtons.get(0).setPresent(true);
		for(int i=1;i<menuButtons.size();i++) {
			menuButtons.get(i).setPresent(false);
		}
		
		
		
		
	}

}
