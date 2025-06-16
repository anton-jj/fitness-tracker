package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScreen {

	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setTop(setupButtons());
		Scene scene = new Scene(root,600,500, Color.BEIGE);
		
		
		primaryStage.setTitle("fitness-tracker");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	private HBox setupButtons() {
		HBox buttonBar = new HBox(10);
		
		Button createProgram = new Button("create program");
		Button logWeight = new Button("log weight");
		Button logWorkout = new Button("log workout");
		
		buttonBar.getChildren().addAll(createProgram, logWeight, logWorkout);
		return buttonBar;
	}
	
	
}
