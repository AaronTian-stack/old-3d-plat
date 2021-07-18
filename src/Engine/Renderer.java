package Engine;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import Engine.gfx.Box;
import Engine.gfx.Font;
import Engine.gfx.Image;
import Engine.gfx.ImageTile;
import Engine.gfx.Text;
import objparser.Obj;
import threeD.MyPolygon;

public class Renderer {
	
	private int pW,pH;
	private int[] p;
	private int[] pp; //previous frame FIX
	private int[] ppp;
	private int[] zb;
	private int zDepth = 0;
	
	private Window window;

	private Font font = Font.STANDARD;
	
	//private float scale = 1f;
	
	public Renderer(GameContainer gc,Window window) {
		pW=gc.getWidth();
		pH=gc.getHeight();
		
		this.window=window;
		

		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
		
		zb = new int[p.length];
		
		pp = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		ppp = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
		
		
	}
	
	public void scale(GameContainer gc, float scale) {
		if(scale>1 && gc.getZoom()<5) {
			gc.setZoom(gc.getZoom()+1);
			window.scale(gc,scale);
		}
		if(scale<1 && gc.getZoom()>0) {
			gc.setZoom(gc.getZoom()-1);
			window.scale(gc,scale);	
		}
		
	}
	
	
	public void fontText(String text, float d, float e, Color color) {
		
		window.getTextQ().add(new Text(text,d,e,color));
	}
	
	public void box(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color) {
		
		window.getBoxQ().add(new Box(x,y,width,height,arcWidth,arcHeight,color));
	}
	
	public void swingImage(Image i) {
		window.getImgQ().add(i);
	}
	
	public void clear() {
		for(int i=0;i<p.length;i++) {
			p[i] = 0; //0x2e3037, 363636, 382b26, 282828, x111111
			zb[i] = 0;
		}
	}
	
	public void pFrame() {
		pp=p.clone();
	}
	
	public void pFrame2() {
		ppp=p.clone();
	}
	
	public void blur(int alpha) {
		
		for(int y=0;y<pH;y++){
			for(int x=0;x<pW;x++) {
				setTPixel(x,y,pp[x+y*pW],alpha);
			}
		}
	}
	
	public void drawFrame(int alpha) {
		
		for(int y=0;y<pH;y++){
			for(int x=0;x<pW;x++) {
				setTPixel(x,y,ppp[x+y*pW],alpha);
			}
		}
	}
	
	public void setPixel(int x,int y,int value) {
		
		int alpha = ((value>>24)&0xff);
		
		if((x<0||x>=pW||y<0||y>=pH)|| alpha==0) {
			return;
		}
		if(zb[x+y*pW]>zDepth) {
			return;
		}
		if(alpha==255) {
			p[x+y*pW]=value;
		}
		else {
			
			
			
			int pixelColor = p[x+y*pW];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff)-((value >> 16) & 0xff))*(alpha/255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff)-((value >>8) & 0xff))*(alpha/255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff)-(value & 0xff))*(alpha/255f));
			
			
			
			p[x+y*pW]=(255 << 24 | newRed << 16| newGreen << 8 | newBlue);
		}
		
	}
	
	public void multiply(int x, int y, int value) {
		
		if((x<0||x>=pW||y<0||y>=pH)) {
			return;
		}
		if(zb[x+y*pW]>zDepth) {
			return;
		}
		
		int pixelColor = p[x+y*pW];

		int newRed = ((value >> 16) & 0xff) * ((pixelColor >> 16) & 0xff) / 255;
		int newGreen = ((value >> 8) & 0xff) * ((pixelColor >> 8) & 0xff) / 255;
		int newBlue = (value & 0xff) * (pixelColor & 0xff) / 255;
		
		p[x+y*pW]=(255 << 24 | newRed << 16| newGreen << 8 | newBlue);
		
		
		
	}
	
	public int getPixel(int x, int y) {
		
		if((x<0||x>=pW||y<0||y>=pH)) {
			return 0;
		}
		
		return p[x+y*pW];
		
	}
	

	public void setTPixel(int x,int y,int value,int alpha) {
		
		
		if(alpha>=100) {
			setPixel(x,y,value);
			return;
		}
		
		if(alpha<=0) {
			return;
		}

		alpha*=255/100;

		if((x<0||x>=pW||y<0||y>=pH)|| alpha==0) {
			return;
		}
		if(zb[x+y*pW]>zDepth) {
			return;
		}
		if(alpha==255) {
			p[x+y*pW]=value;
		}
		else {
			
			
			
			int pixelColor = p[x+y*pW];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff)-((value >> 16) & 0xff))*(alpha/255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff)-((value >>8) & 0xff))*(alpha/255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff)-(value & 0xff))*(alpha/255f));
			
			
			
			p[x+y*pW]=(255 << 24 | newRed << 16| newGreen << 8 | newBlue);
		}
		
		
	}
	
	public void drawText(String text,int offX,int offY, int color) {
		
		text = text.toUpperCase();
		int offset = 0;
		for(int i=0;i<text.length();i++) {
			int unicode = text.codePointAt(i) - 32;
			
			if(unicode==94) { //~
				drawText(text.substring(i+1,text.length()),offX,offY+10,color);
				return;
			}
			
			for(int y=0;y<font.getFontImage().getH();y++) {
				for(int x=0;x<font.getWidths()[unicode];x++) {
					if(font.getFontImage().getP()[(x+font.getOffsets()[unicode])+y*font.getFontImage().getW()]==0xffffffff) {
						setPixel(x+offset+offX,y+offY,color);
					}
				}

			}
		
			offset += font.getWidths()[unicode];
		}
	}
	
	public void drawImage(Image image,int offX, int offY,boolean t,int a) {
		
		if(offX<-image.getW()) {
			return;
		}
		
		if(offY<-image.getH()) {
			return;
		}
		
		if(offX>=pW) {
			return;
		}
		
		if(offY>=pH) {
			return;
		}
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();
		
		//clipping
		
		if(offX<0) {
			newX -= offX;
		}
		
		if(offY<0) {
			newY -= offY;
		}
		
		if(newWidth+offX>=pW) {
			newWidth -= newWidth + offX - pW;
			
		}
		
		if(newHeight+offY>=pH) {
			newHeight -= newHeight + offY - pH;
			
		}
		
		if(!t) {
		
		for(int y=newY;y<newHeight;y++){
			for(int x=newX;x<newWidth;x++) {
				setPixel(x+offX,y+offY,image.getP()[x+y*image.getW()]);
			}
		}
		}
		else {
			for(int y=newY;y<newHeight;y++){
				for(int x=newX;x<newWidth;x++) {
					setTPixel(x+offX,y+offY,image.getP()[x+y*image.getW()],a);
					//multiply(x+offX, y+offY, image.getP()[x+y*image.getW()]);
				}
		}
		}
		
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
		if(offX<-image.getW()) {
			return;
		}
		
		if(offY<-image.getH()) {
			return;
		}
		
		if(offX>=pW) {
			return;
		}
		
		if(offY>=pH) {
			return;
		}
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();
		
		//dont render
		
		
		
		//clipping
		
		if(offX<0) {
			newX -= offX;
		}
		
		if(offY<0) {
			newY -= offY;
		}
		
		if(newWidth+offX>=pW) {
			newWidth -= newWidth + offX - pW;
			
		}
		
		if(newHeight+offY>=pH) {
			newHeight -= newHeight + offY - pH;
			
		}
		
		for(int y=newY;y<newHeight;y++){
			for(int x=newX;x<newWidth;x++) {
				setPixel(x+offX,y+offY,image.getP()[(x+tileX*image.getTileW())+(y+tileY*image.getTileH())*image.getW()]);
			}
		}
	}
	
	public void drawLine(int x1,int y1,int x2,int y2,int color,boolean a, int alpha) //brensenham algorithm
	{
		 	int d = 0;
		 
	        int dx = Math.abs(x2 - x1);
	        int dy = Math.abs(y2 - y1);
	 
	        int dx2 = 2 * dx; // slope scaling factors to
	        int dy2 = 2 * dy; // avoid floating point
	 
	        int ix = x1 < x2 ? 1 : -1; // increment direction
	        int iy = y1 < y2 ? 1 : -1;
	 
	        int x = x1;
	        int y = y1;
	 
	        if (dx >= dy) {
	            while (true) {
	            	if(a)
	            		setTPixel(x,y,color, alpha);
	            	else {
	            		setPixel(x,y,color);
	            	}
	                if (x == x2)
	                    break;
	                x += ix;
	                d += dy2;
	                if (d > dx) {
	                    y += iy;
	                    d -= dx2;
	                }
	            }
	        } else {
	            while (true) {
	            	if(a)
	            		setTPixel(x,y,color, alpha);
	            	else {
	            		setPixel(x,y,color);
	            	}
	                if (y == y2)
	                    break;
	                y += iy;
	                d += dx2;
	                if (d > dy) {
	                    x += ix;
	                    d -= dy2;
	                }
	            }
	        }
	}
	
	public void drawSquare(int x, int y, int width, int height, int color) {
		
		
		
		int x1 = x-width;
		int x2 = x+width;
		int y1 = y+height;
		int y2 = y-height;
		
		
		
		drawLine(x1,y1,x1,y2,color,false,0);
		drawLine(x1,y2,x2,y2,color,false,0);
		drawLine(x2,y2,x2,y1,color,false,0);
		drawLine(x2,y1,x1,y1,color,false,0);
	}
	
	public void fillSquareLight(int x,int y,int width,int color,int alpha) {
		
		for(int i=y;i<y+width;i++) {
			for(int j=x;j<x+width;j++) {
				
				setTPixel(j,i,color,alpha);
				
			}
		}

	}
	
	public void fillSquare(int x,int y,int width,int height,int color) {
		
		if(width==0) {
			setPixel(x,y,color);
			return;
		}
		
		for(int i=y-height;i<y+height;i++) {
			for(int j=x-width;j<x+width;j++) {
				
				setPixel(j,i,color);
				
			}
		}
		
	}
	
	public void fillSquareT(int x,int y,int width,int height,int color,int alpha) {
		
		if(width==0) { 
			setTPixel(x,y,color,alpha);
			return;
		}
		
		for(int i=y-height;i<y+height;i++) {
			for(int j=x-width;j<x+width;j++) {
				
				setTPixel(j,i,color,alpha);
				
			}
		}
		
	}
	
	public void drawCircle(int xc,int yc,int r, int color) {
		
		int x = 0, y = r; 
	    int d = 3 - 2 * r; 
	    circle(xc, yc, x, y,color); 
	    while (y >= x) 
	    { 
	        // for each pixel we will 
	        // draw all eight pixels 
	          
	        x++; 
	  
	        // check for decision parameter 
	        // and correspondingly  
	        // update d, x, y 
	        if (d > 0) 
	        { 
	            y--;  
	            d = d + 4 * (x - y) + 10; 
	        } 
	        else
	            d = d + 4 * x + 6; 
	        circle(xc, yc, x, y,color); 
	        
	    } 
		
		
	}
	
	public void drawCircleFilled(int xc,int yc,int r,int color,boolean a,int alpha) {
		for(int y=-r; y<=r; y++)
		    for(int x=-r; x<=r; x++)
		        if(x*x+y*y <= r*r+r)
		        	if(!a)
		        		setPixel(xc+x, yc+y,color);
		        	else
		        		setTPixel(xc+x, yc+y,color,alpha);
	}
	
	public void circle(int xc, int yc, int x, int y, int color) 
	{ 
	    setPixel(xc+x, yc+y, color); 
	    setPixel(xc-x, yc+y, color); 
	    setPixel(xc+x, yc-y, color); 
	    setPixel(xc-x, yc-y, color); 
	    setPixel(xc+y, yc+x, color); 
	    setPixel(xc-y, yc+x, color); 
	    setPixel(xc+y, yc-x, color); 
	    setPixel(xc-y, yc-x, color); 
	} 
	
	public void circleLines(int xc, int yc, int x,int y,int color) {
		drawLine(xc,yc,xc+x,yc+y,color,false,0);
		drawLine(xc,yc,xc-x,yc+y,color,false,0);
		drawLine(xc,yc,xc+x,yc-y,color,false,0);
		drawLine(xc,yc,xc-x,yc-y,color,false,0);
		drawLine(xc,yc,xc+x,yc+y,color,false,0);
		drawLine(xc,yc,xc-x,yc+y,color,false,0);
		drawLine(xc,yc,xc+x,yc-y,color,false,0);
		drawLine(xc,yc,xc-x,yc-y,color,false,0);
	}
	  
	
	public void drawPoly(int x, int y, MyPolygon p, Obj cube, int color) {
		
		Polygon points = p.render();
		
		
		
		ArrayList<Integer>[][] faceorder = cube.getFaceorder();
		
		
		for(int i=0;i<faceorder.length;i++) {
			
			for(int j=0;j<faceorder[i][0].size()-1;j++) {
				
				
				int po = faceorder[i][0].get(j);
				int p1 =  faceorder[i][0].get(j+1);
				
		
				
				drawLine(x+points.xpoints[po],y+points.ypoints[po],x+points.xpoints[p1],y+points.ypoints[p1],color,false,0);
				
			}
			
			drawLine(x+points.xpoints[faceorder[i][0].get(faceorder[i][0].size()-1)],y+points.ypoints[faceorder[i][0].get(faceorder[i][0].size()-1)],x+points.xpoints[faceorder[i][0].get(0)],y+points.ypoints[faceorder[i][0].get(0)],color,false,0);
			
			
		}
		
		
		
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	
	
}
