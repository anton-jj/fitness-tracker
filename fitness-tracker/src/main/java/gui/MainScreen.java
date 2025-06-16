package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainScreen {

	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
        Label welcomeLabel = new Label("welcome to the fitness-tracker");
        root.setCenter(welcomeLabel);

        Scene scene = new Scene(root, 500, 800);

        primaryStage.setTitle("fitness-tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
