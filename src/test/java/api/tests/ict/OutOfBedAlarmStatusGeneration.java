package api.tests.ict;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import api.common.ict.GetRequestBodyICT;
import api.common.ict.GetTestDataICT;
import api.common.ict.TokenUtilICT;
import api.endpoints.ict.RoutesICT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Test(groups = "alarms")
public class OutOfBedAlarmStatusGeneration {

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
   public void testUpdateAlarmSettings() throws IOException, InterruptedException {
	   
	   Thread.sleep(20000);
	   
	   JSONObject requestBody = GetRequestBodyICT.getUpdateBedAlarmSettingsRequestBody();
	   String residentId = GetTestDataICT.getCommonValue("residentId");
	   
	   Response response = given()
	           .header("Authorization", "Bearer " + accessToken)
               .header("Accept", "application/json")
               .pathParam("residentId",residentId )
               .contentType(ContentType.JSON) 
               .body(requestBody.toString()) .when()
               .put(RoutesICT.updateAlarmSettings_url) 
               .then() .statusCode(200)
               .extract().response();

    System.out.println("Response Body:\n" + response.asPrettyString());
	   
}
   
   @Test(dataProvider = "OutOfBedAlarm",
		    dataProviderClass = GetTestDataICT.class, priority=2)
  public void testHardwareMeasurementSubmission(JSONObject data) throws IOException, InterruptedException {
	   
	   JSONObject requestBody = GetRequestBodyICT.getHardwareMeasurementSubmissionRequestBodyForAlarms();
	    
	   String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam"))
		        .truncatedTo(ChronoUnit.SECONDS)
		        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);


	   requestBody.put("timestamp", timestamp);
	   System.out.println("timestamp created: " + timestamp);
	   
	   requestBody.getJSONObject("gateway").put("id", data.get("gatewayId"));

	   requestBody.getJSONObject("device") .put("id", data.get("deviceId"));
	   
	   requestBody.getJSONArray("measurements").getJSONObject(0).put("value", data.get("value")); 
	   
	         given()
			   .log().all()
   		   .header("Authorization", "Bearer " + accessToken)
  		       .contentType(ContentType.JSON)
  		       .body(requestBody.toString())
   		   .when()
              .post(RoutesICT.hardwareMeasurementSubmission_url)
              .then()
              .statusCode(202)
              .extract().response();
	         Thread.sleep(10000);
	        
  }
   
   @Test(priority=3)
   public void testRegisterForV4StreamingData() throws IOException, InterruptedException {
	   
	   JSONObject requestBody = GetRequestBodyICT.getRegisterForV4StreamingData();
	  
	   Response response = given()
				.header("Authorization", "Bearer " + accessToken)
				.header("Accept", "text/plain")
	            .contentType(ContentType.JSON)  
			    .body(requestBody.toString())
			.when()
			    .post(RoutesICT.registerForV4StreamingData_url)
			.then()
			    .statusCode(201)
			    .extract()
	            .response();
	   
	   System.out.println("Response Body:\n" + response.asPrettyString());
	   token = response.asString().trim();
	   System.out.println("Token extracted: " + token);
	   
	   Thread.sleep(10000);
	 
   }
   
   
   
   @Test(dataProvider = "OutOfBedAlarmFalse",
		    dataProviderClass = GetTestDataICT.class, priority=4)
 public void testHardwareMeasurementSubmissionFrontDoor(JSONObject data) throws IOException, InterruptedException {
	   
	   JSONObject requestBody = GetRequestBodyICT.getHardwareMeasurementSubmissionRequestBodyForAlarms();
	    
	   String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam"))
		        .truncatedTo(ChronoUnit.SECONDS)
		        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

	   
	   requestBody.put("timestamp", timestamp);
	   System.out.println("timestamp created: " + timestamp);
	   
	   requestBody.getJSONObject("gateway").put("id", data.get("gatewayId"));

	   requestBody.getJSONObject("device") .put("id", data.get("deviceId"));
	   
	   requestBody.getJSONArray("measurements").getJSONObject(0).put("value", data.get("value")); 
	   
	         given()
			   .log().all()
  		   .header("Authorization", "Bearer " + accessToken)
 		       .contentType(ContentType.JSON)
 		       .body(requestBody.toString())
  		   .when()
             .post(RoutesICT.hardwareMeasurementSubmission_url)
             .then()
             .statusCode(202)
             .extract().response();
	         
 }
   
	/*
	 * @Test(priority = 5, timeOut = 100000) public void testSSEConnection() throws
	 * Exception {
	 * 
	 * HttpClient client = HttpClient.newHttpClient();
	 * 
	 * String url = RoutesICT.receiveSSEForRegistrationToken_url .replace("{token}",
	 * token); System.out.println(url);
	 * 
	 * HttpRequest request = HttpRequest.newBuilder() .uri(URI.create(url))
	 * .header("Authorization", "Bearer " + accessToken) .header("Accept",
	 * "text/event-stream") .GET() .build();
	 * 
	 * client.send(request, HttpResponse.BodyHandlers.ofLines()) .body()
	 * .takeWhile(line -> { System.out.println("SSE >> " + line); return
	 * !line.contains("OUT_OF_BED"); }) .forEach(l -> {});
	 * 
	 * }
	 */

   
   @AfterTest 
 	 public void tearDown() { 
 		 TokenUtilICT.revokeAccessToken();
 	  }
 }

