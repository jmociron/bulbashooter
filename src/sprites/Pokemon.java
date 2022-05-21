package sprites;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import main.GameTimer;

// parent class for enemy pokemon
public class Pokemon extends Sprite{

	// PROPERTIES (unique to Pokemon):
	protected boolean alive = true;
	protected int speed;
	protected int damage;
	protected boolean moveRight;

	// IMAGES:
	protected int imageCount;
	protected Image[] baseImages;

	// CONSTANTS:
	protected final static int MAX_SPEED = 5;
	protected final static int MOVE_LEFT = 0;
	protected final static int MOVE_RIGHT = 1;

	public Pokemon(int xPos, int yPos, GameTimer gametimer) {
		super(xPos, yPos, gametimer);
		this.alive = true;
		this.dx = x;
	}

	// method for randomly generating speed
	public void generateSpeed(){
		Random r = new Random();
		this.speed = r.nextInt(MAX_SPEED) + 1; // generates speed randomly from 1 to 5
		int intRight = r.nextInt(2); // randomizes moveRight's initial value

		switch(intRight){
			case MOVE_LEFT:
				this.setMoveRight(false);
				break;
			case MOVE_RIGHT:
				this.setMoveRight(true);
				break;
		}
	}

	// method for randomly picking image
	public void generateImage(){
		Random r = new Random();
		int index = r.nextInt(imageCount);
		this.loadImage(baseImages[index]);
	}

	public void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);
    }

	// method for horizontal movement
	public void move(){

		// checks if pokemon has reached the edge in terms of x-position
		if(!this.moveRight && this.dx <= 0){ // left edge
			this.setMoveRight(true);
		}else if(this.moveRight && this.dx >= GameStage.WINDOW_WIDTH - this.width){ // right edge
			this.setMoveRight(false);
		}

		// incrementing/decrementing dx to allow horizontal movement
		if(this.moveRight && this.dx <= GameStage.WINDOW_WIDTH - this.width){ // move to the right until edge is reached
			this.setDX(this.dx + this.speed);
			this.setX(this.getDX());
		}else if(!this.moveRight && this.dx >= 0){ // move to the left until edge is reached
			this.setDX(this.dx - this.speed);
			this.setX(this.getDX());
		}
	}

	// GETTERS:
	public boolean isAlive() {
		return this.alive;
	}

	// SETTERS:
	public void checkCollision(Starter myStarter) {
		myStarter.decreaseStrength(this.damage);
	}

	public void setAlive(boolean alive){
		this.alive = alive;
	}

	public void setMoveRight(boolean direction){
		this.moveRight = direction;
	}

}
