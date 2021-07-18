package Engine.game;

import java.io.IOException;

import Engine.GameContainer;
import Engine.Renderer;
import objparser.Obj;

public class UIPackage extends GameObject {
	
	private UIPackage parent;
	
	private boolean present;
	private UIElement[] elements;
	
	private Obj model;
	
	private int vx,vy;
	
	public UIPackage(UIPackage parent, boolean present, Obj model, int vx, int vy, UIElement ... ui) {
		
		this.parent=parent;
		
		this.present=present;
		
		this.elements=ui;
		
		this.model=model;
		
		this.vx=vx;
		
		this.vy=vy;
		
	}
	
	public UIPackage(UIPackage parent, boolean present, UIElement ... ui) {
		
		this.parent=parent;
		
		this.present=present;
		
		this.elements=ui;
		
	}


	@Override
	public void update(GameContainer gc, float dt) {
		if(model!=null)
			model.getPoly().rotate(1, 1, 1);
		
		if(present) {
			//System.out.println("UPDATING");
			for(int i=0;i<elements.length;i++) {
				elements[i].update(gc, dt);
			}
		}
		
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		boolean cog = true;

		if(present) {
			for(int i=0;i<elements.length;i++) {
				elements[i].render(gc, r);
				if(!elements[i].isDraw()) {
					cog=false;
				}
			}
			
			if(cog && model!=null)
				r.drawPoly(vx, vy, model.getPoly(), model, 0xffffffff);
			
			
		}
		
		
		
		
		
		
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public UIPackage getParent() {
		return parent;
	}

	public void setParent(UIPackage parent) {
		this.parent = parent;
	}
	

}
