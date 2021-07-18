package Engine.game;

import java.util.ArrayList;

import Engine.GameContainer;
import Engine.Renderer;
import Engine.game.vfx.ParticleManager;

public abstract class GameObject {
	
	
	protected String tag;
	protected float posX, posY,posZ;
	protected float gravity;
	protected int width,height,xc,yc,ppx,ppy,color;
	protected boolean dead, ground,ramp,reading,water,air = false;
	protected int looking,looking2;
	protected float temp,airtime;
	
	//protected double gravity;
	
	protected Vec2 velocity;
	
	protected ArrayList<ParticleManager> particleManagers = new ArrayList<ParticleManager>();
	
	public abstract void update(GameContainer gc,float dt);
	public abstract void render(GameContainer gc, Renderer r);
	
	public boolean onScreen(GameContainer gc, Level level, int xc,int yc) {
		return xc>-level.getTilesize() && xc<gc.getWidth() && yc>-level.getTilesize() && yc<gc.getHeight();
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public void addPosX(float posX) {
		this.posX += posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float d) {
		this.posY = d;
	}
	public void addPosY(float posY) {
		this.posY += posY;
	}
	public float getPosZ() {
		return posZ;
	}
	public void setPosZ(float posZ) {
		this.posZ = posZ;
	}
	public void addPosZ(float posZ) {
		this.posZ += posZ;
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
	public void setGround(boolean ground) {
		this.ground = ground;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public int getXc() {
		return xc;
	}
	public void setXc(int xc) {
		this.xc = xc;
	}
	public int getYc() {
		return yc;
	}
	public void setYc(int yc) {
		this.yc = yc;
	}
	public Vec2 getVelocity() {
		return velocity;
	}
	public void setVelocity(double x, double y) {
		velocity.set(x, y);
		
	}
	
	public void addVelocity(double d, double y) {
		this.velocity.add(d, y);
		
	}
	public void setColor(int i) {
		this.color=i;
	}
	public int getPpx() {
		return ppx;
	}

	public void setPpx(int ppx) {
		this.ppx = ppx;
	}

	public int getPpy() {
		return ppy;
	}
	public float getGravity() {
		return gravity;
	}
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	public int getColor() {
		return color;
	}
	public boolean isGround() {
		return ground;
	}
	public void setPpy(int ppy) {
		this.ppy = ppy;
	}
	public boolean isRamp() {
		return ramp;
	}
	public void setRamp(boolean ramp) {
		this.ramp = ramp;
	}
	public void setVelocity(Vec2 velocity) {
		this.velocity = velocity;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public float getAirtime() {
		return airtime;
	}
	public void setAirtime(float airtime) {
		this.airtime = airtime;
	}
	public ArrayList<ParticleManager> getParticleManagers() {
		return particleManagers;
	}
	public void setParticleManagers(ArrayList<ParticleManager> particleManagers) {
		this.particleManagers = particleManagers;
	}
	public boolean isWater() {
		return water;
	}
	public void setWater(boolean water) {
		this.water = water;
	}
	


}
