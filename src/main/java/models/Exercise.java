package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Exercise {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("sets")
	private int sets;
	
	@JsonProperty("reps")
	private int reps;
	
	@JsonProperty("weight")
	private double weight;
	
	public Exercise(String name, int sets, int reps) {
		this.name = name;
		this.sets = sets;
		this.reps = reps;
		this.weight = 0.0;
	}
}