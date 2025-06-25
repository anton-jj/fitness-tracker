package gui;

import com.antonjj.FitnessService;
import com.antonjj.ScreenManager;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateProgramScreen {
		    private Scene scene;

		    public CreateProgramScreen(ScreenManager manager, FitnessService fitnessService) {
		    	VBox layout = new VBox();
		    	
		    	Button addExercise = new Button();
		    	layout.getChildren().add(addExercise);

		    	layout.getChildren().add(layout.getChildren().size() - 1, createExerciseInputGroup());
		    	addExercise.setOnAction(e -> {
		    	    layout.getChildren().add(layout.getChildren().size() - 1, createExerciseInputGroup());
		    	});
		    	
		        scene = new Scene(layout, 400, 300);
		    }
		    
		    private HBox createExerciseInputGroup() {
		    	HBox group = new HBox();
		    	
		    	TextField nameField = new TextField();
		    	nameField.setPromptText("Name of exercise");
		    	TextField setsField = new TextField();
		    	setsField.setPromptText("Eneter sets for the exercise");
		    	TextField repsField = new TextField();

		    	repsField.setPromptText("Eneter reps per set");
		    	group.getChildren().addAll(nameField, setsField, repsField);
		    	
		    	return group;
		    	
		    }

		    public Scene getScene() {
		        return this.scene;
		    }
		
	}
