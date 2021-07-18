package Engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	private int w,h;
	private int[] p;
	
	private int x,y;
	
	private BufferedImage image;
	
	public Image(String path) {
		//BufferedImage image = null;
		image = null;
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w=image.getWidth();
		h=image.getHeight();
		p=image.getRGB(0, 0, w, h, null, 0, w);
		
		image.flush();
	}
	
	public Image(String path,int x,int y) {
		//BufferedImage image = null;
		image = null;
		this.x=x; this.y=y;
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w=image.getWidth();
		h=image.getHeight();
		p=image.getRGB(0, 0, w, h, null, 0, w);
		
		image.flush();
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
