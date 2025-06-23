package com.antonjj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileManager {
	private static String dirr = "fitness_data";
	private static String programs_file = "programs.json";
	private static String workouts_file = "workouts.json";
	private static String weight_log_file = "weightlog.json";
	
	private ObjectMapper objectMapper;
	private Path path;
	
	
	public FileManager() {
		setUpObjectMapper();
		setupDataDirectory();
		System.out.println(path);
	}


	private void setupDataDirectory() {
		try {

			path = Paths.get(dirr);
			Files.createDirectories(path);
		}catch (IOException e) {
			throw new RuntimeException("failed to crate data directory");
		}
	}


	private void setUpObjectMapper() {
		objectMapper = new ObjectMapper();
		
	}
	
	public void savePrograms(List <Program> programs) {
		Path filePath = path.resolve(program_file);
		if (!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		objectMapper.writeValue(filePath.toFile(),programs);
		
	}
}
