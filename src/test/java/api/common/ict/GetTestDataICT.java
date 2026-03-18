package api.common.ict;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

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
	
	public static JSONObject getCommonData() throws IOException {
        JSONObject root = getTestData();
        return root.has("commonData") ? root.getJSONObject("commonData") : root;
    }

    public static String getCommonValue(String key) throws IOException {
        return getCommonData().getString(key);
    }
    
    @DataProvider(name = "OutOfChairAlarm")
    public static Object[][] OutOfChairAlarmDataProvider() throws IOException {

       String env = System.getProperty("env", "acceptance").toLowerCase();
 
        String testDataFile;

        if (env.equals("acceptance")) {
            testDataFile = "testDataICT/ACCDataICT.json";
        } else {
            throw new RuntimeException("Unknown environment: " + env);
        }

        String jsonContent = new String(Files.readAllBytes(Paths.get(testDataFile)));
        JSONObject root = new JSONObject(jsonContent);

        if (!root.has("OutOfChairAlarm")) {
            throw new RuntimeException(
                "'OutOfChairAlarmData' not found in " + testDataFile
            );
        }

        JSONArray sensorArray = root.getJSONArray("OutOfChairAlarm");
        Object[][] data = new Object[sensorArray.length()][1];

        for (int i = 0; i < sensorArray.length(); i++) {
            data[i][0] = sensorArray.getJSONObject(i);
        }

        return data;
    }
    
    @DataProvider(name = "OutOfChairAlarmFalse")
    public static Object[][] OutOfChairAlarmFalseDataProvider() throws IOException {

        String env = System.getProperty("env", "acceptance").toLowerCase();
      
        String testDataFile;

        if (env.equals("acceptance")) {
            testDataFile = "testDataICT/ACCDataICT.json";
        } else {
            throw new RuntimeException("Unknown environment: " + env);
        }

        String jsonContent = new String(Files.readAllBytes(Paths.get(testDataFile)));
        JSONObject root = new JSONObject(jsonContent);

        if (!root.has("OutOfChairAlarmFalse")) {
            throw new RuntimeException(
                "'OutOfChairAlarmFalse' not found in " + testDataFile
            );
        }

        JSONArray sensorArray = root.getJSONArray("OutOfChairAlarmFalse");
        Object[][] data = new Object[sensorArray.length()][1];

        for (int i = 0; i < sensorArray.length(); i++) {
            data[i][0] = sensorArray.getJSONObject(i);
        }

        return data;
    }
    
    @DataProvider(name = "OutOfBedAlarm")
    public static Object[][] OutOfBedAlarmDataProvider() throws IOException {

       String env = System.getProperty("env", "acceptance").toLowerCase();
 
        String testDataFile;

        if (env.equals("acceptance")) {
            testDataFile = "testDataICT/ACCDataICT.json";
        } else {
            throw new RuntimeException("Unknown environment: " + env);
        }

        String jsonContent = new String(Files.readAllBytes(Paths.get(testDataFile)));
        JSONObject root = new JSONObject(jsonContent);

        if (!root.has("OutOfBedAlarm")) {
            throw new RuntimeException(
                "'OutOfBedAlarmData' not found in " + testDataFile
            );
        }

        JSONArray sensorArray = root.getJSONArray("OutOfBedAlarm");
        Object[][] data = new Object[sensorArray.length()][1];

        for (int i = 0; i < sensorArray.length(); i++) {
            data[i][0] = sensorArray.getJSONObject(i);
        }

        return data;
    }
    
    @DataProvider(name = "OutOfBedAlarmFalse")
    public static Object[][] OutOfBedAlarmFalseDataProvider() throws IOException {

        String env = System.getProperty("env", "acceptance").toLowerCase();
      
        String testDataFile;

        if (env.equals("acceptance")) {
            testDataFile = "testDataICT/ACCDataICT.json";
        } else {
            throw new RuntimeException("Unknown environment: " + env);
        }

        String jsonContent = new String(Files.readAllBytes(Paths.get(testDataFile)));
        JSONObject root = new JSONObject(jsonContent);

        if (!root.has("OutOfBedAlarmFalse")) {
            throw new RuntimeException(
                "'OutOfBedAlarmFalse' not found in " + testDataFile
            );
        }

        JSONArray sensorArray = root.getJSONArray("OutOfBedAlarmFalse");
        Object[][] data = new Object[sensorArray.length()][1];

        for (int i = 0; i < sensorArray.length(); i++) {
            data[i][0] = sensorArray.getJSONObject(i);
        }

        return data;
    }


}
