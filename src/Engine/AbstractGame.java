package Engine;

import java.io.IOException;

public abstract class AbstractGame {
	
	public abstract void init(GameContainer gc) throws IOException;
	public abstract void update(GameContainer gc,float dt);
	public abstract void render(GameContainer gc,Renderer r);

}
