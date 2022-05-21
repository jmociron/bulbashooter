package main;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.Menu;

public class GameOverStage {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private Text text;
	private VBox vbox;
	private int gameOverNum;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;

	public GameOverStage(int gameOverNum) {
		StackPane root = new StackPane();
		this.scene = new Scene(root, GameOverStage.WINDOW_WIDTH, GameOverStage.WINDOW_HEIGHT);
		this.gameOverNum = gameOverNum;
		this.imgView = this.createBG(gameOverNum);
		this.vbox = this.createVBox();

		root.getChildren().addAll(this.imgView, this.vbox);
	}

	private ImageView createBG(int gameOverNum) {
		switch(gameOverNum){
			case GameTimer.LOSE_NUM:
				Image bg1 = new Image("images/losebg.png");
				ImageView view1 = new ImageView();
				view1.setImage(bg1);
				return view1;
			case GameTimer.WIN_NUM:
				Image bg2 = new Image("images/winbg.png");
				ImageView view2 = new ImageView();
				view2.setImage(bg2);
				return view2;
		}
		return imgView;
	}

	private VBox createVBox() {
    	VBox vbox = new VBox();

    	switch(gameOverNum){
	    	case GameTimer.LOSE_NUM:
	    		this.text = new Text("YOU LOSE!");
		        this.text.setX(250);
		        this.text.setY(0);
		        this.text.setFill(Color.YELLOW);
				Font titleFont1 = Font.font("Berlin Sans Fb",FontWeight.BOLD,50);
				this.text.setFont(titleFont1);
				this.text.setStroke(Color.BLUE);
				this.text.setStrokeWidth(2);
				break;
	    	case GameTimer.WIN_NUM:
	    		this.text = new Text("YOU WIN!");
		        this.text.setX(250);
		        this.text.setY(0);
		        this.text.setFill(Color.YELLOW);
				Font titleFont2 = Font.font("Berlin Sans Fb",FontWeight.BOLD,50);
				this.text.setFont(titleFont2);
				this.text.setStroke(Color.BLUE);
				this.text.setStrokeWidth(2);
				break;

    	}

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);


        Button b1 = new Button("    Play Again?    ");
        Button b2 = new Button("    Exit    ");

        b1.setStyle("-fx-font: 15 calibri; -fx-background-color: #E06666; -fx-text-fill: #FFFAFA; "
        		+ "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");
        b2.setStyle("-fx-font: 15 calibri; -fx-background-color: #E06666; -fx-text-fill: #FFFAFA; "
        		+ "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");

        vbox.getChildren().add(text);
        vbox.getChildren().add(b1);
        vbox.getChildren().add(b2);

        this.setMouseHandler1(b1);
        this.setMouseHandler2(b2);

        return vbox;
    }

	private void setMouseHandler1(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				Menu menu = new Menu();
				menu.setStage(stage); //when clicked, it will go back to the splash screen
			}
		});

	}

	private void setMouseHandler2(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.exit(0); //when clicked, the system will exit
			}
		});
	}


	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Game Over!");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	Scene getScene(){
		return this.scene;
	}
}
