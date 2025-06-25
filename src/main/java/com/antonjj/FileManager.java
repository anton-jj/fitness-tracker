package com.antonjj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Program;
import models.WeightEntry;
import models.Workout;

public class FileManager {
	private static String dirr = "fitness_data";
	private static String programs_file = "programs.json";
	private static String workouts_file = "workouts.json";
	private static String weight_log_file = "weightlog.json";
	private static String settings_file = "settings.json";
	
	private ObjectMapper objectMapper;
	private Path path;
	
	
	public FileManager() {
		setUpObjectMapper();
		setupDataDirectory();
		setUpFiles();
	}

	private void setupDataDirectory() {
		try {

			path = Paths.get(dirr);
			Files.createDirectories(path);
		}catch (IOException e) {
			throw new RuntimeException("failed to crate data directory");
		}
	}

	private void setUpFiles() {
		String[] files = {programs_file, workouts_file, weight_log_file, settings_file };
		for(int i = 0; i < files.length; i++) {
			Path filePath = path.resolve(files[i]);
			if(!Files.exists(filePath)) {
				try {
					Files.createFile(filePath);
					Files.write(filePath,"[]".getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setUpObjectMapper() {
		objectMapper = new ObjectMapper();
		
	}
	
	public void savePrograms(List <Program> programs) throws StreamWriteException, DatabindException, IOException {
		Path filePath = path.resolve(programs_file);
		objectMapper.writeValue(filePath.toFile(),programs);
	}
	
	public ArrayList<Program> loadProgram() throws StreamReadException, DatabindException, IOException {
		Path filePath = path.resolve(programs_file);
		if(!Files.exists(filePath)) {
			return new ArrayList<>();
		}
		Program[] programs = objectMapper.readValue(filePath.toFile(), Program[].class);
		return new ArrayList<>(Arrays.asList(programs));
	}
	
	public void saveWorkout(List<Workout> workouts) throws StreamWriteException, DatabindException, IOException {
		Path filePath = path.resolve(workouts_file);
		objectMapper.writeValue(filePath.toFile(), workouts);
	}
	
	public ArrayList<Workout> loadWorkouts() throws StreamReadException, DatabindException, IOException {
		Path filePath = path.resolve(workouts_file);
		if(!Files.exists(filePath)) {
			return new ArrayList<>();
		}
		Workout[] workouts = objectMapper.readValue(filePath.toFile(), Workout[].class);
		return new ArrayList<>(Arrays.asList(workouts));
	}
	
	public void saveWheightEntry(List<WeightEntry> weightLog) throws StreamWriteException, DatabindException, IOException {
		Path filePath = path.resolve(weight_log_file);
		objectMapper.writeValue(filePath.toFile(), weightLog);
		
	}
	
	public ArrayList<WeightEntry> loadWeightLog() throws StreamReadException, DatabindException, IOException {
		Path filePath = path.resolve(weight_log_file);

		if(!Files.exists(filePath)) {
			return new ArrayList<>();
		}

		WeightEntry[] weightLog = objectMapper.readValue(filePath.toFile(), WeightEntry[].class);
		return new ArrayList<>(Arrays.asList(weightLog));
	}

	public void saveSettings(Map<String, Object> settings) throws IOException {
        Path filePath = path.resolve(settings_file);
        objectMapper.writeValue(filePath.toFile(), settings);
    }
    
    public Map<String, Object> loadSettings() throws IOException {
        Path filePath = path.resolve(settings_file);
        if (!Files.exists(filePath)) {
            return new HashMap<>();
        }
        
        @SuppressWarnings("unchecked")
        Map<String, Object> settings = objectMapper.readValue(filePath.toFile(), Map.class);
        return settings;
    }

}

