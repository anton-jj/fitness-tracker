package gui;

import java.util.Optional;

import com.antonjj.FitnessService;
import com.antonjj.ScreenManager;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScreen {
	private Scene scene;

	public MainScreen(ScreenManager manager, FitnessService fitnessService) {
		BorderPane root = new BorderPane();
		root.setTop(setupButtons(manager, fitnessService));
		HBox centerElement = new HBox();
		centerElement.getChildren().addAll(setupWeightGraph(), setupStrenghtGraph());

		root.setCenter(centerElement);
		scene = new Scene(root,600,500, Color.BEIGE);

	}
	
	private HBox setupButtons(ScreenManager manager, FitnessService fitnessService) {
		HBox buttonBar = new HBox(10);
		
		Button viewPrograms = new Button("View Programs");
		viewPrograms.setOnAction(e -> manager.activate("programsScreen"));
		Button createProgram = new Button("Create program");
		createProgram.setOnAction(e -> manager.activate("createProgram"));
		Button logWeight = new Button("Log weight");

		logWeight.setOnAction(e -> {
			TextInputDialog weightInput = new TextInputDialog();
			weightInput.setTitle("Weight log");
			weightInput.setContentText("Enter todays weight");
			Optional<String> result = weightInput.showAndWait();
			result.ifPresent(weight -> {
				try {
					double weightD = Double.parseDouble(weight);
					fitnessService.addWeightEntry(weightD);
				} catch(NumberFormatException f) {
					System.out.println("failed to parse to double");
				}
			});
		});

		Button logWorkout = new Button("log workout");
		
		buttonBar.getChildren().addAll(createProgram, logWeight, logWorkout, viewPrograms);
		return buttonBar;
	}
	
	private LineChart setupWeightGraph() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();

		LineChart weightChart = new LineChart(xAxis, yAxis);
		weightChart.setPrefSize(40,40);
		weightChart.setMaxHeight(200);

		return weightChart;

	}

	private LineChart setupStrenghtGraph() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();

		LineChart strenghtChart = new LineChart(xAxis, yAxis);
		strenghtChart.setPrefSize(40,40);
		strenghtChart.setMaxHeight(200);

		return strenghtChart;

	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	
}
