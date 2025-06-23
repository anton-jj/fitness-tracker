package models;

import lombok.Getter;
import lombok.Setter;

public class Exercise {

	@Getter @Setter private String name;
	@Getter @Setter private int sets;
	@Getter @Setter private int reps;
	@Getter @Setter private double weight;
	
	public Exercise(String name, int sets, int reps) {
		this.name = name;
		this.sets = sets;
		this.reps = reps;
		this.weight = 0.0;
	}
}
