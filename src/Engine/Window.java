package Engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import Engine.gfx.Image;

public class Window {
	
	private JFrame frame;
	private BufferedImage image;
	private BufferedImage cursorImg;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	private Graphics2D g2d;
	private int screenHeight;
	private int screenWidth;
	
	private boolean borderless;
	
	//private BufferedImage img;
	
	private Font font = new Font("Yahei", Font.PLAIN, 36);
	
	private ArrayList<Engine.gfx.Text> textQ = new ArrayList<Engine.gfx.Text>();
	
	private ArrayList<Engine.gfx.Box> boxQ = new ArrayList<Engine.gfx.Box>();
	
	private ArrayList<Engine.gfx.Image> imgQ = new ArrayList<Engine.gfx.Image>();
	
	public Window(GameContainer gc, boolean borderless) {
		
		this.borderless=borderless;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;
		
		

		image = new BufferedImage(gc.getWidth(),gc.getHeight(),BufferedImage.TYPE_INT_RGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.getWidth()*gc.getScale()),(int)(gc.getHeight()*gc.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		
		
		frame =  new JFrame("Undulate");
		
	
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("appicon/icon2.png"));
		
		frame.setIconImage(logo.getImage());

		if(this.borderless) 
			frame.setUndecorated(true); //borderless
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(canvas,BorderLayout.CENTER);
		
		frame.pack();
		
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g2d = (Graphics2D) bs.getDrawGraphics();
		
		
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		
		
		//try {
			//img = ImageIO.read(Image.class.getResourceAsStream("/ui/arrow.png"));
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		//img = new BufferedImage((img.getWidth() * 5),
	   //             (img.getHeight() * 5), BufferedImage.TYPE_INT_ARGB);
		
		//g2d=img.createGraphics();
		/*
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("res/bc.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(0, 
		          0), "image");
		frame.getContentPane().setCursor(c);
*/
		
		/*
		 * // Transparent 16 x 16 pixel cursor image.
		cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		
		//Cursor c = toolkit.createCustomCursor(image , new Point(0, 
		//          0), "image");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		
		*/

		((Graphics2D) g).setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		(g2d).setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	}
	float m = 1780; //textbox length
	float maxL = m;
	
	
	float d = 0;
	
	float boxScale = 1;
	
	public void resize(GameContainer gc, int b) {
		
	
		gc.setResP(gc.getResP()+b);
		
		
		d=gc.getRes()[gc.getResP()]/1f/gc.getHeight();

		gc.setScale(d);
		Dimension s = new Dimension((int)(gc.getWidth()*d+0.5),gc.getRes()[gc.getResP()]);
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		frame.setLayout(new BorderLayout());
		frame.add(canvas,BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.requestFocus();
		canvas.createBufferStrategy(2);
		
		maxL=m*gc.getScale()/3;
		boxScale=(boxScale*gc.getScale()/3);
		
		g = bs.getDrawGraphics();
		g2d = (Graphics2D) bs.getDrawGraphics();
		
		
		g.setFont(new Font("Yahei", Font.PLAIN, (int)(font.getSize()*gc.getScale()/3))); 
		
		g.setColor(Color.WHITE);
		((Graphics2D) g).setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		

		
		
		
		g2d.setFont(new Font("Yahei", Font.PLAIN, (int)(font.getSize()*gc.getScale()/3))); 
		
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		 
		bs = canvas.getBufferStrategy();
		
		
	}
	
	public void setborder(GameContainer gc,boolean borderless) {
		
		frame.dispose();
		
		frame.setUndecorated(borderless);
		this.resize(gc, 0);
		
		frame.setVisible(true);
		frame.requestFocus();
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g2d = (Graphics2D) bs.getDrawGraphics();

	}
	
	
	public void update() {
		
		
	
		g.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight(),null);
		
		//g2d.fillRect(100, 100, 100, 100);
		
		//g2d.drawImage(img, 100, 100, null);
		
		for(Engine.gfx.Box t : boxQ) {
			
			g2d.setColor(t.getColor());

			g2d.fillRoundRect(t.getX(), t.getY(), (t.getWidth()), (t.getHeight()), t.getArcW(), t.getArcH());
	
		}
		
		for(Engine.gfx.Image t : imgQ) {
			
			g2d.drawImage(t.getImage(), t.getX(),t.getY(),null);
		}
		
		
		for(Engine.gfx.Text t : textQ) {
			text(t.getS(),t.getX(),t.getY(),t.getColor());
		}
		bs.show();
		textQ.clear();
		boxQ.clear();
		imgQ.clear();
	}
	
	public void scale(GameContainer gc, float scale) {
		
		//g.clearRect(0, 0, 10000, 10000);
		
		int s = (int)(gc.getWidth()*gc.getScale());
		int s1 = (int)(gc.getHeight()*gc.getScale());
		
		if(scale>1) {
			g.translate((int) -(s*scale-s)/2, (int) -(s1*scale-s1)/2);
			//g.translate(-960, -540);
		}
		if(scale<1) {
			g.translate((int) -(s*scale-s)/2, (int) -(s1*scale-s1)/2);
			//g.translate(960/2, 540/2);
		}
		
		
		
		((Graphics2D)(g)).scale(scale, scale);
	}
	
	public void text(String text, int d, int e,Color c) {
		
		g2d.setColor(c);
		
		int width = g2d.getFontMetrics().stringWidth(text);
		if(width>=maxL) {
			for(int i=text.length()-1;i>=0;i--) {
				if(text.charAt(i)==' ') {
					if(g2d.getFontMetrics().stringWidth(text.substring(0,i))<maxL) {
						g2d.drawString(text.substring(0,i), d, e);
						text(text.substring(i+1,text.length()),d,e+g2d.getFontMetrics().getHeight(),c);
						return;
					}
				}
			}
		}
		
		
		g2d.drawString(text, d, e);
		bs.show();
		
	}

	public BufferedImage getImage() {
		return image;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setCursorImg(BufferedImage cursorImg) {
		this.cursorImg = cursorImg;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public BufferStrategy getBs() {
		return bs;
	}

	public void setBs(BufferStrategy bs) {
		this.bs = bs;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public BufferedImage getCursorImg() {
		return cursorImg;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public boolean isBorderless() {
		return borderless;
	}

	public void setBorderless(boolean borderless) {
		this.borderless = borderless;
	}

	public ArrayList<Engine.gfx.Text> getTextQ() {
		return textQ;
	}

	public void setTextQ(ArrayList<Engine.gfx.Text> textQ) {
		this.textQ = textQ;
	}

	public ArrayList<Engine.gfx.Box> getBoxQ() {
		return boxQ;
	}

	public void setBoxQ(ArrayList<Engine.gfx.Box> boxQ) {
		this.boxQ = boxQ;
	}

	public ArrayList<Engine.gfx.Image> getImgQ() {
		return imgQ;
	}

	public void setImgQ(ArrayList<Engine.gfx.Image> imgQ) {
		this.imgQ = imgQ;
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}

}
