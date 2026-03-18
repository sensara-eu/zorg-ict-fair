package api.utility.ict;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    // Make static to share across all tests/groups
    private static ExtentReports extent;
    private static ExtentTest test;
    private ExtentSparkReporter sparkReporter;
    private String repName;

    @Override
    public void onStart(ITestContext context) {
        if (extent == null) { 
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            repName = "Extent-Report-" + timeStamp + ".html";

            String reportDir = System.getProperty("user.dir") + File.separator + "reports";
            File folder = new File(reportDir);
            if (!folder.exists()) folder.mkdirs();

            sparkReporter = new ExtentSparkReporter(reportDir + File.separator + repName);
            sparkReporter.config().setDocumentTitle("SensaraRestAssuredAutomationProject");
            sparkReporter.config().setReportName(
                    "<span style='display:block; text-align:center; font-size:22px; font-weight:bold;'>Automated Rest Assured Tests for Sensara API</span>");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "SENSARA API");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Environment", "QA");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
        // assign all groups as categories
        test.assignCategory(result.getMethod().getGroups());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
