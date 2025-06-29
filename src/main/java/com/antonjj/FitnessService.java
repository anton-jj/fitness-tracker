package com.antonjj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Program;
import models.WeightEntry;
import models.Workout;

public class FitnessService {
	private FileManager fileManager;
	private List<Program> programs;
	private List<Workout> workouts;
	private List<WeightEntry> weightLog;
	private Map<String, Object> settings;
	
	
	public FitnessService(){
		this.fileManager = new FileManager();
		loadAllData();
	}
	
	private void loadAllData() {
		try {
			this.programs = fileManager.loadProgram();
			this.workouts = fileManager.loadWorkouts();
			this.weightLog = fileManager.loadWeightLog();
			this.settings = fileManager.loadSettings();
		} catch (IOException e) {
			this.programs = new ArrayList<>();
			this.workouts = new ArrayList<>();
			this.weightLog = new ArrayList<>();
			this.settings = new HashMap<>();
			e.printStackTrace();
		}
	}
	
	public void addWeightEntry(Double weight) {
		if (weight <= 0) {
			System.out.println("most be positive");
			return;
		}
		

		WeightEntry today = new WeightEntry(new Date(), weight);
		weightLog.add(today);
		try {
			fileManager.saveWheightEntry(weightLog);
		} catch(IOException e) {
			System.out.println("failed to save");
	        e.printStackTrace();
			weightLog.remove(today);

		}

	}
		 public List<WeightEntry> getWeightHistory() {
		        return new ArrayList<>(weightLog);
		    }

		 public void addProgram(Program program) {
			 this.programs.add(program);
			 try {
				fileManager.savePrograms(programs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		 }

		 public String getCurrentProgram() {
			// TODO Auto-generated method stub
			return null;
		 }

		 public void removeProgram(Program program) {
		if(programs.remove(program)){
			try {
				fileManager.savePrograms(programs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		 }

		 public void setCurrentProgram(Object name) {

		 }

	public List<Program> getAllPrograms() {
		return programs;
	}

	public List<Program> getPrograms() {
		return new ArrayList<>(programs);
	}

	public void addWorkout(Workout workout) {
		if (workout == null || workout.getName() == null || workout.getName().trim().isEmpty()) {
			System.out.println("invalid workout");
			return;
		}
		workouts.add(workout);

		try {
			fileManager.saveWorkout(workouts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveWorkout(Workout workout) {

	}
}
