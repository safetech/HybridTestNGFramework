package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNG;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;


public class GenericKeywords extends TestNG{
    
    public static WebDriver driver;
    public static Properties prop;
    public static ExtentTest test;


    public static SimpleDateFormat COMPAS_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat COMMON_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat NORMALIZED_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat DD_MMM_YYYY_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
    public static SimpleDateFormat MMMM_D_YYYY_FORMAT = new SimpleDateFormat("MMMM 1, yyyy");
    public static SimpleDateFormat YY_MM_DD_ZERO_TIME = new SimpleDateFormat("yyyy-MM-dd");
    
    
    
    public static void openBrowser(String browserName){
        System.out.println("Opening Browser");
        System.setProperty("webdriver.chrome.driver","/Users/sislam13/dev/apps/chrome/chrome-46/chromedriver");
        if(browserName.equals("Firefox")){

            FirefoxProfile ffprofile = new FirefoxProfile(new File("C:\\temp\\Apps\\Firefox\\Profiles"));
            driver = new FirefoxDriver(ffprofile);
        }else if(browserName.equals("Chrome")){
            driver= new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        test.log(LogStatus.INFO,"Browser opened successfully");
    }
    
    public void navigate(String urlKey){
        test.log(LogStatus.INFO,"Navigated to url");
        driver.get(prop.getProperty(urlKey));
    }
    
    public void click(String objectKey){
        try {
            waitForSpecificSeconds("1");
            test.log(LogStatus.INFO,"Clicking on "+prop.getProperty(objectKey));
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(getObject(objectKey)));
            getObject(objectKey).click();
            
        }catch (Exception e){
            reportFailures("Couldn't click on element -->"+e);
        }

    }
    public static void assertIsDisplayed(String objectKey){
        assertThat(objectKey+" is not Displayed",getObject(objectKey).isDisplayed(),equalTo(true));
    }
    public void sendKeys(String objectKey, String data){
        waitForSpecificSeconds("1");
        test.log(LogStatus.INFO,"Writing in "+prop.getProperty(objectKey));
        getObject(objectKey).sendKeys(Keys.COMMAND+"a");
        getObject(objectKey).sendKeys(data);
        getObject(objectKey).sendKeys(Keys.TAB);
    }
    
    public void switchtoFrame(String frameIndex) {
        test.log(LogStatus.INFO,"Switching to frame "+frameIndex );
        int s = driver.findElements(By.tagName("iframe")).size();
        System.out.println("Total frames - "+s);
        driver.switchTo().frame(Integer.parseInt(frameIndex));
    }
    public static void waitForSpecificSeconds(String numOfSeconds) {
        try {
            Thread.sleep(Integer.parseInt(numOfSeconds)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void closeSpecificBrowser(String Browser){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> windows = new ArrayList<String> (driver.getWindowHandles());
        String baseWindowHdl = driver.getWindowHandle();
        driver.switchTo().window(windows.get(Integer.parseInt(Browser)));
        driver.close();
        driver.switchTo().window(baseWindowHdl);
    }
    public static void exitFrame() {
        test.log(LogStatus.INFO,"Exiting from frame");
        driver.switchTo().defaultContent();
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
    public static Integer getVisibleElementCount(String type) {
        return Integer.parseInt(String.valueOf(executeScript("return $('"+type+":visible').length")));
    }    
    public static void setDob(WebElement element, String data) {
        String date = DateUtils.getDOBofPersonTurningAgeToday(Integer.parseInt(data));
        element.sendKeys(date);
    }     
    public static void setMpbed(WebElement element, String data) {
        String date = DateUtils.getFirstDayOfPastOrFutureMonths(Integer.parseInt(data));
        element.sendKeys(date);
        element.sendKeys(Keys.RETURN);
        
    }     
    public static void setDpsd(WebElement element, String data) {
        String date = DateUtils.getFirstDayOfPastOrFutureMonths(Integer.parseInt(data));
        element.sendKeys(date);
    }    
    public static void setText(WebElement element, String data) {
        element.sendKeys(Keys.CONTROL+"a");
        element.sendKeys(data);
    }    
    public static String getText(String element) {
        String actualText = getObject(element).getText();
        return actualText;
    }   
    public  static void hoverOver(String objIdentifier){
        Actions actions=new Actions(driver);
        actions.moveToElement(getObject(objIdentifier)).perform();
    }
    public static String getStateCode(String element) {
        sendKey(Keys.TAB);
        String ZipCode = driver.findElement(By.id("ZipCode")).getAttribute("value");
        if (getObject(element).getAttribute("value").equals("")) {
            driver.findElement(By.id("ZipCode")).sendKeys(Keys.COMMAND + "a");
            driver.findElement(By.id("ZipCode")).sendKeys("fgfgf");
            driver.findElement(By.id("ZipCode")).sendKeys(Keys.RETURN);
        }

            driver.findElement(By.cssSelector("#ZipCode")).sendKeys(Keys.COMMAND + "a");
            driver.findElement(By.id("ZipCode")).sendKeys(ZipCode);
            waitForSpecificSeconds("1");
            driver.findElement(By.id("ZipCode")).sendKeys(Keys.RETURN);
            waitForSpecificSeconds("1");
        
        waitForSpecificSeconds("1");
        return getObject(element).getAttribute("value");
         
    }
    
    public static void blur(String selector){
        executeScript("$('"+selector+"').blur()");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
        }
    }
    public static Object executeScript(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
         
    }
    
    public static void select(String objectKey, String data) throws Exception {
        Thread.sleep(1000);
     int size = new Select(driver.findElement(By.id(prop.getProperty(objectKey)))).getOptions().size();
     WebElement dropdown = driver.findElement(By.id(prop.getProperty(objectKey)));
        int i;
        for(i=0; i<size; i++){
            sendKey(Keys.ENTER);
            dropdown.sendKeys(Keys.ARROW_DOWN);
            if(dropdown.getAttribute("value").equals(data)){
                test.log(LogStatus.INFO,"Requested effective date selected");
                break;
            }
        }
            if(i==size){
                reportFailures("Couldn't find requested effective date");
                throw new Exception("Couldn't find requested effective date");
            }
        
    }    
    public static void selectDpsd(String objectKey, String data) throws Exception {
        Thread.sleep(1000);
        blur("#ReqEffectiveDate");
        Thread.sleep(1000);
     int size = new Select(driver.findElement(By.id(prop.getProperty(objectKey)))).getOptions().size();
     WebElement dropdown = driver.findElement(By.id(prop.getProperty(objectKey)));
        int i;
        String dpsd=DateUtils.getFirstDayOfFutureMonth(Integer.parseInt(data));
        
        for(i=0; i<size; i++){
            //sendKey(Keys.ENTER);
            dropdown.sendKeys(Keys.ARROW_DOWN);
            if(dropdown.getAttribute("value").equals(dpsd)){
                test.log(LogStatus.INFO,"Requested effective date selected");
                break;
            }
        }
        sendKey(Keys.TAB);
            if(i==size){
                reportFailures("Couldn't find requested effective date");
                throw new Exception("Couldn't find requested effective date");
            }
        
    }
    
    public static void sendKey(Keys key){
        Actions obj = new Actions(driver);
        obj.sendKeys(key).perform();
    }
    public static void sendClick(){
        Actions obj = new Actions(driver);
        obj.click().perform();
    }


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
    
    public static void verifyElementPresent(String objectKey){
        int i= getObjectList(objectKey).size();
        
        if(i==0)
            reportFailures("Object not found - "+objectKey);
    }
    
    public static List<WebElement> getObjectList(String objectKey){
        List<WebElement> e=null;
        try{
            if(objectKey.endsWith("_xpath"))
                e = driver.findElements(By.xpath(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_id"))
                e = driver.findElements(By.id(prop.getProperty(objectKey)));
            else if(objectKey.endsWith("_name"))
                e = driver.findElements(By.name(prop.getProperty(objectKey)));
        }catch(Exception e1){
            reportFailures(e1.getMessage());
        }
        return e;
    }    
    
    
    
    public static String getDOBofPersonTurningAgeToday(int age) {
        Date dob = org.apache.commons.lang3.time.DateUtils.addYears(new Date(), -age);
        return NORMALIZED_DATE_FORMAT.format(dob);
    }
    public static void verifyTitle(String expectedTitleKey){
        String expectedTitle = prop.getProperty(expectedTitleKey);
        String actualTitle=driver.getTitle();
        if(expectedTitle.equals(actualTitle))
            test.log(LogStatus.INFO,"Title is matched");
        else {
            reportFailures("Title didn't match - "+actualTitle);
        }
    }
    public static void actualDataAssertion(String actualData, String expectedData){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(expectedData,containsString(actualData));
    }
    //*******************************************/
    public static void reportFailures(String failurMsg){
        test.log(LogStatus.FAIL,failurMsg);
        takeScreenShot();
        Assert.fail(failurMsg);
    }
    public static void takeScreenShot(){
        // fileName of the screenshot
        Date d=new Date();
        String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
        // store screenshot in that file
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"/src/test/screenshots/"+screenshotFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //put screenshot file in reports
        test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"/src/test/screenshots/"+screenshotFile));

    }
}
