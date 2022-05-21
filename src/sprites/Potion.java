package sprites;

import javafx.scene.image.Image;
import main.GameTimer;

public class Potion extends PowerUp{

	// CONSTANTS:
	public static final int POTION_TYPE = 0;
	private final static Image POTION_IMAGE = new Image("images/potion.png", PowerUp.POWERUP_WIDTH, PowerUp.POWERUP_WIDTH, false, false);
	private final static int POTION_STRENGTH = 50; // adds 50 strength to the starter pokemon

	public Potion(int xPos, int yPos, GameTimer gametimer) {
		super(xPos, yPos, gametimer);
		this.img = POTION_IMAGE;
		this.strength = POTION_STRENGTH;
		this.type = POTION_TYPE;
	}
}
