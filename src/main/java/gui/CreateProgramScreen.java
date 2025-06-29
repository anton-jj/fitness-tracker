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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import models.Exercise;
import models.Program;
import models.Workout;

public class CreateProgramScreen {
    @Getter
    private Scene scene;
    private VBox mainLayout;
    private TextField programNameField;
    private VBox workoutsContainer;
    private List<WorkoutInputGroup> workoutInput;
    
    public CreateProgramScreen(ScreenManager manager, FitnessService fitnessService) {
        workoutInput = new ArrayList<>();
        setupUi(manager, fitnessService);
    }

    private void setupUi(ScreenManager manager, FitnessService fitnessService){
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        //title
        Label title = new Label("create new program");

        // Program name input
        Label programLabel = new Label("Program Name:");
        programNameField = new TextField();
        programNameField.setPromptText("Enter program name (e.g., 'Push Pull Legs')");
        programNameField.setPrefWidth(300);

        // Workout section
        Label workoutLabel = new Label("Workouts: ");
        workoutLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        workoutsContainer = new VBox(15);
        ScrollPane scrollPane = new ScrollPane(workoutsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);

        //first workout by default
        addWorkoutInput();

        //buttons
        HBox buttonBox = new HBox(10);
        Button addWorkoutBtn = new Button("+ Add workout");
        Button saveBtn = new Button("Save program");
        Button cancelBtn = new Button("Cancel");

        addWorkoutBtn.setOnAction(e -> addWorkoutInput());
        saveBtn.setOnAction(e -> {
            if (validateAndSaveProgram(fitnessService)) {
                clearForm();
                showSuccessAlert();
                manager.activate("main");
            }
        });

        cancelBtn.setOnAction(e -> {
            clearForm();
            manager.activate("main");
        });


        buttonBox.getChildren().addAll(addWorkoutBtn, saveBtn, cancelBtn);

        mainLayout.getChildren().addAll(title, programLabel, programNameField,
                workoutLabel, scrollPane, buttonBox);

        scene = new Scene(mainLayout, 600, 600);
    }
    
    private void addWorkoutInput() {
        WorkoutInputGroup workoutGroup = new WorkoutInputGroup();
        workoutInput.add(workoutGroup);
        mainLayout.getChildren().add(workoutGroup.getContainer());
    }
    
    private boolean validateAndSaveProgram(FitnessService fitnessService) {
        // Validate program name
        String programName = programNameField.getText().trim();
        if (programName.isEmpty()) {
            showErrorAlert("Please enter a program name");
            return false;
        }

        List<Workout> workouts = new ArrayList<>();
        for (WorkoutInputGroup inputGroup : workoutInput) {
            Workout workout = inputGroup.createWorkout();
            if (workout != null) {
                workouts.add(workout);
            }
        }
        
        if (workouts.isEmpty()) {
            showErrorAlert("Please add at least one valid exercise");
            return false;
        }

        //create and save
        try {
            Program program = new Program();
            program.setName(programName);
            program.setWorkouts(new ArrayList<>(workouts));

            fitnessService.addProgram(program);
            return true;
        } catch (Exception e) {
            showErrorAlert("failed to save fowkout" +  e.getMessage());
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
    private  void clearForm() {
        programNameField.clear();
        workoutsContainer.getChildren().clear();
        workoutInput.clear();
        addWorkoutInput();
    }
    
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class for workout input groups
    private class WorkoutInputGroup {
        @Getter
        private VBox container;
        private TextField workoutNameField;
        private VBox exerciseContainer;
        private List<ExerciseInputGroup> exerciseInputs;

        public WorkoutInputGroup() {
            exerciseInputs = new ArrayList<>();
            setupWorkoutUi();
        }

        private void setupWorkoutUi() {
            container = new VBox(10);

            HBox header = new HBox(10);

            Label workoutLabel = new Label("Workout Name");
            workoutLabel.setFont(Font.font("arial", FontWeight.BOLD, 14));

            workoutNameField = new TextField();
            workoutNameField.setPromptText("enter name (e.g 'Upper', 'lowe')");
            workoutNameField.setPrefWidth(200);

            Button removeWorkoutBtn = new Button("Remove workout");
            removeWorkoutBtn.setOnAction(e -> {
                workoutsContainer.getChildren().remove(container);
                workoutInput.remove(this);
            });

            header.getChildren().addAll(workoutLabel, workoutNameField, removeWorkoutBtn);

            //exercise section
            Label exerciseLabel = new Label("Exercises: ");
            exerciseLabel.setFont(Font.font("arial", FontWeight.BOLD, 14));

            exerciseContainer = new VBox(5);

            Button addExerciseButton = new Button("+ Add exercise: ");
            addExerciseButton.setOnAction(e -> addExerciseInput());

            addExerciseInput();

            container.getChildren().addAll(header, exerciseLabel, exerciseContainer);

        }

        private void addExerciseInput() {
            ExerciseInputGroup exerciseGroup = new ExerciseInputGroup(this);
            exerciseInputs.add(exerciseGroup);
            exerciseContainer.getChildren().add(exerciseGroup.getContainer());
        }

        public Workout createWorkout() {
            String name = workoutNameField.getText().trim();
            if (name.isEmpty()) {
                return null;
            }

            List<Exercise> exercises = new ArrayList<>();
            for (ExerciseInputGroup exerciseGroup : exerciseInputs) {
                Exercise exercise = exerciseGroup.createExercise();
                if (exercise != null) {
                    exercises.add(exercise);
                }
            }

                if (exercises.isEmpty()) {
                    return null;
                }

                Workout workout = new Workout();
                workout.setName(name);
                workout.setExercises(new ArrayList<>(exercises));
                return workout;
            }
        }

        //inner class for exercises
    private class ExerciseInputGroup {
        @Getter
        private HBox container;
        private TextField nameField;
        private TextField setsField;
        private TextField repsField;
        private WorkoutInputGroup parentWorkout;

        public ExerciseInputGroup(WorkoutInputGroup parentWorkout) {
            container = new HBox(5);
            container.setPadding(new Insets(5));

            nameField = new TextField();
            nameField.setPromptText("Exercise name");
            nameField.setPrefWidth(155);

            setsField = new TextField();
            setsField.setPromptText("Sets");
            setsField.setPrefWidth(50);

            repsField = new TextField();
            repsField.setPromptText("reps");
            repsField.setPrefWidth(50);

            Button removeBtn = new Button("x");

            removeBtn.setOnAction(e -> {
                parentWorkout.exerciseContainer.getChildren().remove(container);
                parentWorkout.exerciseInputs.remove(this);
            });

            container.getChildren().addAll(nameField, setsField, repsField, removeBtn);
        }
        public Exercise createExercise() {
            String name = nameField.getText().trim();

            if(name.isEmpty()) {
                return  null;
            }

            try {
                int sets = Integer.parseInt(setsField.getText().trim());
                int reps = Integer.parseInt(setsField.getText().trim());
                return new Exercise(name, sets, reps);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        }
    }