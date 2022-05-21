package sprites;

import javafx.scene.image.Image;
import main.GameTimer;

public class WildPokemon extends Pokemon {

	// IMAGES:
	public final static int WILD_WIDTH = 50;
	private final static Image RATATA_IMAGE = new Image("images/ratata.gif", WildPokemon.WILD_WIDTH, WildPokemon.WILD_WIDTH, false, false);
	private final static Image NIDORINA_IMAGE = new Image("images/nidorina.gif", WildPokemon.WILD_WIDTH, WildPokemon.WILD_WIDTH, false, false);
	private static final Image[] BASE_IMAGES = {RATATA_IMAGE, NIDORINA_IMAGE};
	private static final int IMAGE_COUNT = BASE_IMAGES.length;

	// CONSTANTS:
	public final static int WILD_RESPAWN_DELAY = 5;
	public final static int START_WILD_NUM = 7; // initial spawning
	public final static int RESPAWN_WILD_NUM = 3; // respawning
	private final static int WILD_DAMAGE = 30;

	public WildPokemon(int x, int y, GameTimer gametimer){
		super(x,y, gametimer);

		this.dx = x;
		this.imageCount = IMAGE_COUNT; // number of images
		this.baseImages = BASE_IMAGES; // array of images
		this.damage = WILD_DAMAGE; // reduces 50 from the ship strength
		this.width = WILD_WIDTH;

		generateImage();
		generateSpeed();
	}
}