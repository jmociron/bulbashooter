package sprites;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import main.GameStage;
import main.GameTimer;

public class Starter extends Sprite{

	// PROPERTIES (unique to Starter Pokemon):
	private String name;
	private int strength;
	private boolean alive;
	private GameTimer gametimer;
	private ArrayList<Bullet> bullets;

	// CONSTANTS:
	private final static Image BULBASAUR_IMAGE = new Image("images/bulbasaur.gif",Starter.BULBASAUR_WIDTH,Starter.BULBASAUR_WIDTH,false,false);
	private final static Image VENUSAUR_IMAGE = new Image("images/venusaur.gif",Starter.VENUSAUR_WIDTH,Starter.VENUSAUR_WIDTH,false,false);
	private final static int BULBASAUR_WIDTH = 50;
	private final static int VENUSAUR_WIDTH = 75;

	public Starter(String name, int x, int y, GameTimer gametimer){

		super(x,y, gametimer);
		this.name = name;

		Random r = new Random();
		this.strength = r.nextInt(150 - 100) + 100; // generates strength from 100-150

		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.gametimer = gametimer;
		this.loadImage(Starter.BULBASAUR_IMAGE);
	}

	//method called if spacebar is pressed
	public void shoot(){

		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width+20);
		int y = (int) (this.y + this.height/2);

		Bullet bullet = new Bullet(x, y, gametimer, this.getStrength());
		bullets.add(bullet);
	}

	//method called if arrow keys are pressed
	public void move() {

		int tempx = this.x + this.dx; // computes for the position to be moved horizontally
		int tempy = this.y + this.dy; // computes for the position to be moved vertically


		// early return if the starter has reached either ends of the screen
		if(tempx < 0 || tempx > (GameStage.WINDOW_WIDTH - BULBASAUR_WIDTH)){ // deducts starter's width
			return;
		}

		this.x += this.dx; // moves horizontally if still within bounds

		// early return if the starter has reached either ends of the screen
		if(tempy < 0 || tempy > (GameStage.WINDOW_HEIGHT - BULBASAUR_WIDTH)){ // deducts starter's height
			return;
		}

		this.y += this.dy; // moves vertically if still within bounds

	}

	// SETTERS:
	public void changeImage(){
		this.loadImage(Starter.VENUSAUR_IMAGE);
	}

	public void revertImage(){
		this.loadImage(Starter.BULBASAUR_IMAGE);
	}

	public void increaseStrength(double strength){
		this.strength += strength;
	}

	public void die(){
		this.alive = false;
	}

	// GETTERS:

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public int getStrength(){
		return this.strength;
	}

	public void setStrength(int strength){
		this.strength = strength;
	}

	public void decreaseStrength(double damage){
		this.strength -= damage;
		if(this.strength <= 0){
			this.die();
		}
	}
}