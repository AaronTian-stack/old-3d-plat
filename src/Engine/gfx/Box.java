package Engine.gfx;

import java.awt.Color;

public class Box {
	
	int x, y,width, height, arcW, arcH;
	
	Color color;

	public Box(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color) {
		this.x=x; this.y=y; this.width=width; this.height=height; this.arcW=arcWidth; this.arcH=arcHeight; this.color=color;
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

	public int getArcW() {
		return arcW;
	}

	public void setArcW(int arcW) {
		this.arcW = arcW;
	}

	public int getArcH() {
		return arcH;
	}

	public void setArcH(int arcH) {
		this.arcH = arcH;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
