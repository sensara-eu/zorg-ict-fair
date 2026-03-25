package api.tests.ict;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import api.common.ict.GetRequestBodyICT;
import api.common.ict.TokenUtilICT;
import api.endpoints.ict.RoutesICT;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@Test(groups = "alarms")
public class OutOfChairAlarmVideoGeneration {

    private static String accessToken;
    private static Logger logger;

    // --- Shared setup for both TestNG and orchestrator ---
    public static void setUp() throws IOException {
        accessToken = TokenUtilICT.getAccessToken();
        RestAssured.baseURI = RoutesICT.base_url;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        System.setProperty("io.restassured.json.schema.validation.report.format", "detailed");
        logger = LogManager.getLogger(OutOfChairAlarmGeneration.class);
    }

    public static void tearDown() {
        TokenUtilICT.revokeAccessToken();
    }
    
    // --- For orchestrator ---
    public static void trigger() throws IOException, InterruptedException {
        setUp();
        Thread.sleep(1000); 
        sendAlarm();
        tearDown();
    }

    public static void sendAlarm() throws IOException, InterruptedException {
        JSONObject requestBody = GetRequestBodyICT.getOutOfChairAlarmRequestBody();

        given()
            .header("Authorization", "Bearer " + accessToken)
            .header("Accept", "application/json")
            .contentType(ContentType.JSON)
            .body(requestBody.toString())
        .when()
            .post(RoutesICT.createAnAlarm_url)
        .then()
            .statusCode(200);

        System.out.println("✅ Out Of Chair Alarm triggered!");
    }

    @BeforeTest
    public void beforeTest() throws IOException {
        setUp();
    }

    @Test
    public void testGenerateOutOfChairAlarm() throws IOException, InterruptedException {
        sendAlarm();
    }

    @AfterTest
    public void afterTest() {
        tearDown();
    }
}