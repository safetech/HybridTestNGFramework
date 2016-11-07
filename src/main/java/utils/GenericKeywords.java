package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.TestNG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;


public class GenericKeywords extends TestNG{
    
    public static WebDriver driver;
    public static Properties prop;
    public static ExtentTest test;
    
    public static void openBrowser(String browserName){
        System.out.println("Opening Browser");
        System.setProperty("webdriver.chrome.driver","/Users/sislam13/dev/apps/chrome/chrome-46/chromedriver");
        if(browserName.equals("Firefox")){
            driver = new FirefoxDriver();
        }else if(browserName.equals("Chrome")){
            driver= new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        test.log(LogStatus.INFO,"Browser opened successfully");
    }
    
    public void navigate(String urlKey){
        test.log(LogStatus.INFO,"Navigated to url");
//        System.out.println("Navigating to "+prop.getProperty(urlKey) );
        driver.get(prop.getProperty(urlKey));
    }
    
    public void click(String objectKey){
        test.log(LogStatus.INFO,"Clicking on "+prop.getProperty(objectKey));
        getObject(objectKey).click();
    }

    public void sendKeys(String objectKey, String data){
        test.log(LogStatus.INFO,"Writing in "+prop.getProperty(objectKey));
        getObject(objectKey).sendKeys(data);
    }
    
    public void switchtoFrame(String frameIndex) {
        test.log(LogStatus.INFO,"Switching to frame "+frameIndex );
        int s = driver.findElements(By.tagName("iframe")).size();
        System.out.println("Total frames - "+s);
        //driver.switchTo().frame("zohoiam");
        driver.switchTo().frame(Integer.parseInt(frameIndex));
    }
    public void waitForSpecificSeconds(String numOfSeconds) {
        try {
            Thread.sleep(Integer.parseInt(numOfSeconds)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void closeSpecificBrowser(String Browser){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> windows = new ArrayList<String> (driver.getWindowHandles());
        String baseWindowHdl = driver.getWindowHandle();
        driver.switchTo().window(windows.get(Integer.parseInt(Browser)));
        driver.close();
        driver.switchTo().window(baseWindowHdl);
    }
    public void exitFrame() {
        test.log(LogStatus.INFO,"Exiting from frame");
        driver.switchTo().defaultContent();
    }
    public void fillAndSubmitCheatPage() {
        //
    }
    public static void assertBlank(String objectKey) {
            String[] ObjectKeys = objectKey.split(",");

        String assertMessage = "Testing following element for blankness: %s";
        for (String Element : ObjectKeys) {
            WebElement element=getObject(Element);
            switch (element.getTagName().toLowerCase()) {
                case "input":
                    switch (element.getAttribute("type").toLowerCase()) {
                        case "radio":
                        case "checkbox":

                            assertThat(String.format(assertMessage, element.getAttribute("id")), element.isSelected(), equalTo(false));
                            break;
                        default:
                            assertThat(String.format(assertMessage, element.getAttribute("id")), element.getAttribute("value"), equalTo(""));
                            break;
                    }
                    break;
                case "select":
                case "textarea":
                    assertThat(String.format(assertMessage, element.getAttribute("id")), element.getAttribute("value"), equalTo(""));
                    break;
                default:
                    fail();
            }
        }
    }
//    public int getVisibleElementCount() {
//        return Integer.parseInt(getScriptResult("$('input:visible, select:visible').length"));
//    }

    public static WebElement getObject(String objectKey){
        WebElement e=null;
        try{
            if(objectKey.endsWith("_xpath")) 
                
                e = driver.findElement(By.xpath(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_id"))
                e = driver.findElement(By.id(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_name"))
                e = driver.findElement(By.name(prop.getProperty(objectKey)));          
            else if(objectKey.endsWith("_cssSelector"))
                e = driver.findElement(By.cssSelector(prop.getProperty(objectKey)));  
            else if(objectKey.endsWith("_className"))
                e = driver.findElement(By.className(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_tagName"))
                e = driver.findElement(By.tagName(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_partialLinkText"))
                e = driver.findElement(By.partialLinkText(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_linkText"))
                e = driver.findElement(By.linkText(prop.getProperty(objectKey)));

        }catch(Exception e1){
            reportFailures("Could not find Object ->"+e1.getMessage());
        }
        return e;
    }    
    public List<WebElement> getObjects(String objectKey){
        List<WebElement> e=null;
        try{
            if(objectKey.endsWith("_xpath")) 
                e = driver.findElements(By.xpath(prop.getProperty(objectKey)));
        }catch(Exception e1){
            reportFailures("Could not find Object ->"+e1.getMessage());
        }
        return e;
    }
    public void verifyTitle(String expectedTitleKey){
        String expectedTitle = prop.getProperty(expectedTitleKey);
        String actualTitle=driver.getTitle();
        if(expectedTitle.equals(actualTitle))
            test.log(LogStatus.PASS,"Title is matched");
        else {
            reportFailures("Title didn't match");
        }
    }
    
    //*******************************************/
    public static void reportFailures(String failurMsg){
        test.log(LogStatus.FAIL,failurMsg);
        Assert.fail(failurMsg);
    }
    public void takeScreenShot(){
        // fileName of the screenshot
        Date d=new Date();
        String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
        // store screenshot in that file
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //put screenshot file in reports
        test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));

    }
}
