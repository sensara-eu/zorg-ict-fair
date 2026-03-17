package api.common.ict;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class GetRequestBodyICT {

 public static JSONObject getCreateAlarmRequestBody() throws IOException {
		 
	     String env = System.getProperty("env", "acceptance").toLowerCase();
	        String requestBodyFile;

	       if(env.equals("acceptance")) {
	            requestBodyFile = "src/test/resources/accRequestBody/createAlarmRequestBody_Acc.json";
	        } else {
	            throw new RuntimeException("Unknown environment: " + env);
	        }

	        String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
	        return new JSONObject(jsonContent);
	    }
}
