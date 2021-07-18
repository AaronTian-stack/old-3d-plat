package Engine.game.vfx;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Level;

public class Light extends GameObject { //fully dynamic :DDDD !
	
	private float radius;
	
	private double theta;
	private int strength;
	private int color, tilesize, realtilesize;
	
	private double rays;
	
	private Level level;
	
	//private float[][] lightmap;
	
	private TreeSet<Edge> edges = new TreeSet<Edge>();
	
	private Lightmap reallight;
	public Light(float posX, float posY, float radius, int strength, double rays, int color,Level level, Lightmap reallight) {
		
		this.posX=posX;
		this.posY=posY;
		//this.radius=radius;
		this.height=(int) radius; this.width=(int) radius;
		this.strength=strength;
		
		
		this.color=color;
		this.rays=rays;	
		this.level=level;
		//this.lightmap=lightmap;
		this.reallight=reallight;
		
		this.realtilesize = tilesize;
		
		//this.tilesize=tilesize/split;
		
		
		
		//this.maxstrength = rays*strength;
		
		//this.maxstrength = rays;
		
		this.radius=16*4-level.getTilesize();
		
	//	this.theta = 360.0/rays*Math.PI/180.0;
		
		//System.out.println("STRENGTH"+this.strength);
		
		
		
	}

	float temp = 0;
	
	public void update(GameContainer gc, float dt) {
		
		//posX+=dt*10;
		
		edges.clear();
		
		int tileX = (int)(posX/level.getTilesize());
		int tileY = (int)(posY/level.getTilesize());
		
		//System.out.println(tileX+" "+tileY);
		
		if(onScreen(gc,level,xc,yc)) {
			
			edges.add(new Edge((int)(tileX-radius),(int)(tileY-radius),Math.atan2(-1, 1)));
			edges.add(new Edge((int)(tileX-radius),(int)(tileY+radius),Math.atan2(-1, -1)));
			edges.add(new Edge((int)(tileX+radius),(int)(tileY-radius),Math.atan2(1, 1)));
			edges.add(new Edge((int)(tileX+radius),(int)(tileY+radius),Math.atan2(1, -1)));

			for(int i=(int) (tileY-(radius/level.getTilesize()));i<=(int)(tileY+(radius/level.getTilesize()));i++) {
				for(int j=(int) (tileX-(radius/level.getTilesize()));j<=(int)(tileX+(radius/level.getTilesize()));j++) {
					
					if(i>=0 && j>=0 && i<level.getHeight() && j<level.getWidth() && level.getLevel()[i][j]==1) {
						
						//System.out.println(i+" "+j);

						int xl = j*level.getTilesize()-level.getDraw();
						int xr = j*level.getTilesize()+level.getDraw();
						int yu = i*level.getTilesize()-level.getDraw();
						int yd = i*level.getTilesize()+level.getDraw();
						
						
						
						edges.add(new Edge(xl,yu,Math.atan2(yu-posY, xl-posX)));
						
						edges.add(new Edge(xr,yu,Math.atan2(yu-posY, xr-posX)));
						
						edges.add(new Edge(xr,yd,Math.atan2(yd-posY, xr-posX)));
						
						edges.add(new Edge(xl,yd,Math.atan2(yd-posY, xl-posX)));
		
						
					}
					
					
				}
			}
	
			////

		}

	}
	
	
	
	public void render(GameContainer gc, Renderer r) {
		
		//System.out.println(Arrays.deepToString(lightmap));
		this.xc=level.getXc();
		this.yc=level.getYc();
		
		
		for(Edge e : edges) {
			//r.drawLine((int)posX+xc, (int)posY+yc, e.x+xc, e.y+yc, 0xff124212, false, 0);
			//Point p = raycast((int)posX,(int)posY,e.x,e.y);
			
			
			int x = (int) ((int)(posX)+radius*Math.cos(e.angle));
			int y = (int) ((int)(posY)+radius*Math.sin(e.angle));
			Point p = raycast((int)posX,(int)posY,x,y);

			//x = (int) ((int)(posX)+radius*Math.cos(e.angle-0.00001));
			//y = (int) ((int)(posY)+radius*Math.sin(e.angle-0.00001));
			//Point p2 = raycast((int)posX,(int)posY,x,y);
			
			
			r.drawLine((int)posX+xc, (int)posY+yc, p.x+xc, p.y+yc, 0xffff4242, false, 0);
			//r.drawLine((int)posX+xc, (int)posY+yc, p1.x+xc, p1.y+yc, 0xffff1242, false, 0);
			//r.drawLine((int)posX+xc, (int)posY+yc, p2.x+xc, p2.y+yc, 0xffff2442, false, 0);
		}
	
		r.drawSquare((int)posX+xc,(int)posY+yc,8,8,0xffff1242);
		
	}
	
	public Point raycast(int x1,int y1,int x2,int y2) {
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
            	
            	//setPixel(x,y,color);
            	
                if (level.getLevel()[(y+level.getDraw())/level.getTilesize()][(x+level.getDraw())/level.getTilesize()]==1 	||		x == x2		|| x<=posX-radius || x>=posX+radius) { 
                	//System.out.println(y/level.getTilesize()+" "+x/level.getTilesize());
                	break;
                }
                    
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
            	
            	//setPixel(x,y,color);
            	
                if (level.getLevel()[(y+level.getDraw())/level.getTilesize()][(x+level.getDraw())/level.getTilesize()]==1 	||		y == y2		|| y<=posY-radius || y>=posY+radius) {
                	//System.out.println(y/level.getTilesize()+" "+x/level.getTilesize());
                	break;
                }
                    
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
        
        return(new Point(x,y));
		
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public double getRays() {
		return rays;
	}

	public void setRays(int rays) {
		this.rays = rays;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}


}

class Edge implements Comparable<Edge>{
	
	int x, y;
	double angle;
	
	public Edge(int x,int y,double angle) {
		this.x=x; this.y=y; this.angle=angle;
	}

	
	public int compareTo(Edge arg0) {
		
		return Double.compare(arg0.angle, this.angle);
	}
}
