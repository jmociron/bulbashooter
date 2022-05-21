package sprites;

import javafx.scene.image.Image;
import main.GameTimer;

public class RareCandy extends PowerUp{

	// PROPERTIES (unique to RareCandy):
	private long useTime;

	// CONSTANTS:
	public static final int CANDY_TYPE = 1;
	public final static int CANDY_EFFECT_DURATION = 3; // immortality lasts for 3 seconds
	private final static Image CANDY_IMAGE = new Image("images/rarecandy.png", PowerUp.POWERUP_WIDTH, PowerUp.POWERUP_WIDTH, false, false);
	private final static int CANDY_STRENGTH = 9999999; // displays "immortality" in the game stats

	public RareCandy(int xPos, int yPos, GameTimer gametimer) {
		super(xPos, yPos, gametimer);
		this.img = CANDY_IMAGE;
		this.strength = CANDY_STRENGTH;
		this.type = CANDY_TYPE;
	}

	// GETTERS:
	public long getUseTime(){
		return this.useTime;
	}

	// SETTERS:
	public void setUseTime(long time){
		this.useTime = time;
	}

}