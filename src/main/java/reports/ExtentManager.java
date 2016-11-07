package reports;

import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;
import java.util.Date;

public class ExtentManager {
    
    private static ExtentReports extent;
    
    public static ExtentReports getInstance(){
        if(extent==null) {
            Date d = new Date();
            String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
            extent=new ExtentReports("/Users/sislam13/Desktop/"+ fileName);
            extent.loadConfig(new File(System.getProperty("user.dir")+"/ReportsConfig.xml"));
        }
        return extent;
    }
    
}
