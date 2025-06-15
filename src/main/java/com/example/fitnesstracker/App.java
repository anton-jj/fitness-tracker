package com.example.fitnesstracker;

import com.example.fitnesstracker.gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
       MainWindow mainWindow = new MainWindow();
       mainWindow.start(primaryStage);
    }
    public static void main(String[] args) {
        launch();
    }
}