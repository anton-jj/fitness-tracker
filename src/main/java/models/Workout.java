package models;

import java.util.ArrayList;


import lombok.Getter;
import lombok.Setter;

public class Workout {
	@Getter @Setter private String name;
	@Getter @Setter private ArrayList<Exercise> exercises;
	
	public Workout () {
		this.exercises = new ArrayList<>();
	}
}
