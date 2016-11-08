package integration;


import com.relevantcodes.extentreports.LogStatus;
import integration.base.BaseTest;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Hashtable;


public class CreateLeadTest extends BaseTest {
   
    @Test(dataProvider = "getData")
    public void createLead(Hashtable<String, String> data) throws Exception {
    test = rep.startTest("CreateLeadTest");
        test.log(LogStatus.INFO, "Starting the create lead test");
        test.log(LogStatus.INFO, "Data -> "+data.toString());
        
        if(data.get("Runmode").equals("N")){
            test.log(LogStatus.SKIP, "Skipping the test as runmode is set to No");
            throw new SkipException("Skipping the test as runmode is set to No");
        }
        app.executeKeywords("CreateLeadTest",data,xls);
        test.log(LogStatus.PASS, "Login test Passed");
    }

    @AfterMethod
    public void endTest(){
        rep.endTest(test);
        rep.flush();
        app.quit();
    }
}
