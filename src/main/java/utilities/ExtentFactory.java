package utilities;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {

    public static ExtentReports getInstance() {
        ExtentReports extent;
        String Path = System.getProperty("user.dir") + "\\ExtentReports\\testResults.html";
        extent = new ExtentReports(Path, false);
        extent
                .addSystemInfo("Selenium Version", "2.52")
                .addSystemInfo("Platform", "Windows");

        return extent;
    }

}
