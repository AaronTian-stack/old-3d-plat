package Engine.game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import Engine.AbstractGame;
import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.vfx.Lightmap;
import objparser.Obj;

public class GameManager extends AbstractGame {
	
	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ArrayList<UIPackage> UIObjects = new ArrayList<UIPackage>();
	
	private ArrayList<GameObject> menuObjects = new ArrayList<GameObject>();
	
	private UIElement ResDisplay;
	private UIElement AudDisplay;
	private UIElement WindowType;

	public GameManager() {

		Player p = new Player(0,0,16,16,0xffff4242); //0xFFB84995
		Level level = null;
		try {
			level = new Level("/levels/test2.txt",16,p);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Lightmap lightmap = new Lightmap(level);
		//Parallax b = new Parallax(p,"/background/o.png",0,150,10);
		Camera camera = new Camera(level,10,lightmap);
		
		//gameObjects.add(b); //testing...
		gameObjects.add(level);
		//gameObjects.add(p);
		gameObjects.add(lightmap);
		gameObjects.add(camera);
		
		
		TitleScreen c = new TitleScreen(320,-1000);
		Parallax ba = new Parallax(c,"/background/mountain3.png",0,140,2);
		Level levelT = null;
		try {
			levelT = new Level("/levels/title.txt",16,c);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Camera cameraT = new Camera(levelT,100);
		
		
		menuObjects.add(ba);
		menuObjects.add(levelT);
		menuObjects.add(c);
		menuObjects.add(cameraT);
		
		
		int size = 85;
		
		Obj hightorus = null;
		Obj cog = null;
		Obj torus = null;
		Obj ico = null;
		try {
			hightorus = new Obj("/model/hightorus.txt",size,0,0,0, 0xffff4242,0,0,0,null,0,1);
			cog = new Obj("/model/cog.txt",size+10,0,0,0, 0xffff4242,0,0,0,null,0,1);
			torus = new Obj("/model/torus.txt",size,0,0,0, 0xffff4242,0,0,0,null,0,1);
			ico = new Obj("/model/ico.txt",size,0,0,0, 0xffff4242,0,0,0,null,0,1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		int vx = 320;
		int vy = 118;
		int d = 80;
		
		UIElement Button1 = new UIElement("video",320-30,180+d,20,10,vx,vy,12,3,hightorus,0xffffffff,0xffff4242,true,0); 
		UIElement Button2 = new UIElement("audio",320+30,180+d,20,10,vx,vy,11,3,torus,0xffffffff,0xffff4242,true,0); //name box w h vx vy
		
		UIElement Quit = new UIElement("exit to title",320,180+d+40,40,10,vx,vy,25,3,ico,0xffffffff,0xffff4242,true,-1,c); //name box w h vx vy

		UIPackage Main = new UIPackage(null,true,cog,vx,vy,Button1, Button2,Quit);
		
		UIElement IncreaseRes = new UIElement(">",320+60,180-20,10,10,vx,vy,2,3,null,0xffffffff,0xffff4242,true,1); 
		UIElement DecreaseRes = new UIElement("<",320-60,180-20,10,10,vx,vy,2,3,null,0xffffffff,0xffff4242,true,2); 
		
		ResDisplay = new UIElement("",320,180-20,40,10,vx,vy,17,3,null,0xffffffff,0xffff4242,false,0); 
		
		
		WindowType = new UIElement("windowed",320,180+20,60,10,vx,vy,19,3,null,0xffffffff,0xffff4242,true,0); 

		UIPackage Resolution = new UIPackage(Main,false,IncreaseRes, DecreaseRes,ResDisplay,WindowType);
		
		UIElement IncreaseAud = new UIElement(">",320+60,180,10,10,vx,vy,2,3,null,0xffffffff,0xffff4242,true,3); 
		UIElement DecreaseAud = new UIElement("<",320-60,180,10,10,vx,vy,2,3,null,0xffffffff,0xffff4242,true,4); 
		
		AudDisplay = new UIElement("",320,180,40,10,vx,vy,4,3,null,0xffffffff,0xffff4242,false,0); 
		
		UIPackage Audio = new UIPackage(Main,false,IncreaseAud, DecreaseAud,AudDisplay);
		
		
		
		Button1.setChild(Resolution);
		Button1.setParent(Main);

		IncreaseRes.setParent(Main);
		DecreaseRes.setParent(Main);
		ResDisplay.setParent(Main);
		
		Button2.setChild(Audio);
		Button2.setParent(Main);
		
		IncreaseAud.setParent(Main);
		DecreaseAud.setParent(Main);
		
		UIObjects.add(Main);
		UIObjects.add(Resolution);
		UIObjects.add(Audio);
		
	}
		
	public GameObject getObject(String tag) {	
		for(int i=0;i<gameObjects.size();i++) {
			if(gameObjects.get(i).getTag().equals(tag)) {
				return gameObjects.get(i);
			}
		}
		return null;
	}
	
	
	@Override
	public void init(GameContainer gc) {
		
		
		if(gc.isBorderless()) {
			WindowType.setText("borderless");
			WindowType.setTextX(21);
		}
		else {
			WindowType.setText("windowed");
			WindowType.setTextX(19);
		}
	
		
	}

	public void update(GameContainer gc, float dt) {
	
		
		switch(gc.getGameState()) { //0 pause 1 play 2 title 
		case 0:
			for(int i=0;i<UIObjects.size();i++) {
				if(UIObjects.get(i).isPresent()) {
					UIObjects.get(i).update(gc, dt);  //change this
					if(gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
						
						UIObjects.get(i).setPresent(false);
						if(UIObjects.get(i).getParent()!=null)
							UIObjects.get(i).getParent().setPresent(true);
						else 
							gc.setGameState(1);					
						
						
						
					}
				
				
				}
				ResDisplay.setText((int)(gc.getWidth()*gc.getScale()+0.5)+"x"+(int)(gc.getHeight()*gc.getScale()+0.5));
				ResDisplay.setTextX(ResDisplay.getText().length()+8);
				AudDisplay.setText(""+gc.getMastervolume());
				AudDisplay.setTextX(AudDisplay.getText().length()+1);
				
				
				
			}
			break;
		case 1:

			for(int i=0;i<gameObjects.size();i++) {
				gameObjects.get(i).update(gc, dt);
				if(gameObjects.get(i).isDead()) {
					gameObjects.remove(i);
					i--;
				}
			}
			
			
			if(gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
				UIReset();
				gc.setPaused(true);

			}
			else
				gc.setPaused(false);

			break;
		case 2:
			
			for(int i=0;i<menuObjects.size();i++) {
				menuObjects.get(i).update(gc, dt);
			}
			

			break;

			
		}
		
		
		
	}
	
	
	public void render(GameContainer gc, Renderer r) {
		
		
		switch(gc.getGameState()) { //0 pause 1 play 2 title 
		case 0:
			
			
			
			r.drawFrame(30);
			
				
			for(int i=0;i<UIObjects.size();i++) {
				if(UIObjects.get(i).isPresent())
				UIObjects.get(i).render(gc, r);
			}
			
			break;
		case 1:
			
			for(int i=0;i<gameObjects.size();i++) {
				gameObjects.get(i).render(gc, r);
			
			}
			

			if(gc.isPaused()) {
				System.out.println("saved frame");
				r.pFrame2();
				gc.setGameState(0);
				gc.setPaused(false);
				while(gc.getZoom()!=0) {
					r.scale(gc, 1/gc.getZoomScale());
				}
			}
			

			break;
		case 2:
			
			for(int i=0;i<menuObjects.size();i++) {
				menuObjects.get(i).render(gc, r);
			}
			
			break;
			
		case 3: //hacky workaround for strange bug, frameskip
			while(gc.getZoom()!=0)
				r.scale(gc,1/gc.getZoomScale());
			
			gc.setGameState(2);

			break;
			
		}
		
		
		
		
	}
	
	public void UIReset() {
		
		UIObjects.get(0).setPresent(true);
		
		for(int i=1;i<UIObjects.size();i++) {
			UIObjects.get(i).setPresent(false);
		}
		
	}
	
	
	public static void main(String args[]) throws IOException {
		GameContainer gc = new GameContainer(new GameManager());

		//gc.setWidth(896); //384
		//gc.setHeight(504); //216
		//gc.setScale(4f); //4
		gc.start();
	}


}
