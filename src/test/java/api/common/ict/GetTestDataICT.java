package api.common.ict;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class GetTestDataICT {
	public static JSONObject getTestData() throws IOException {
		
		String env = System.getProperty("env", "acceptance").toLowerCase();
	    String testDataFile;

	     if(env.equals("acceptance")) {
	        testDataFile = "testDataICT/ACCDataICT.json";
	    } else {
	        throw new RuntimeException("Unknown environment: " + env);
	    }

	    String jsonContent = new String(Files.readAllBytes(Paths.get(testDataFile)));
	    return new JSONObject(jsonContent);
	}
	
	public static Map<String, Object> getExpectedData() throws IOException {
		
		String env = System.getProperty("env", "acceptance").toLowerCase();
	    String expectedFilePath;

	   if(env.equals("acceptance")) {
	        expectedFilePath = "testDataICT/expectedTestDataICT.json";
	    } else {
	        throw new RuntimeException("Unknown environment: " + env);
	    }

	    ObjectMapper objectMapper = new ObjectMapper();
	    File expectedFile = new File(expectedFilePath);
	    return objectMapper.readValue(expectedFile, new TypeReference<Map<String, Object>>() {});
	}

}
