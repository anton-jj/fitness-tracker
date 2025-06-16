package com.antonjj;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.MainScreen;

public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
	MainScreen main = new MainScreen();
	main.start(primaryStage);
	}
	
    public static void main(String[] args) {
        launch();
    }
}