package gui;

import java.util.ArrayList;
import java.util.List;

import com.antonjj.FitnessService;
import com.antonjj.ScreenManager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Exercise;
import models.Workout;

public class CreateProgramScreen {
    private Scene scene;
    private VBox exerciseContainer;
    private TextField programNameField;
    private TextField workoutNameField;
    private List<ExerciseInputGroup> exerciseInputs;
    
    public CreateProgramScreen(ScreenManager manager, FitnessService fitnessService) {
        exerciseInputs = new ArrayList<>();
        
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        
        // Program name input
        Label programLabel = new Label("Program Name:");
        programNameField = new TextField();
        programNameField.setPromptText("Enter program name (e.g., 'Push Pull Legs')");
        
        // Workout name input
        Label workoutLabel = new Label("Workout Name:");
        workoutNameField = new TextField();
        workoutNameField.setPromptText("Enter workout name (e.g., 'Push Day')");
        
        // Exercise container with scroll
        Label exerciseLabel = new Label("Exercises:");
        exerciseContainer = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(exerciseContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        
        // Add first exercise input by default
        addExerciseInput();
        
        // Buttons
        HBox buttonBox = new HBox(10);
        Button addExerciseBtn = new Button("Add Exercise");
        Button saveBtn = new Button("Save Program");
        Button cancelBtn = new Button("Cancel");
        
        addExerciseBtn.setOnAction(e -> addExerciseInput());
        
        saveBtn.setOnAction(e -> {
            if (validateAndSaveProgram(fitnessService)) {
                showSuccessAlert();
                manager.activate("main");
            }
        });
        
        cancelBtn.setOnAction(e -> manager.activate("main"));
        
        buttonBox.getChildren().addAll(addExerciseBtn, saveBtn, cancelBtn);
        
        mainLayout.getChildren().addAll(
            programLabel, programNameField,
            workoutLabel, workoutNameField,
            exerciseLabel, scrollPane,
            buttonBox
        );
        
        scene = new Scene(mainLayout, 600, 500);
    }
    
    private void addExerciseInput() {
        ExerciseInputGroup inputGroup = new ExerciseInputGroup();
        exerciseInputs.add(inputGroup);
        exerciseContainer.getChildren().add(inputGroup.getContainer());
    }
    
    private boolean validateAndSaveProgram(FitnessService fitnessService) {
        // Validate program name
        String programName = programNameField.getText().trim();
        if (programName.isEmpty()) {
            showErrorAlert("Please enter a program name");
            return false;
        }
        
        // Validate workout name
        String workoutName = workoutNameField.getText().trim();
        if (workoutName.isEmpty()) {
            showErrorAlert("Please enter a workout name");
            return false;
        }
        
        // Create exercises
        List<Exercise> exercises = new ArrayList<>();
        for (ExerciseInputGroup inputGroup : exerciseInputs) {
            Exercise exercise = inputGroup.createExercise();
            if (exercise != null) {
                exercises.add(exercise);
            }
        }
        
        if (exercises.isEmpty()) {
            showErrorAlert("Please add at least one valid exercise");
            return false;
        }
        
        // Create workout
        Workout workout = new Workout();
        workout.setName(workoutName);
        workout.setExercises(new ArrayList<>(exercises));

        // Save program
        try {
            fitnessService.addWorkout(workout);
            return true;
        } catch (Exception e) {
            showErrorAlert("Failed to save program: " + e.getMessage());
            return false;
        }
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Program Created");
        alert.setContentText("Your program has been saved successfully!");
        alert.showAndWait();
    }
    
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public Scene getScene() {
        return this.scene;
    }
    
    // Inner class for exercise input groups
    private class ExerciseInputGroup {
        private HBox container;
        private TextField nameField;
        private TextField setsField;
        private TextField repsField;
        private TextField weightField;
        
        public ExerciseInputGroup() {
            container = new HBox(10);
            container.setPadding(new Insets(5));
            
            nameField = new TextField();
            nameField.setPromptText("Exercise name");
            nameField.setPrefWidth(150);
            
            setsField = new TextField();
            setsField.setPromptText("Sets");
            setsField.setPrefWidth(60);
            
            repsField = new TextField();
            repsField.setPromptText("Reps");
            repsField.setPrefWidth(60);
            
            weightField = new TextField();
            weightField.setPromptText("Weight (kg)");
            weightField.setPrefWidth(80);
            
            Button removeBtn = new Button("Remove");
            removeBtn.setOnAction(e -> {
                exerciseContainer.getChildren().remove(container);
                exerciseInputs.remove(this);
            });
            
            container.getChildren().addAll(
                nameField, setsField, repsField, weightField, removeBtn
            );
        }
        
        public HBox getContainer() {
            return container;
        }
        
        public Exercise createExercise() {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                return null;
            }
            
            try {
                int sets = Integer.parseInt(setsField.getText().trim());
                int reps = Integer.parseInt(repsField.getText().trim());
                
                Exercise exercise = new Exercise(name, sets, reps);
                
                // Set weight if provided
                String weightText = weightField.getText().trim();
                if (!weightText.isEmpty()) {
                    double weight = Double.parseDouble(weightText);
                    exercise.setWeight(weight);
                }
                
                return exercise;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}