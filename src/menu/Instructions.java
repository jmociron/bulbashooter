package menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class Instructions {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private VBox vbox;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;

	public Instructions () {
		StackPane root = new StackPane();
		this.scene = new Scene(root, Instructions.WINDOW_WIDTH,Instructions.WINDOW_HEIGHT);
		this.imgView = this.createBG();
		this.vbox = this.createVBox();

		root.getChildren().addAll(this.imgView, this.vbox);
	}

	private ImageView createBG() {
		Image bg = new Image("images/instructions.png");
		ImageView view = new ImageView();
		view.setImage(bg);
		return view;
	}

	private VBox createVBox() {

		VBox vbox = new VBox();

		vbox.setPadding(new Insets(15));
        vbox.setSpacing(0);

        Button b1 = new Button("Back");
        Button b2 = new Button(" Exit ");

        b1.setTranslateX(15);
		b1.setTranslateY(400);
		b1.setStyle("-fx-font: 12 arial; -fx-background-color: #4682B4; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 2px;");

		b2.setTranslateX(15);
		b2.setTranslateY(400);
		b2.setStyle("-fx-font: 12 arial; -fx-background-color: #4682B4; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 2px;");

        vbox.getChildren().add(b1);
        vbox.getChildren().add(b2);

        this.setMouseHandler(b1);
        this.setMouseHandler2(b2);

		return vbox;

	}

	private void setMouseHandler(Button b1) {
		b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				Menu menu = new Menu();
				menu.setStage(stage);
			}
		});

	}

	private void setMouseHandler2(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});

	}

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Instructions");
		this.stage.setScene(this.scene);
		this.stage.show();
	}



	Scene getScene(){
		return this.scene;
	}

}