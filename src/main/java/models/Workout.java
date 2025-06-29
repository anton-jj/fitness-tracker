package models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class Workout {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("exercises")
	private ArrayList<Exercise> exercises;
	
	public Workout() {
		this.exercises = new ArrayList<>();
	}
	
	public void addExercise(Exercise exercise) {
		this.exercises.add(exercise);
	}
	
	public void removeExercise(Exercise exercise) {
		this.exercises.remove(exercise);
	}
}
