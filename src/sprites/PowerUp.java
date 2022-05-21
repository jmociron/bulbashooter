package sprites;

import javafx.scene.canvas.GraphicsContext;
import main.GameTimer;

public class PowerUp extends Sprite{

	// PROPERTIES (unique to PowerUps):
	protected int type;
	protected int strength;
	protected long spawnTime;

	// CONSTANTS:
	public final static int POWERUP_COUNT = 1; // to allow spawning one at a time
	public final static int POWERUP_DURATION = 5;
	public final static int POWERUP_DELAY = 10;
	public final static int POWERUP_WIDTH = 30;

	public PowerUp(int xPos, int yPos, GameTimer gametimer) {
		super(xPos, yPos, gametimer);
	}

	public void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);
    }

	// SETTERS:
	public void setVisible(boolean visibility) {
		this.visible = visibility;
	}

	public void setSpawnTime(long time){
		this.spawnTime = time;
	}

	// GETTERS:
	public long getSpawnTime(){
		return this.spawnTime;
	}

	public int getType(){
		return this.type;
	}

	public int getStrength(){
		return this.strength;
	}

}
