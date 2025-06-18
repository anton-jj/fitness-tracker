package com.antonjj;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.CreateProgramScreen;
import gui.MainScreen;

public class App extends Application {
	@Override
	public void start(Stage primaryStage) {

		ScreenManager manager = new ScreenManager(primaryStage);
		MainScreen main = new MainScreen(manager);
		CreateProgramScreen createProgram = new CreateProgramScreen(manager);
		
		manager.addScreen("main", main.getScene());
		manager.addScreen("createProgram", createProgram.getScene());
		
		manager.activate("main");
		
		primaryStage.setTitle("fitness tracker");
		primaryStage.show();

	}
	
    public static void main(String[] args) {
        launch();
    }
}