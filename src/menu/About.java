package menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class About {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private Text text;
	private VBox vbox;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;

	public About() {
		StackPane root = new StackPane();
		this.scene = new Scene(root, About.WINDOW_WIDTH,About.WINDOW_HEIGHT,Color.CADETBLUE);
		this.imgView = this.createBG();
		this.vbox = this.createVBox();

		root.getChildren().addAll(this.imgView, this.vbox);
	}

	private ImageView createBG() {
		Image bg = new Image("images/aboutus.png");
		ImageView view = new ImageView();
		view.setImage(bg);
		return view;
	}

	private VBox createVBox() {
		VBox vbox = new VBox();
        this.text = new Text("ABOUT US");
        this.text.setTranslateX(5);
        this.text.setX(250);
        this.text.setY(0);
        this.text.setFill(Color.BLUE);
		Font titleFont = Font.font("Berlin Sans Fb",FontWeight.BOLD,30);
		this.text.setFont(titleFont);
		this.text.setStroke(Color.BLUE);
		this.text.setStrokeWidth(1);
		vbox.setPadding(new Insets(15));
        vbox.setSpacing(0);

        Button b1 = new Button("Back");
        Button b2 = new Button(" Exit ");

        b1.setTranslateX(15);
		b1.setTranslateY(400);
		b1.setStyle("-fx-font: 12 arial; -fx-background-color: #4682B4; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 2px;");

		b2.setTranslateX(15);
		b2.setTranslateY(410);
		b2.setStyle("-fx-font: 12 arial; -fx-background-color: #4682B4; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 2px;");

		//vbox.getChildren().add(text);
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
		this.stage.setTitle("About Us");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	Scene getScene(){
		return this.scene;
	}

}
