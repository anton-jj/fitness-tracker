package models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Program {
	@Getter @Setter private String name;
	@Getter @Setter private ArrayList<Workout> workouts;
	
	public Program() {
		this.exercises = new ArrayList<>();
	}
	
	public void addExercise(Exercise exercise) {
		this.exercises.add(exercise);
	}
	
	public void removeExercise(Exercise exercise) {
		this.exercises.remove(exercise);
	}
	
}
