package api.common.ict;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class GetRequestBodyICT {

 public static JSONObject getUpdateChairAlarmSettingsRequestBody() throws IOException {
		 
	     String env = System.getProperty("env", "acceptance").toLowerCase();
	        String requestBodyFile;

	       if(env.equals("acceptance")) {
	            requestBodyFile = "src/test/resources/accRequestBodyICT/updateChairAlarmSettingsRequestBody_Acc.json";
	        } else {
	            throw new RuntimeException("Unknown environment: " + env);
	        }

	        String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
	        return new JSONObject(jsonContent);
	    }
 
 public static JSONObject getUpdateBedAlarmSettingsRequestBody() throws IOException {
	 
     String env = System.getProperty("env", "acceptance").toLowerCase();
        String requestBodyFile;

       if(env.equals("acceptance")) {
            requestBodyFile = "src/test/resources/accRequestBodyICT/updateBedAlarmSettingsRequestBody_Acc.json";
        } else {
            throw new RuntimeException("Unknown environment: " + env);
        }

        String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
        return new JSONObject(jsonContent);
    }

 
 public static JSONObject getRegisterForV4StreamingData() throws IOException {
	 
	   String env = System.getProperty("env", "acceptance").toLowerCase();
	   
      String requestBodyFile;

      if(env.equals("acceptance")) {
          requestBodyFile = "src/test/resources/accRequestBodyICT/registerForV4StreamingData.json";
      } else {
          throw new RuntimeException("Unknown environment: " + env);
      }

      String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
      return new JSONObject(jsonContent); 	 	 
}
 
 public static JSONObject getCreateSuspiciousAlarmRequestBody() throws IOException {
	 
	   String env = System.getProperty("env", "acceptance").toLowerCase();
	   
    String requestBodyFile;

    if(env.equals("acceptance")) {
        requestBodyFile = "src/test/resources/accRequestBodyICT/createSuspiciousInactivityAlarmRequestBody_acc.json";
    } else {
        throw new RuntimeException("Unknown environment: " + env);
    }

    String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
    return new JSONObject(jsonContent); 	 	 
}
 public static JSONObject getOutOfBedAlarmRequestBody() throws IOException {
	 
	   String env = System.getProperty("env", "acceptance").toLowerCase();
	   
  String requestBodyFile;

  if(env.equals("acceptance")) {
      requestBodyFile = "src/test/resources/accRequestBodyICT/createOutOfBedAlarmRequestBody.json";
  } else {
      throw new RuntimeException("Unknown environment: " + env);
  }

  String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
  return new JSONObject(jsonContent); 	 	 
}
 public static JSONObject getOutOfChairAlarmRequestBody() throws IOException {
	 
	   String env = System.getProperty("env", "acceptance").toLowerCase();
	   
  String requestBodyFile;

  if(env.equals("acceptance")) {
      requestBodyFile = "src/test/resources/accRequestBodyICT/createOutOfChairAlarmRequestBody.json";
  } else {
      throw new RuntimeException("Unknown environment: " + env);
  }

  String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
  return new JSONObject(jsonContent); 	 	 
}
 
 public static JSONObject getOutOfChairHardwareMeasurementSubmissionRequestBodyForAlarms() throws IOException {
	 
     String env = System.getProperty("env", "acceptance").toLowerCase();
      String requestBodyFile;

       if(env.equals("acceptance")) {
          requestBodyFile = "src/test/resources/accRequestBodyICT/outOfChairHardwareMeasurementsSubmissionReqBody_acc.json";
      } else {
          throw new RuntimeException("Unknown environment: " + env);
      }

      String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
      return new JSONObject(jsonContent);
  }
 public static JSONObject getOutOfBedHardwareMeasurementSubmissionRequestBodyForAlarms() throws IOException {
	 
     String env = System.getProperty("env", "acceptance").toLowerCase();
      String requestBodyFile;

       if(env.equals("acceptance")) {
          requestBodyFile = "src/test/resources/accRequestBodyICT/outOfBedHardwareMeasurementsSubmissionReqBody_acc.json";
      } else {
          throw new RuntimeException("Unknown environment: " + env);
      }

      String jsonContent = new String(Files.readAllBytes(Paths.get(requestBodyFile)));
      return new JSONObject(jsonContent);
  }
}

