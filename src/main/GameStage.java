package main;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameStage {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private ImageView imgView;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;

	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gametimer = new GameTimer(this.gc,this.scene,this);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("BULBASHOOTER: Pokemon Shooting Game");
		this.stage.setScene(this.scene);
		this.imgView = this.createBG();

		//invoke the start method of the animation timer
		this.gametimer.start();

		this.stage.show();
		this.root.getChildren().addAll(this.imgView, canvas);
	}

	private ImageView createBG() {
		Image bg = new Image("images/gamestagebg.png");
		ImageView view = new ImageView();
		view.setImage(bg);
		return view;
	}

	public void flashGameOver(int result) {
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage(result);
				gameover.setStage(stage);
				stage.setScene(gameover.getScene());
			}
		});
	}

	public Scene getScene() {
		return this.scene;
	}



}
