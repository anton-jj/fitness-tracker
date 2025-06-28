package models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Program {
	private String name;
	private ArrayList<Workout> workouts;
	
	public Program() {
		this.workouts = new ArrayList<>();
	}
	
	public void addExercise(Workout workout) {
		this.workouts.add(workout);
	}
	
	public void removeExercise(Workout workout) {
		this.workouts.remove(workout);
	}

	public void setName(String programName) {
		this.name = programName;
	}

	public void addWorkout(Workout workout) {
		workouts.add(workout);
		
	}
	
}
