package sprites;

import java.util.Random;
import javafx.scene.image.Image;
import main.GameStage;
import main.GameTimer;

public class LegendaryPokemon extends Pokemon {

	// PROPERTIES (unique to Legendary Pokemon):
	private int health;
	private boolean moveUp;

	// IMAGES:
	private final static int LEGEND_WIDTH = 150;
	private final static Image LUGIA_IMAGE = new Image("images/lugia.gif",LegendaryPokemon.LEGEND_WIDTH,LegendaryPokemon.LEGEND_WIDTH,false,false);
	private final static Image HOOH_IMAGE = new Image("images/ho-oh.gif",LegendaryPokemon.LEGEND_WIDTH,LegendaryPokemon.LEGEND_WIDTH,false,false);
	private final static Image MOLTRES_IMAGE = new Image("images/moltres.gif",LegendaryPokemon.LEGEND_WIDTH,LegendaryPokemon.LEGEND_WIDTH,false,false);
	private static final Image[] BASE_IMAGES = {LUGIA_IMAGE, HOOH_IMAGE, MOLTRES_IMAGE};
	private static final int IMAGE_COUNT = BASE_IMAGES.length;

	// CONSTANTS:
	public static final double LEGEND_DELAY = 30; // delay in spawning
	public static final double COLLISION_DELAY = 1; // delay before reducing strength from starter
	public static final int MOVE_DOWN = 0;
	public static final int MOVE_UP = 1;
	private final static int LEGEND_DAMAGE = 50;
	private final static int LEGEND_HEALTH = 3000;

	// legendary's initial position
	private static Random r = new Random();
	public final static int xPos = GameStage.WINDOW_WIDTH - LegendaryPokemon.LEGEND_WIDTH;
	public final static int yPos = r.nextInt(GameStage.WINDOW_HEIGHT - LegendaryPokemon.LEGEND_WIDTH);

	public LegendaryPokemon(int x, int y, GameTimer gametimer){

		super(x,y, gametimer);

		this.visible = false; // initializes visible to false since legendary will spawn after 30 seconds
		this.moveRight = false;
		this.imageCount = IMAGE_COUNT; // number of images
		this.baseImages = BASE_IMAGES; // array of images
		this.health = LEGEND_HEALTH; // has a health of 3000
		this.damage = LEGEND_DAMAGE; // reduces 50 from the ship strength
		this.width = LEGEND_WIDTH; // image dimension

		generateImage(); // randomly generates an image from the array
		generateSpeed(); // randomly generates speed from 1 to 5
	}

	// randomizes the boolean for vertical movement
	public void generateMoveUp(){
		Random r = new Random();
		int intUp = r.nextInt(2); // randomizes moveUp's initial value

		switch(intUp){
			case MOVE_DOWN:
				this.setMoveUp(false);
				break;
			case MOVE_UP:
				this.setMoveUp(true);
				break;
		}
	}

	// decrementing dy to allow vertical movement
	public void moveUp(){
		this.setDY(this.dy - 1);
		this.setY(this.getDY());
	}

	// incrementing dy to allow vertical movement
	public void moveDown(){
		this.setDY(this.dy + 1);
		this.setY(this.getDY());
	}

	public void moveY(boolean moveUp){
		if(moveUp){
			moveUp();
		}else{
			moveDown();
		}
	}

	@Override
	public void move(){ // modified move method, allowing the legendary to also move vertically

		// checks if legendary has reached the edge in terms of y-position
		if(this.moveUp && this.dy <= 0){ // top edge
			this.setMoveUp(false);
		}else if(!this.moveUp && this.dy >= GameStage.WINDOW_HEIGHT - this.width){ // bottom edge
			this.setMoveUp(true);
		}

		// checks if legendary has reached the edge in terms of x-position
		if(!this.moveRight && this.dx <= 0){ // left edge
			this.setMoveRight(true);
			generateMoveUp(); // randomizes moveup when legendary has hits the left edge
		}else if(this.moveRight && this.dx >= GameStage.WINDOW_WIDTH - this.width){ // right edge
			this.setMoveRight(false);
			generateMoveUp(); // randomizes moveup when legendary has hits the right edge
		}

		// incrementing/decrementing dx to allow horizontal movement
		if(this.moveRight && this.dx <= GameStage.WINDOW_WIDTH - this.width){ // move to the right until edge is reached
			this.setDX(this.dx + this.speed);
			this.setX(this.getDX());
			moveY(this.moveUp);
		}else if(!this.moveRight && this.dx >= 0){ // move to the left until edge is reached
			this.setDX(this.dx - this.speed);
			this.setX(this.getDX());
			moveY(this.moveUp);
		}
	}

	// GETTERS:
	public int getLegendHealth(){
		return this.health;
	}

	// SETTERS:
	public void setLegendHealth(int damage){
		this.health -= damage;
	}

	public void setMoveUp(boolean direction){
		this.moveUp = direction;
	}

	public int setLegendHealth() {
		return this.health;
	}

}