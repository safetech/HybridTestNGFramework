package integration.base;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import reports.ExtentManager;
import utils.Keywords;
import utils.Xls_Reader;

public class BaseTest {
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//data//TestData.xlsx");
	public Keywords app;
	
	
	
}