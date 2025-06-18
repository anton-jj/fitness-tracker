package gui;

import com.antonjj.ScreenManager;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CreateProgramScreen {
		    private Scene scene;

		    public CreateProgramScreen(ScreenManager manager) {

		        Button backButton = new Button("Back to Main");
		        backButton.setOnAction(e -> manager.activate("main"));

		        VBox layout = new VBox(10, backButton);
		        scene = new Scene(layout, 400, 300);
		    }

		    public Scene getScene() {
		        return this.scene;
		    }
		
	}
