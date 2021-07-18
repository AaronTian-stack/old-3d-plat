package specialtiles;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.GameObject;
import Engine.game.Level;
import Engine.gfx.Image;

public class Mirror extends GameObject {

	private Level level;

	private Image debug = new Image("/tiles/mirror.png");

	public Mirror(float posX, float posY,int width, int height,Level level) {
		
		this.posX=posX;
		this.posY=posY;

		this.level = level;
		
		this.width=width;
		this.height=height;

		
	}

	
	//float speed = (float) (Math.PI/24); //only works with multiples of 0.125
	float offsetScale = 2.2f;
	int offset = 0;
	float fadeout= 1f;
	float opacity = 0;
	//double limit = Math.PI*2;
	public void update(GameContainer gc, float dt) {
		
		temp+=dt;
		
		if(temp>0.5) {
			//index++;
			temp=0;
		}
		
	}
	
	
	float[] lookup = new float[] {
			0.195090322f
			,0.382683432f
			,0.555570233f
			,0.707106781f
			,0.831469612f
			,0.923879533f
			,0.98078528f
			,1
			,0.98078528f
			,0.923879533f
			,0.831469612f
			,0.707106781f
			,0.555570233f
			,0.382683432f
			,0.195090322f
			,0
			,-0.195090322f
			,-0.382683432f
			,-0.555570233f
			,-0.707106781f
			,-0.831469612f
			,-0.923879533f
			,-0.98078528f
			,-1
			,-0.98078528f
			,-0.923879533f
			,-0.831469612f
			,-0.707106781f
			,-0.555570233f
			,-0.382683432f
			,-0.195090322f
			,0};


	
	int index,index2 = 0;
	//float rad =0;
	
	public void render(GameContainer gc, Renderer r) {
		
		opacity = 100+fadeout-1;
		
		//rad=0;

		xc = level.getXc()+(int)posX-level.getDraw();
		yc = level.getYc()+(int)posY-level.getDraw();
		
		if(xc>-width && xc<gc.getWidth() && yc>-height && yc<gc.getHeight()) {
			
			
			
			for(int j=xc;j<xc+width+1;j++) {
				//int RA = (int) (Math.random() * 2+1);
				for(int i=0;i<lookup[index2];i++) 
					r.setTPixel(j, (int) (yc-i), 0xff57AEFF,20);
					index2++;
					if(index2>lookup.length/2)
						index2=index2-lookup.length/2;
				
			}
			

			for(int i=yc+1;i<yc+height;i++) {
				
				opacity-=fadeout;

				//rad+=Math.PI/180;
				
				//index+=(h/3);
				index++;
				
				if(index>lookup.length-1)
					index=index-lookup.length;
				
				//if(rad>limit) {
					//rad=0;
				//}
				
				//offset = (int)(Math.sin(rad)*offsetScale);
				
				offset=(int)(lookup[index]*offsetScale);

				for(int j=xc;j<xc+width+1;j++) {
					
					r.setTPixel(j, i, 0xff57AEFF,(int)opacity); //	376FA3 57AEFF
					
					//r.setPixel(j, i, 0xff57AEFF);
					r.setTPixel(j, i, r.getPixel(j+offset, 2*yc-i-1), (int)opacity); //	57AEFF 	3A454F
					

				}
			}
			
			//r.drawImage(debug, xc, yc, false, 0);
			//r.setPixel(xc, yc, 0xffff4242);
			//r.setPixel(xc+width, yc+height, 0xffff4242);
		
		}
		
	}


}
