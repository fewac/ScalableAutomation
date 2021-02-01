package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.awt.AWTException;
import java.io.*;
import java.util.*;


public class CommonFunctions {

    WebDriver driver = null;
    ExtentTest test;
    String highLightMode = "true";
//	String path = Screenshots.takeScreenshot(driver, testResult.getName());
//	String imagePath = test.addScreenCapture(path);


    public CommonFunctions(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        PageFactory.initElements(driver, this);
    }

    public String aLocByText = "//a[text()='%s']";
    ExtentReports report;

    /**
     * Method to click on a web element
     *
     * @param obj
     * @param objName
     */
    public void clickElement(By obj, String objName) {
        try {
            WebElement element = driver.findElement(obj);
            element.click();
        } catch (Exception e) {
            System.out.println("Something");
        }
    }

    public By byElementBuilder(String byType, String locator, String ...wildcard) {
        By element = null;
        locator = String.format(locator, (Object[]) wildcard);
        switch(byType) {
            case "id":
                element = By.id(locator);
                break;
            case "xpath":
                element = By.xpath(locator);
                break;
            case "name":
                element = By.name(locator);
                break;
            case "css":
                element = By.cssSelector(locator);
            default:
                element = null;
                break;
        }
        return element;
    }

    public void elementIsDisplayed(By obj, String objName) throws AWTException, InterruptedException{
        try {
            waitForElement(obj, objName);
            WebElement element = driver.findElement(obj);
            boolean flag = element.isDisplayed();
            if (!flag) {
                test.log(LogStatus.FAIL, objName + " is not getting displayed correctly");
            } else {
                highlightElement(element, objName);
                test.log(LogStatus.PASS, objName + " element is displayed");
            }
        }catch(Exception e) {
            test.log(LogStatus.ERROR, objName + " element is not displayed after " + 60 + " Seconds" + " Exception: " + e.getMessage());
        }

    }

    public void waitForElement(By obj, String objName) throws AWTException, InterruptedException{
        boolean flag = false;
        int waitTime = 0;
        try {
            do {
                try {
                    System.out.println("driver=" + driver);
                    Boolean cosa = driver.findElement(obj).isDisplayed();
                    System.out.println(cosa);
                    if (driver.findElement(obj).isDisplayed()) {
                        flag = true;
                        test.log(LogStatus.INFO, objName + " element is displayed");
                        break;
                    } else {
                        hardDelay(10000);
                        waitTime = waitTime + 10;
                        test.log(LogStatus.INFO, "wait for Object " + objName +" Time: " + waitTime + " seconds");
                    }
                } catch (Exception e) {
                    hardDelay(10000);
                    waitTime = waitTime + 10;
                    test.log(LogStatus.INFO, "wait for Object " + objName +" Time: " + waitTime + " seconds" + e.getMessage());
                }
            } while (waitTime < 60);
        } catch (Exception e) {
            test.log(LogStatus.ERROR, objName + " element is not displayed after " + 60 + " Seconds" + " Exception: " + e.getMessage());
        }

        if (!flag) {
            test.log(LogStatus.INFO, objName + " element is not displayed after " + 60 + " Seconds");
        }
    }

    protected void waitForElement(WebElement element, String objName) {
        boolean flag = false;
        int waitTime = 0;

        try {
            do {
                try {
                    if (element.isDisplayed()) {
                        flag = true;
                        test.log(LogStatus.INFO, objName + " element is displayed");

                        break;
                    } else {
                        hardDelay(10000);
                        waitTime = waitTime + 10;
                        test.log(LogStatus.INFO, "wait for Object " + objName +" Time: " + waitTime + " seconds");
                    }
                } catch (Exception e) {
                    hardDelay(10000);
                    waitTime = waitTime + 10;
                    test.log(LogStatus.INFO, "wait for Object " + objName +" Time: " + waitTime + " seconds" + e.getMessage());
                }
            } while (waitTime < 60);
        } catch (Exception e) {
            test.log(LogStatus.ERROR, objName + " element is not displayed after " + 60 + " Seconds" + " Exception: " + e.getMessage());
        }

        if (!flag) {
            test.log(LogStatus.INFO, objName + " element is not displayed after " + 60 + " Seconds");
        }
    }

    public void hardDelay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void highlightElement(WebElement element, String objName) {
        try {
            if ("true".equals(highLightMode)) {
                String originalStyle = getAttribute(element, objName, "style");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                        element,
                        "background-color: rgba(0, 128, 0, 0.15); border: 3px solid darkgreen !important;");
                hardDelay(300);
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                        element,
                        originalStyle);
                test.log(LogStatus.INFO, "Highlighted: " + element.getTagName() + "[" + objName + "]");
            }
        } catch (Exception e) {
            test.log(LogStatus.ERROR, "Unable to highlight: " + objName + " element is not displayed after " + 60 + " Seconds");
            return;
        }
    }

    protected String getAttribute(WebElement element, String name, String attribute) {
        String attValue = "";

        try {
            waitForElement(element, name);
            attValue = element.getAttribute(attribute);
//            log(LogType.INFO,
//                attValue + " is present in attribute " + attribute +
//                " of element " + name);
        } catch (Exception e) {
            test.log(LogStatus.INFO, name + " element is not displayed after " + 60 + " Seconds");
        }

        return attValue;
    }

    /**
     * Switch to iframe based on iframe element
     *
     * @throws Exception
     */
    public void switchToIframe(By obj, String objName) throws Exception {

        try {
            waitForElement(obj, objName);
            WebElement element = driver.findElement(obj);
            highlightElement(element, objName);

            if (element != null) {
                driver.switchTo().frame(element.getAttribute("id"));
            }
            test.log(LogStatus.INFO, "Switched to: " + obj);
        } catch (Exception e) {
            e.printStackTrace();
            test.log(LogStatus.ERROR, "Unable to switch to iframe: " + objName);
        }
    }

    /**
     * To get the text from the element
     * @param obj
     * @param objName
     * @return
     */
    public String getText(By obj, String objName) {
        String text = "";

        try {
            text = driver.findElement(obj).getText();
            test.log(LogStatus.INFO, text + " is present in element " + objName);
        } catch (Exception e) {
            test.log(LogStatus.ERROR, objName + " element is not displayed" + " Exception: " + e.getMessage());
        }
        return text;
    }

    /**
     * To enter text in the text box
     * @param obj
     * @param objName
     * @param key
     */
    public void enterText(By obj, String objName, String key) {
        try {
            waitForElement(obj, objName);
            WebElement element = driver.findElement(obj);
            highlightElement(element, objName);
            element.clear();
            hardDelay(1000);
            element.click();
            hardDelay(1000);
            element.sendKeys(key);
            test.log(LogStatus.INFO, key + " is entered in element: " + objName);
        } catch (Exception e) {
            test.log(LogStatus.ERROR, "Exception while entering text -" + key + " in element: " + objName,e.getMessage());
        }
    }

    /**
     * @param list - This parameter must be a list of WEB Elements built before
     * @param section - Indicates which section is being tested
     */
    public void validateListOfWebElements(List<WebElement> list, String section){
        try{
            boolean elementFound;
            for(WebElement element : list){
                elementFound = element.isDisplayed();
                if(elementFound){
                    highlightElement(element," Element in " + section + " is displayed correctly");
                }
            }
        }catch(Exception e){
            test.log(LogStatus.ERROR, "Exception while validating web elements in section " + section + ". Exception: " + e.getMessage());
        }
    }

    /**
     * Create locators dictionary - method to create a list of locators that have almost the same address and
     * only have a little change between them
     * @param locType - Could be: id, xpath, name or css
     * @param locBase - Is the base of the locators
     * @param optionsList - Is the list of all options that will be used to build the locators
     * @param sectionToTest - Indicates the section of the page that will be testing with this method
     * @implNote - optionsList parameter must be a string with options separated by '\n' in the JSON data file
     */
    public void buildAndValidateLocatorsElements(String locType,String locBase,String optionsList,String sectionToTest){
        try{
            String[] optionsArray = optionsList.split("\n");
            By element;
            for(int i=0;i<optionsArray.length;i++){
                element = byElementBuilder(locType,locBase,optionsArray[i]);
                elementIsDisplayed(element,"Element of " + sectionToTest + " section");
            }
        }catch(Exception e){
            test.log(LogStatus.FAIL, "Exception while building and validate locators elements. Exception: " + e.getMessage());
        }
    }

    public void moveToElement(By obj, String elementName) {
        try {
            WebElement element = driver.findElement(obj);
            highlightElement(element, elementName);
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(obj));
            test.log(LogStatus.INFO, "Moved to element: "+elementName);
        }catch(Exception e) {
            test.log(LogStatus.ERROR, "Exception while move to element "+elementName+" "+e.getMessage());
        }
    }


}
