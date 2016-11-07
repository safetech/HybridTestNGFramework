package integration;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import integration.base.BaseTest;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentManager;
import utils.DataUtil;
import utils.Keywords;
import utils.Xls_Reader;

import java.util.Hashtable;

/**
 * Created by sislam13 on 10/14/16.
 */
public class LoginTest extends BaseTest{
    Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"/data/TestData.xlsx");
    ExtentReports rep = ExtentManager.getInstance();
    ExtentTest test;
    Keywords app = new Keywords(test);
    
    @Test(dataProvider = "getData")
    public void loginTest(Hashtable<String, String> data){
        
        test = rep.startTest("LoginTest");
        test.log(LogStatus.INFO, "Starting the login Test");
        test.log(LogStatus.INFO, "Data ->" + data.toString());
        
        if(data.get("Runmode").equals("N")){
            test.log(LogStatus.SKIP, "Skipping the test as runmode is set to No");
            throw new SkipException("Skipping the test as runmode is set to No");
        }

        app = new Keywords(test);
        app.executeKeywords("LoginTest", data, xls);
        test.log(LogStatus.PASS, "Login Test Passed");
    }
    
    @Test
    public void testMethod(){
System.out.print("Testing ");
    }
    
    @AfterMethod
    public void endTest(){
        rep.endTest(test);
        rep.flush();
        app.quit();
    }
    @DataProvider
    public Object[][] getData(){
        return DataUtil.getTestData(xls, "LoginTest");
    }
}
