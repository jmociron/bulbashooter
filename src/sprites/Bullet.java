package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import main.GameTimer;

public class Bullet extends Sprite {

	// PROPERTIES
	private int bulletStrength;

	// CONSTANTS:
	private final int BULLET_SPEED = 20;
	private final static Image BULLET_IMAGE = new Image("images/bullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	private final static int BULLET_WIDTH = 20;


	public Bullet(int x, int y, GameTimer gametimer, int strength){
		super(x, y, gametimer);
		this.loadImage(Bullet.BULLET_IMAGE);
		this.dx = x;
		this.bulletStrength = strength;
	}

	public void render(GraphicsContext gc){
		gc.drawImage(BULLET_IMAGE, this.dx, this.y);
	}


	//method that will move/change the x position of the bullet
	public void move(){

		// if the bullet has not reached the right edge of the window
		if(this.visible && this.dx <= GameStage.WINDOW_WIDTH){
			// the bullet will continue to move to the right
			this.dx += BULLET_SPEED;
			this.x = this.dx;
		}else{ // if the bullet has reached the right edge
			// bullet will no longer be visible
			this.visible = false;
		}

	}

	// SETTERS:
	public void setStrength(int strength){
		this.bulletStrength = strength;
	}

	// GETTERS:
	public int getStrength(){
		return this.bulletStrength;
	}


}