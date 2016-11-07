package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.FileInputStream;
import java.util.Properties;

public class ApplicationKeywords extends GenericKeywords{
       public ApplicationKeywords(ExtentTest t){
           test=t;
           t.log(LogStatus.INFO, "Loading properties file");
        prop = new Properties();
        try {
            FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"/project.properties");
            prop.load(fs);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }
    public void validateLogin(){
        System.out.println("Validating Login" );
    }

    public void quit() {
        if(driver!=null)
            driver.quit();
    }
}
