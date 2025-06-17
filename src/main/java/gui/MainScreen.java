package gui;

import java.util.Optional;

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

	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setTop(setupButtons());
		
		HBox centerElement = new HBox();
		centerElement.getChildren().addAll(setupWeightGraph(), setupStrenghtGraph());

		root.setCenter(centerElement);
		Scene scene = new Scene(root,600,500, Color.BEIGE);
		
		
		primaryStage.setTitle("fitness-tracker");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	private HBox setupButtons() {
		HBox buttonBar = new HBox(10);
		
		Button createProgram = new Button("create program");
		Button logWeight = new Button("log weight");
		logWeight.setOnAction(e -> {
			
			TextInputDialog weightInput = new TextInputDialog();
			weightInput.setTitle("Weight log");
			weightInput.setContentText("Eneter todays weight");
			Optional<String> result = weightInput.showAndWait();
			result.ifPresent(weight -> {
				try {
					double weightD = Double.parseDouble(weight);
					System.out.println(weight);
				} catch(NumberFormatException f) {
					System.out.println("failed to parse to double");
				}
			});
		});
		Button logWorkout = new Button("log workout");
		
		buttonBar.getChildren().addAll(createProgram, logWeight, logWorkout);
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
	
	
}
