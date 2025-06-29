package models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Program {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("workouts")
	private ArrayList<Workout> workouts;
	
	public Program() {
		this.workouts = new ArrayList<>();
	}
	
	public void addWorkout(Workout workout) {
		this.workouts.add(workout);
	}
	
	public void removeWorkout(Workout workout) {
		this.workouts.remove(workout);
	}
}
	
