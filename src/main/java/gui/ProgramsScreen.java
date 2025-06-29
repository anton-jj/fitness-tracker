package gui;

import java.util.List;

import com.antonjj.FitnessService;
import com.antonjj.ScreenManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import models.Exercise;
import models.Program;
import models.Workout;

public class ProgramsScreen {
    @Getter
    private Scene scene;
    private VBox mainLayout;
    private VBox programsContainer;
    private FitnessService fitnessService;
    private ScreenManager screenManager;

    public ProgramsScreen(ScreenManager manager, FitnessService fitnessService) {
        this.fitnessService = fitnessService;
        this.screenManager = manager;

        setupUI();
        scene = new Scene(createScrollableContent(), 800, 600);
        refreshPrograms();
    }

    private ScrollPane createScrollableContent() {
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    private void setupUI() {
        mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Title and controls
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("My Programs");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button backButton = new Button("← Back to Main");
        backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> screenManager.activate("main"));

        Button refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> refreshPrograms());

        HBox.setHgrow(title, Priority.ALWAYS);
        header.getChildren().addAll(title, refreshButton, backButton);

        // Programs container
        programsContainer = new VBox(15);

        mainLayout.getChildren().addAll(header, programsContainer);
    }

    public void refreshPrograms() {
        programsContainer.getChildren().clear();

        List<Program> programs = fitnessService.getPrograms();

        if (programs.isEmpty()) {
            Label noPrograms = new Label("No programs found. Create your first program!");
            noPrograms.setFont(Font.font("Arial", 16));
            noPrograms.setStyle("-fx-text-fill: #666;");
            programsContainer.getChildren().add(noPrograms);
        } else {
            for (Program program : programs) {
                programsContainer.getChildren().add(createProgramCard(program));
            }
        }
    }

    private VBox createProgramCard(Program program) {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: #ddd; -fx-border-width: 2; -fx-padding: 15; " +
                "-fx-background-color: white; -fx-background-radius: 5; -fx-border-radius: 5;");

        // Program header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label programName = new Label(program.getName());
        programName.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label workoutCount = new Label("(" + program.getWorkouts().size() + " workouts)");
        workoutCount.setFont(Font.font("Arial", 12));
        workoutCount.setStyle("-fx-text-fill: #666;");

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 10;");
        deleteButton.setOnAction(e -> deleteProgram(program));

        HBox.setHgrow(programName, Priority.ALWAYS);
        header.getChildren().addAll(programName, workoutCount, deleteButton);

        // Workouts preview
        VBox workoutsPreview = new VBox(8);
        for (Workout workout : program.getWorkouts()) {
            HBox workoutRow = new HBox(10);
            workoutRow.setAlignment(Pos.CENTER_LEFT);
            workoutRow.setStyle("-fx-padding: 8; -fx-background-color: #f5f5f5; -fx-background-radius: 3;");

            Label workoutName = new Label("• " + workout.getName());
            workoutName.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Label exerciseCount = new Label(workout.getExercises().size() + " exercises");
            exerciseCount.setStyle("-fx-text-fill: #666;");

            Button startWorkoutButton = new Button("Start Workout");
            startWorkoutButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            startWorkoutButton.setOnAction(e -> startWorkout(workout));

            HBox.setHgrow(workoutName, Priority.ALWAYS);
            workoutRow.getChildren().addAll(workoutName, exerciseCount, startWorkoutButton);

            workoutsPreview.getChildren().add(workoutRow);
        }

        card.getChildren().addAll(header, workoutsPreview);
        return card;
    }

    private void startWorkout(Workout workout) {
        // Create a simple workout display dialog
        Alert workoutDialog = new Alert(Alert.AlertType.INFORMATION);
        workoutDialog.setTitle("Workout: " + workout.getName());
        workoutDialog.setHeaderText("Today's Workout");

        StringBuilder workoutText = new StringBuilder();
        workoutText.append("Exercises:\n\n");

        for (Exercise exercise : workout.getExercises()) {
            workoutText.append("• ").append(exercise.getName())
                    .append(" - ").append(exercise.getSets()).append(" sets of ")
                    .append(exercise.getReps()).append(" reps");

            if (exercise.getWeight() > 0) {
                workoutText.append(" @ ").append(exercise.getWeight()).append("kg");
            }
            workoutText.append("\n");
        }

        workoutText.append("\nGood luck with your workout! 💪");

        workoutDialog.setContentText(workoutText.toString());
        workoutDialog.showAndWait();
    }

    private void deleteProgram(Program program) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete Program");
        confirmDialog.setHeaderText("Are you sure?");
        confirmDialog.setContentText("Do you want to delete the program '" + program.getName() + "'? This cannot be undone.");

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response.getButtonData().isDefaultButton()) {
                fitnessService.removeProgram(program);
                refreshPrograms();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setContentText("Program deleted successfully!");
                successAlert.showAndWait();
            }
        });
    }

}