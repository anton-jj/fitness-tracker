package models;


public class Exercise {

	 private String name;
	 private int sets;
	 private int reps;
	 private double weight;
	
	public Exercise(String name, int sets, int reps) {
		this.name = name;
		this.sets = sets;
		this.reps = reps;
		this.weight = 0.0;
	}

	public void setWeight(double weight2) {
		this.weight = weight2;
	}
}
