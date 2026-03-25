package api.tests.ict;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import api.common.ict.GetRequestBodyICT;
import api.common.ict.TokenUtilICT;
import api.endpoints.ict.RoutesICT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Test(groups = "alarms")
public class FallDetectionSuspiciousInactivityAlarm {

	private String accessToken;
	public String token;
	public Logger logger;

   @BeforeTest
    public void setUp() throws IOException {
        accessToken = TokenUtilICT.getAccessToken();
        RestAssured.baseURI = RoutesICT.base_url;
        
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        System.setProperty("io.restassured.json.schema.validation.report.format", "detailed");
        
        logger = LogManager.getLogger(this.getClass());
    }
   
   @Test(priority=1)
   public void testGenerateSuspiciousInactivityAlarm() throws IOException, InterruptedException {
	   
	   Thread.sleep(10000);
	   
	   JSONObject requestBody = GetRequestBodyICT.getCreateSuspiciousAlarmRequestBody();
	   
	           given()
	           .header("Authorization", "Bearer " + accessToken)
               .header("Accept", "application/json")
               .contentType(ContentType.JSON) 
               .body(requestBody.toString()) .when()
               .post(RoutesICT.createAnAlarm_url) 
               .then() .statusCode(200)
               .extract().response();

	   System.out.println("Fall Detection Alarm generated successfully" );
	     
   }
   
   @AfterTest 
	 public void tearDown() { 
		 TokenUtilICT.revokeAccessToken();
	  }
}
