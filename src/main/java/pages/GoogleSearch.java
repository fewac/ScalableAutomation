package pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import utilities.CommonFunctions;
import java.util.List;

public class GoogleSearch {
    ExtentTest test;
    WebDriver driver = null;
    CommonFunctions cf;
    String baseUrl = "https://teechip.com/defaulttest";
    By searchBar = By.name("q");
    String shirtXpath = "//img[@alt='Default Test Classic T-Shirt tile']";
    By classicTshirt = By.xpath("//img[@alt='Default Test Classic T-Shirt tile']");
    By shirtColor = By.xpath("//div[@style='background-color:#2B63BA;']");
    By shirtSize = By.xpath("//div[@class='br ta-center fs-md cursor-pointer py-p75 bc-grey-400 bw-1' and text()='L']");



    public GoogleSearch(WebDriver driver, ExtentTest test){
        this.driver = driver;
        this.test = test;
        cf = new CommonFunctions(driver, test);
    }


    public void openTeechipPage() throws  Exception{
        try{
            driver.get(baseUrl);
            driver.manage().window().maximize();
            test.log(LogStatus.PASS, "Google landing page has been opened successfully");
        }catch(Exception e){
            System.out.println("Error while trying to open Google web page. Error: " + e.getMessage());
        }
    }

    public void selectClassicTShirt(){
        try{
//            WebElement shirt = driver.findElement(By.xpath("shirtXpath"));
//            shirt.click();
            cf.clickElement(classicTshirt,"classic t-shirt");
            test.log(LogStatus.PASS, "Successfully clicked in classic t-shirt");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void selectShirtColor(){
        try{
            List<WebElement> listColorShirt = driver.findElements(By.xpath("//div[@style='background-color:#0A2245;']"));
            WebElement correctElement = listColorShirt.get(1);
            cf.hardDelay(2000);
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeAsyncScript("arguments[0].click();",correctElement);
//            listColorShirt.get(0).sendKeys(Keys.ENTER);
//            listColorShirt.get(0).sendKeys(Keys.RETURN);
//            listColorShirt.get(1).click();
//            System.out.println(listColorShirt.get(1));
//            correctElement.click();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void selectShirtSize(){
        try{
            cf.waitForElement(shirtSize,"Shirt size");
            cf.clickElement(shirtSize,"Shirt size");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void checkIFSearchBarIsThere(){
        try{
            cf.waitForElement(searchBar,"Search bar field");
            test.log(LogStatus.PASS, "Search bar field is appearing");
        }catch(Exception e){
            System.out.println("The search bar field is not appearing. Error: " + e.getMessage());
        }
    }

    public void thisIsAtest(){
        try{
            test.log(LogStatus.PASS, "this is a test");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
