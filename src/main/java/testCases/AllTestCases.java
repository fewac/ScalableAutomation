package testCases;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.GoogleSearch;
import utilities.CommonFunctions;
import utilities.ExtentFactory;
import utilities.Screenshots;

import java.io.IOException;

public class AllTestCases {
    public WebDriver driver;
    private String baseUrl;
    ExtentReports report;
    ExtentTest test;
    GoogleSearch gs;
    CommonFunctions cf;
    ExtentFactory ef;

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
        driver = new FirefoxDriver();
//		driver = new ChromeDriver();
        report = ExtentFactory.getInstance();
        test = report.startTest("Verify Welcome Text");
        gs = new GoogleSearch(driver, test);
        cf = new CommonFunctions(driver, test);
        ef = new ExtentFactory();
    }

    @Test
    public void searchSomethingInGoogle(){
        try{
            gs.openTeechipPage();
            gs.selectClassicTShirt();
            gs.selectShirtColor();
            gs.selectShirtSize();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    @AfterMethod
    public void tearDown(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            System.out.println(testResult.getName());
            String path = Screenshots.takeScreenshot(driver, testResult.getName());
            String imagePath = test.addScreenCapture(path);
            test.log(LogStatus.FAIL, "Verify Welcome Text Failed", imagePath);
        }

    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
//        report.endTest(test);
//        report.flush();
    }



}
