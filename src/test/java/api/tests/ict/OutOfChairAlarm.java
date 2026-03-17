package api.tests.ict;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import api.common.ict.TokenUtilICT;
import api.endpoints.ict.RoutesICT;
import io.restassured.RestAssured;

@Test(groups = "alarms")
public class OutOfChairAlarm {

	private String accessToken;
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
   public void testCreateAlarms() throws IOException {
	   
}
   
   @AfterTest 
 	 public void tearDown() { 
 		 TokenUtilICT.revokeAccessToken();
 	  }
 }
