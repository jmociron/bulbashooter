package main;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sprites.LegendaryPokemon;
import sprites.Bullet;
import sprites.WildPokemon;
import sprites.Potion;
import sprites.PowerUp;
import sprites.RareCandy;
import sprites.Starter;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	// PROPERTIES:
	private GraphicsContext gc;
	private Scene theScene;
	private Starter myStarter;
	private LegendaryPokemon legendary;

	// DISPLAY VARIABLES:
	private int killedPokemon;
	private int seconds;
	private GameStage gameStage;
	public int displayStrength;

	// ARRAY LISTS:
	private ArrayList<WildPokemon> wilds;
	private ArrayList<Bullet> bullets;
	private ArrayList<PowerUp> powerups;

	// TIMERS:
	private long startWildSpawn;
	private long startLegendSpawn;
	private long startPowerupSpawn;
	private long startCandyBuff;
	private long startGame;
	private long startLegendCollision;

	// CHECKERS (for powerups):
	private boolean hasLegendSpawned = false;
	private boolean candyBuffed = false;
	private boolean legendCollided = false;
	private boolean legendKilled = false;

	// CONSTANTS:
	private final static double ONE_SECOND = 1000000000.0; // nanosecond to second
	private final static int GAME_SECONDS = 60; // nanosecond to second
	public static final int LOSE_NUM = 1;
    public static final int WIN_NUM = 2;

	GameTimer(GraphicsContext gc, Scene theScene, GameStage gameStage){

		this.gc = gc;
		this.gameStage = gameStage;
		this.theScene = theScene;
		this.myStarter = new Starter("Bulbasaur", 150, 250, this);
		this.displayStrength = this.myStarter.getStrength();
		this.startGame = System.nanoTime();

		//initial values
		this.killedPokemon = 0;

		//instantiate the ArrayList of Wild Pokemon
		this.wilds = new ArrayList<WildPokemon>();

		//instantiate the ArrayList of PowerUps
		this.powerups = new ArrayList<PowerUp>();
		this.startPowerupSpawn = System.nanoTime();

		// spawns 7 wild pokemon
		this.spawnWildPokemon(WildPokemon.START_WILD_NUM);
		this.startWildSpawn = System.nanoTime();

		// instantiates a new boss with a fixed staring position
		this.legendary = new LegendaryPokemon(LegendaryPokemon.xPos, LegendaryPokemon.yPos, this);
		this.startLegendSpawn = System.nanoTime();

		//call method to handle mouse click event
		this.handleKeyPressEvent();

	}

	@Override
	public void handle(long currentNanoTime) {

		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		myStarter.render(this.gc); // renders the starter pokemon
		myStarter.move(); // moves the starter pokemon depending on key event

		// timer for respawning
		double gameTimer = (currentNanoTime - this.startGame) / ONE_SECOND;

		this.seconds = GAME_SECONDS-(int)gameTimer;

		if(gameTimer > GameTimer.GAME_SECONDS){
			System.out.println("TIME'S UP! Game over!");
			this.gameStage.flashGameOver(WIN_NUM);
			this.stop();
		}

		// timer for respawning
		double wildTimer = (currentNanoTime - this.startWildSpawn) / ONE_SECOND;

		if(wildTimer > WildPokemon.WILD_RESPAWN_DELAY){
			this.spawnWildPokemon(WildPokemon.RESPAWN_WILD_NUM); // spawns 3 fishes after 5 seconds
			this.startWildSpawn = System.nanoTime();
		}

		// timer for rendering the legendary pokemon
		double legendTimer = (currentNanoTime - this.startLegendSpawn) / ONE_SECOND;

		if(legendTimer > LegendaryPokemon.LEGEND_DELAY && this.legendary.isAlive()){
			hasLegendSpawned = true;
			renderLegendaryPokemon(); // renders the boss after 30 seconds
			moveLegendary(currentNanoTime);
		}

		// timer for powerup respawning
		double powerupTimer = (currentNanoTime - this.startPowerupSpawn) / ONE_SECOND;

		if(powerupTimer > PowerUp.POWERUP_DELAY){
			this.spawnPowerup(PowerUp.POWERUP_COUNT); // spawns a new powerup after 10 seconds
			this.startPowerupSpawn = System.nanoTime();
		}

		// timer for the 3-second immortality
		double candyTimer = (currentNanoTime - this.startCandyBuff) / ONE_SECOND;

		if(this.candyBuffed && candyTimer > RareCandy.CANDY_EFFECT_DURATION){
			this.candyBuffed = false;
			this.myStarter.revertImage(); // changes image back to bulbasaur
			this.displayStrength = this.myStarter.getStrength(); // display strength is reverted back to normal
			System.out.println("Immortality has worn off.");
		}

		// timer for collision cooldown
		double collisionTimer = (currentNanoTime - this.startLegendCollision) / ONE_SECOND;

		// allows starter to take damage after a 1 second delay
		if(collisionTimer > LegendaryPokemon.COLLISION_DELAY){
			this.legendCollided = false;
		}

		// move methods
		moveBullets();
		moveWildPokemon();

		// render methods
		renderBullets();
		renderWildPokemon();
		renderPowerup();

		// checker & stats methods
		powerupChecker(currentNanoTime);
		displayStat();
		displayTimer();

		if(!this.myStarter.isAlive()) {
			System.out.println("The starter pokemon has fainted!");
			this.gameStage.flashGameOver(LOSE_NUM);
			this.stop();
		}

		if(this.legendary.getLegendHealth() <= 0 && !this.legendKilled){
			this.legendKilled = true;
			this.legendary.setAlive(false); // sets legendary to fainted
			this.legendary.setVisible(false); // sets legendary to invisible
			System.out.println("The legendary pokemon has been defeated!");
		}

	}

	//method for displaying the game stats on the screen
	private void displayStat() {
		Font theFont = Font.font("Berlin Sans Fb",FontWeight.EXTRA_BOLD,15);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.SNOW);
		this.gc.fillText("Score: "+ this.killedPokemon, GameStage.WINDOW_WIDTH*0.01, GameStage.WINDOW_HEIGHT*0.05);
		if(this.displayStrength < 0){ // if the strength goes below 0, sets the display strength back to 0
			this.setDisplayStrength(0);
		}
		this.gc.fillText("Strength: "+ this.displayStrength, GameStage.WINDOW_WIDTH*0.01, GameStage.WINDOW_HEIGHT*0.08);

	}

	// method for displaying the time left
	private void displayTimer() {
		Font theFont = Font.font("Berlin Sans Fb",FontWeight.EXTRA_BOLD,40);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.SNOW);
		this.gc.fillText(" "+this.seconds, GameStage.WINDOW_WIDTH*0.90, GameStage.WINDOW_HEIGHT*0.09);
	}


	//method that will spawn/instantiate three wild pokemon at a randomly generated location
	private void spawnWildPokemon(int wildCount){

		Random r = new Random(); // randomly generates starting position

		for(int i=0; i<wildCount; i++){

			// image width is deducted to ensure that the fish will only spawn within the window
			int x = r.nextInt(GameStage.WINDOW_WIDTH - WildPokemon.WILD_WIDTH);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - WildPokemon.WILD_WIDTH);

			WildPokemon w = new WildPokemon(x, y, this);
			wilds.add(w); // adds generated wild pokemon to the array list
		}

	}

	//method that will spawn/instantiate a powerup; called every 10 seconds
	private void spawnPowerup(int powerupCount){

		Random r = new Random();

		for(int i=0; i<powerupCount; i++){

			// generates a random location at the left side of the screen
			int x = r.nextInt(GameStage.WINDOW_WIDTH/2);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT/2);

			int type = r.nextInt(2); // randomly generates the type of powerup

			switch(type){
				case Potion.POTION_TYPE: // creates a potion
					PowerUp potion = new Potion(x, y, this);
					potion.setSpawnTime(System.nanoTime()); // saves the spawning time
					this.powerups.add(potion); // adds the candy to the array list
					break;
				case RareCandy.CANDY_TYPE: // creates a rare candy
					PowerUp candy = new RareCandy(x, y, this);
					candy.setSpawnTime(System.nanoTime()); // saves the spawning time
					this.powerups.add(candy); // adds the candy to the array list
					break;
			}
		}
	}

	// method for checking the state of the powerups
	private void powerupChecker(long time){

		for(int i=0; i<this.powerups.size(); i++){ // iterates through all the powerups initiated

			PowerUp current = this.powerups.get(i); // powerup being checked

			// timer for how long the powerup is on-screen
			double timer = (time - current.getSpawnTime()) / ONE_SECOND;

			// if the starter collides with a powerup
			if(current.collidesWith(myStarter)){

				switch(current.getType()){
					case Potion.POTION_TYPE: // potion (pearl)
						this.myStarter.increaseStrength(current.getStrength()); // strength is increased by 50
						this.displayStrength = this.myStarter.getStrength();
						System.out.println("Starter strength increased by 50!");
						current.setVisible(false); // sets the potion to invisible
						this.powerups.remove(i); // removes potion from the array list
						break;
					case RareCandy.CANDY_TYPE: // candy (star)
						this.candyBuffed = true; // starter will not take damage from collision
						this.displayStrength = current.getStrength(); // display strength is set to 9999999
						this.myStarter.changeImage(); // changes the starter from bulbasaur to venusaur
						System.out.println("Starter is immortal for 5 seconds!");
						this.startCandyBuff = System.nanoTime(); // gets the starting time of candy effect
						current.setVisible(false); // sets the candy to invisible
						this.powerups.remove(i); // removes candy from the array list
						break;
				}
			}
			// if the starter did not get the powerup and the on-screen duration is finished
			if(timer > PowerUp.POWERUP_DURATION){
				current.setVisible(false); // sets the powerup to invisible
				this.powerups.remove(i); // removes the powerup from the array list
				this.displayStrength = this.myStarter.getStrength(); // reverts display strength to normal strength
			}
		}
	}

	// method that will render/draw the wild pokemon to the canvas
	private void renderWildPokemon(){
		for (WildPokemon w : this.wilds){
			w.render(this.gc);
		}
	}

	// method that will render/draw the legendary pokemon to the canvas
	private void renderLegendaryPokemon(){
		this.legendary.render(this.gc);
	}

	// method that will render/draw the bullets to the canvas
	private void renderBullets(){

		bullets = this.myStarter.getBullets();

		for(Bullet b : bullets){
			b.render(this.gc);
		}
	}

	// method that will render/draw the powerups to the canvas
	private void renderPowerup(){
		for (PowerUp p : this.powerups){
			p.render(this.gc);
		}
	}

	//method that will move the bullets shot by the starter
	private void moveBullets(){

		//create a local arraylist of Bullets for the bullets 'shot' by the starter
		ArrayList<Bullet> bList = this.myStarter.getBullets();

		// iterates through all the powerups initiated
		for(int i = 0; i < bList.size(); i++){

			Bullet b = bList.get(i);

			if(b.isVisible()){
				b.move();
				// if the bullet collides with the legendary, and the legendary is visible
				if(b.collidesWith(this.legendary) && this.hasLegendSpawned && !this.legendKilled){
					// bullet deducts health from the legendary on collision
					this.legendary.setLegendHealth(b.getStrength());
					bList.remove(i); // removes the bullet from the array
					System.out.println("The legendary was hit!");
				}

				for(int j=0; j<this.wilds.size(); j++) {

					WildPokemon w = this.wilds.get(j);

					if(b.collidesWith(w)) { // if a wild pokemon has collided with a bullet
						System.out.println("A wild pokemon has fainted!");
						this.killedPokemon++;
						w.setAlive(false); // set the wild pokemon to fainted
						w.setVisible(false); // set the wild pokemon to invisible
						b.setVisible(false); // sets the bullet's visibility to false

						try{
							wilds.remove(j); // removes the wild pokemon from the array list
							bullets.remove(i); // removes the bullet from the array list
						}catch(Exception e){
							// catches the error if the bullet hits 2 pokemon
							System.out.println("Two wild pokemon were hit at once!");
						}
					}
 				}

			} else { // removes the bullet from the array list if not visible
				bullets.remove(i);
			}
		}
	}

	//method that will move the wild pokemon
	private void moveWildPokemon(){

		//Loop through the wild pokemon array list
		for(int i = 0; i < this.wilds.size(); i++){

			WildPokemon w = this.wilds.get(i);

			if(w.isAlive()){
				w.move(); // moves the wild pokemon while it is alive
				if (w.collidesWith(this.myStarter)){ // if a wild pokemon collides with the starter

					if(!this.candyBuffed){ // check if candy effect is on
						w.setAlive(false); // sets wild pokemon to fainted
						w.setVisible(false); // sets wild pokemon to invisible
						wilds.remove(i); // removes the wild pokemon from the array list
						System.out.println("A wild pokemon has fainted.");
						w.checkCollision(this.myStarter); // deducts strength from the starter
						this.displayStrength = this.myStarter.getStrength(); // displays deducted strength
					}else{
						w.setAlive(false); // sets wild pokemon to fainted
						w.setVisible(false); // sets wild pokemon to invisible
						wilds.remove(i); // removes the wild pokemon from the array list
						System.out.println("A wild pokemon has fainted.");
					}
				}
			}
			else{
				this.wilds.remove(i); // removes wild pokemon from the array list when fainted
			}
		}
	}

	//method that will move the legendary pokemon
	private void moveLegendary(long currentNanoTime){

		if(this.legendary.isAlive()){

			this.legendary.move(); // moves legendary while alive

			if(this.legendary.collidesWith(this.myStarter) && !this.candyBuffed && !this.legendCollided){
				this.legendary.checkCollision(this.myStarter); // deducts strength from the starter
				this.displayStrength = this.myStarter.getStrength(); // displays deducted strength
				System.out.println("Starter has collided with the legendary!");
				this.startLegendCollision = System.nanoTime(); // starts collision cooldown
				this.legendCollided = true;
			}
		}

	}

	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		            }
		        });
    }

	//method that will move the starter depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) this.myStarter.setDY(-3);

		if(ke==KeyCode.LEFT) this.myStarter.setDX(-3);

		if(ke==KeyCode.DOWN) this.myStarter.setDY(3);

		if(ke==KeyCode.RIGHT) this.myStarter.setDX(3);

		if(ke==KeyCode.SPACE) this.myStarter.shoot();

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the starter's movement; set the starter's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myStarter.setDX(0);
		this.myStarter.setDY(0);
	}

	private void setDisplayStrength(int strength){
		this.displayStrength = strength;
	}

}