package Engine.gfx;

import java.awt.Color;

public class Text {
	
	String s; int x; int y;
	
	Color color;
	
	public Text(String s, float x, float y,Color color) {
		this.s=s; this.x=(int)x; this.y=(int)y; this.color=color;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
