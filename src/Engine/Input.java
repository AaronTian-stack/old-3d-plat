package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener,MouseMotionListener,MouseWheelListener{
	
	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];
	
	private final int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];
	
	private int mouseX,mouseY;
	private int scroll;
	private GameContainer gc;
	
	public Input(GameContainer gc) {
		this.gc = gc;
		mouseX=0;
		mouseY=0;
		scroll=0;
		
		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	public void update() {
		scroll=0;
		for(int i=0;i<NUM_KEYS;i++) {
			keysLast[i]=keys[i];
		}
		for(int i=0;i<NUM_BUTTONS;i++){
			buttonsLast[i]=buttons[i];
		}
	}
	
	public boolean isKey(int keyCode) { //pressed (spam)
		return keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) { //released
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public boolean isKeyDown(int keyCode) { //one time press
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isButton(int button) {
		return buttons[button];
	}
	
	public boolean isButtonUp(int button) {
		return !buttons[button] && buttonsLast[button];
	}
	
	public boolean isButtonDown(int button) {
		return buttons[button] && !buttonsLast[button];
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseX = (int)(arg0.getX()/gc.getScale());
		mouseY = (int)(arg0.getY()/gc.getScale());
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = (int)(arg0.getX()/gc.getScale());
		mouseY = (int)(arg0.getY()/gc.getScale());
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		buttons[arg0.getButton()]=true;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		buttons[arg0.getButton()]=false;
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()]=true;
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()]=false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		scroll = arg0.getWheelRotation();
		
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}

}
