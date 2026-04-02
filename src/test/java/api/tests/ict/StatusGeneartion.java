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
import api.common.ict.TokenUtilICT;
import api.endpoints.ict.RoutesICT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@Test(groups = "alarms")
public class StatusGeneartion {

	private static String accessToken;
	public static String token;
	public static Logger logger;

	public static void setUp() throws IOException {
		accessToken = TokenUtilICT.getAccessToken();
		RestAssured.baseURI = RoutesICT.base_url;

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		System.setProperty("io.restassured.json.schema.validation.report.format", "detailed");

		logger = LogManager.getLogger(StatusGeneartion.class);
	}

	// --- Orchestrator Entry Point ---
	public static void trigger() throws IOException, InterruptedException {
		setUp();

		//testUpdateAlarmSettings();
		testHardwareMeasurementSubmissionForOutOfBedOutOfChair();
		//testSSEConnection();

		tearDown();
	}


	public static void testHardwareMeasurementSubmissionForOutOfBedOutOfChair() throws IOException, InterruptedException {
		
		

			JSONObject requestBody = GetRequestBodyICT.getOutOfChairHardwareMeasurementSubmissionRequestBodyForAlarms();
			   String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam")).truncatedTo(ChronoUnit.SECONDS)
					.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

			   requestBody.put("timestamp", timestamp);
			   requestBody.getJSONObject("gateway").put("id","4a62b3fc-dbf1-4827-a9a7-8e1b956948d2");
			   requestBody.getJSONObject("device").put("id", "VIRTUAL-6g0YESEEN5Metg8LYsOYqECih2uY");
			   requestBody.getJSONArray("measurements").getJSONObject(0).put("value", true);

			   given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON)
					.body(requestBody.toString()).when().post(RoutesICT.hardwareMeasurementSubmission_url).then()
					.statusCode(202);

			   System.out.println("✅ Resident state changed from In room to In chair");
			   Thread.sleep(18000);
		       
	

			JSONObject requestBody1 = GetRequestBodyICT.getOutOfChairHardwareMeasurementSubmissionRequestBodyForAlarms();
               String timestamp1 = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam")).truncatedTo(ChronoUnit.SECONDS)
					.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

			   requestBody1.put("timestamp", timestamp1);
			   requestBody1.getJSONObject("gateway").put("id", "4a62b3fc-dbf1-4827-a9a7-8e1b956948d2");
			   requestBody1.getJSONObject("device").put("id", "VIRTUAL-6g0YESEEN5Metg8LYsOYqECih2uY");
			   requestBody1.getJSONArray("measurements").getJSONObject(0).put("value",  false);

			   given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON)
					.body(requestBody1.toString()).when().post(RoutesICT.hardwareMeasurementSubmission_url).then()
					.statusCode(202);
			   
			   System.out.println("✅ Resident state changed from In chair to In room");
			   Thread.sleep(25000);
			
			  
		
		

			JSONObject requestBody2 = GetRequestBodyICT.getOutOfBedHardwareMeasurementSubmissionRequestBodyForAlarms();
			    String timestamp2 = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam")).truncatedTo(ChronoUnit.SECONDS)
					.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			   requestBody2.put("timestamp", timestamp2);
			   System.out.println("timestamp created: " + timestamp2);
			   
			   requestBody2.getJSONObject("gateway").put("id", "4a62b3fc-dbf1-4827-a9a7-8e1b956948d2");
			   requestBody2.getJSONObject("device") .put("id", "VIRTUAL-CdBLMcqniPePmxJeKzXQw9y8yFtL");
			   requestBody2.getJSONArray("measurements").getJSONObject(0).put("value", true); 
			   
			    given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON) .body(requestBody2.toString())
			            .when() .post(RoutesICT.hardwareMeasurementSubmission_url).then().statusCode(202).extract().response();
			    System.out.println("✅ Resident state changed from In room to In bed");
			         
			    Thread.sleep(25000);		
		
			    

			JSONObject requestBody3 = GetRequestBodyICT.getOutOfBedHardwareMeasurementSubmissionRequestBodyForAlarms();
			    String timestamp3 = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam")).truncatedTo(ChronoUnit.SECONDS)
					.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			
			    requestBody3.put("timestamp", timestamp3);
			    System.out.println("timestamp created: " + timestamp3);
			   
			    requestBody3.getJSONObject("gateway").put("id", "4a62b3fc-dbf1-4827-a9a7-8e1b956948d2");
			    requestBody3.getJSONObject("device") .put("id","VIRTUAL-CdBLMcqniPePmxJeKzXQw9y8yFtL");		   
			    requestBody3.getJSONArray("measurements").getJSONObject(0).put("value",false); 
			   
			    given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).body(requestBody3.toString())
		  		   .when().post(RoutesICT.hardwareMeasurementSubmission_url).then() .statusCode(202).extract().response();
			    
			    System.out.println("✅ Resident state changed from In bed to In room");
			    Thread.sleep(8000);
			         
			   
			         
			    
			 JSONObject requestBody4 = GetRequestBodyICT.getOutOfChairHardwareMeasurementSubmissionRequestBodyForAlarms();
			     String timestamp4 = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam")).truncatedTo(ChronoUnit.SECONDS)
								.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

				 requestBody4.put("timestamp", timestamp4);
				 requestBody4.getJSONObject("gateway").put("id","4a62b3fc-dbf1-4827-a9a7-8e1b956948d2");
				 requestBody4.getJSONObject("device").put("id", "VIRTUAL-6g0YESEEN5Metg8LYsOYqECih2uY");
				 requestBody4.getJSONArray("measurements").getJSONObject(0).put("value", true);

				given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON)
						.body(requestBody.toString()).when().post(RoutesICT.hardwareMeasurementSubmission_url).then().statusCode(202);

				System.out.println("✅ Resident state changed from In room to In chair");
				Thread.sleep(5000);
								

			JSONObject requestBody5 = GetRequestBodyICT.getOutOfChairHardwareMeasurementSubmissionRequestBodyForAlarms();

				String timestamp5 = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam")).truncatedTo(ChronoUnit.SECONDS)
								.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

				requestBody5.put("timestamp", timestamp5);

				requestBody5.getJSONObject("gateway").put("id", "4a62b3fc-dbf1-4827-a9a7-8e1b956948d2");
				requestBody5.getJSONObject("device").put("id", "VIRTUAL-6g0YESEEN5Metg8LYsOYqECih2uY");
				requestBody5.getJSONArray("measurements").getJSONObject(0).put("value",  false);

				given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON)
								.body(requestBody1.toString()).when().post(RoutesICT.hardwareMeasurementSubmission_url).then().statusCode(202);
				System.out.println("✅ Resident state changed from In chair to In room");
				Thread.sleep(20000);
						
			    
					

		}
		
	

	public static void tearDown() {
		TokenUtilICT.revokeAccessToken();
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		setUp();
	}

	@Test
	public void testGenerateOutOfBedAlarm() throws IOException, InterruptedException {
		testHardwareMeasurementSubmissionForOutOfBedOutOfChair();
	}

	@AfterTest
	public void afterTest() {
		tearDown();
	}
}