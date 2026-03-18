package api.endpoints.ict;

import java.io.InputStream;
import java.util.Properties;

public class RoutesICT {

	public static String base_url;
	public static String getBearerToken_url;
	public static String revokeAccessToken_url;
	
	
	static {
		
        try {
            
          String env = System.getProperty("env", "acceptance").toLowerCase(); 
            String propertiesFile = "env-" + env + ".properties";
            
            Properties properties = new Properties();
            InputStream inputStream = RoutesICT.class.getClassLoader().getResourceAsStream(propertiesFile);
            if (inputStream == null) {
                throw new RuntimeException(propertiesFile + " file not found");
            }
            properties.load(inputStream);
            
            base_url = properties.getProperty(env + "_url");
            getBearerToken_url = properties.getProperty("auth_url");
            revokeAccessToken_url = properties.getProperty("logout_url"); 
            
            if (base_url == null||getBearerToken_url == null || revokeAccessToken_url == null)  {
                throw new RuntimeException("Required URLs not found in properties file for environment:" + env);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load base URL: " + e.getMessage());
        }    
        }
	
	public static String updateAlarmSettings_url = base_url+ "/v3alpha/alarmsettings/{residentId}";
	 public static String createAlarm_url = base_url+ "/v3/alarms/create";
	 public static String hardwareMeasurementSubmission_url = base_url+ "/v3/hardware/measurements";
	 public static String registerForV4StreamingData_url = base_url+ "/v4/streams";
	 public static String receiveSSEForRegistrationToken_url = base_url+ "/v4/streams/{token}/data";
	
}
