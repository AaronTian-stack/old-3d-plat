package Engine;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

import Engine.gfx.Image;

public class GameContainer implements Runnable {
	
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	private int fps;
	
	private int GameState;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	
	private boolean borderless;
	private boolean paused;
	
	private int zoom = 0;
	private float zoomScale = 1.25f;
	
	
	private int mastervolume;
	
	
	private int width; 
	private int height;
	private float scale;
	private String title = "Window";
	
	private String config = "/config/config.txt";
	private String audioconfig = "/config/audio.txt";
	
	private int[] res = new int[] {360,720,1080,1440,2160};
	private int resP = 0;
	
	//private Image cursor = new Image("/bc.png");
	
	public GameContainer(AbstractGame game) throws IOException {
		this.game=game;
		
		this.GameState=1;
		
		
		InputStream in = this.getClass().getResourceAsStream(config);
		InputStream in2 = this.getClass().getResourceAsStream(audioconfig);
		
		
		
		System.out.println(in);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
		
		mastervolume = Integer.parseInt(br2.readLine());
		System.out.println("V " + mastervolume);
		
		StringTokenizer st = new StringTokenizer(br.readLine());
				
		
		
		this.width = Integer.parseInt(st.nextToken());
		this.height = Integer.parseInt(st.nextToken());
		
		System.out.println(width+" x "+height);
		
		st = new StringTokenizer(br.readLine());
		
		this.scale = Float.parseFloat(st.nextToken());
		
		for(int i=0;i<res.length;i++) {
			if(res[i]==scale*height) {
				resP=i;
				break;
			}
		}
		
		
		if(br.readLine().equals("false")) {
			this.borderless=false;
		}
		else {
			this.borderless=true;
		}
		
		br.close(); br2.close();
		
		
		
		
	}
	
	public void start() {
		window = new Window(this,borderless);
		
		renderer = new Renderer(this,window);
		input = new Input(this);
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
		running=true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
	
		int frames = 0;
		fps=0;
		
		try {
			game.init(this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		while(running) {
			render=false; //lock framerate
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime+=passedTime;

			while(unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				game.update(this,(float)UPDATE_CAP);
				
				
				input.update();
				
				if(frameTime>=1.0) {
	
					frameTime=0;
					fps=frames;
					frames=0;
					
					
				}
			}
			
			if(render) { //wont render until it's updated. 
				
				renderer.clear();
				
				game.render(this,renderer);
				
				//renderer.drawText("FPS: "+fps, 0, 0, 0xffff4242);
				
				//renderer.drawText("pre-alpha", width-39, height-7, 0xffff4242);

				renderer.pFrame();
				
				renderer.fontText("FPS: "+fps, 0, renderer.getWindow().getG2d().getFontMetrics().getHeight(), Color.RED);

				window.update();
	
				frames++;
			}
			else {
				try {
					Thread.sleep(1);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//dispose();
		}
	}
	
	public void dispose() {
		
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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public int getFps() {
		return fps;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public boolean isBorderless() {
		return borderless;
	}

	public void setBorderless(boolean borderless) {
		this.borderless = borderless;
	}

	public int getMastervolume() {
		return mastervolume;
	}

	public void setMastervolume(int mastervolume) {
		this.mastervolume = mastervolume;
	}
	
	public void addMastervolume(int mastervolume) {
		this.mastervolume += mastervolume;
	}

	public int getGameState() {
		return GameState;
	}

	public void setGameState(int gameState) {
		GameState = gameState;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public float getZoomScale() {
		return zoomScale;
	}

	public void setZoomScale(float zoomScale) {
		this.zoomScale = zoomScale;
	}

	public int getResP() {
		return resP;
	}

	public void setResP(int resP) {
		this.resP = resP;
	}

	public int[] getRes() {
		return res;
	}

	public void setRes(int[] res) {
		this.res = res;
	}

}
