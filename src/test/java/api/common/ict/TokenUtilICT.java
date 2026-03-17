package api.common.ict;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import api.endpoints.ict.RoutesICT;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TokenUtilICT {
	
	private static String accessToken;
	
	 private static JSONObject getCredentials() throws IOException {
		 
		String env = System.getProperty("env", "acceptance").toLowerCase();
	
		 String credentialsFile;
           if(env.equals("acceptance")) {
	            credentialsFile = "testData/accessTokenACCData.json";
	        } else {
	            throw new RuntimeException("Unknown environment: " + env);
	        }
	        
	        String jsonContent = new String(Files.readAllBytes(Paths.get(credentialsFile)));
	        return new JSONObject(jsonContent);
	    }
		
	public static String getAccessToken() throws IOException {
     
	   if (accessToken == null) {
		   JSONObject creds = getCredentials();
		   
       Response response = given()
               .contentType("application/x-www-form-urlencoded")
               .formParam("grant_type", "password")
               .formParam("username", creds.getString("username"))
               .formParam("password", creds.getString("password"))
               .formParam("client_id",creds.getString("client_id"))
               .when()
               .post(RoutesICT.getBearerToken_url)
               .then()
               .statusCode(200)
               .extract()
               .response();

       accessToken = response.path("access_token");
       System.out.println("Access Token: " + accessToken);    
   }
			 return accessToken;
		}
		
		
	 public static void revokeAccessToken() { 
		 if (accessToken != null) { 
			                     given()
		                        .header("Authorization", "Bearer " + accessToken) .when()
		                        .post(RoutesICT.revokeAccessToken_url) .then() .statusCode(200);
		  
		  System.out.println("Token revoked: " + accessToken); 
		  accessToken = null; 
		  
		  } 
		 }

}
