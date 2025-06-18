package com.antonjj;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {
	private Stage stage;
	private HashMap<String, Scene> scenes = new HashMap();

	
	public ScreenManager (Stage stage) {
		this.stage = stage;
	}
	
	public void addScreen(String name, Scene scene) {
		this.scenes.put(name, scene);
	}
	
	public void activate(String name) {
		stage.setScene(scenes.get(name));
	}

}
