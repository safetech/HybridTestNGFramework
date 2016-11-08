package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.util.Hashtable;

import static utils.GenericKeywords.actualDataAssertion;

public class Keywords {
    ExtentTest test;
    ApplicationKeywords appKeywords;
    public Keywords(ExtentTest t) {
        test = t;
    }

    public void executeKeywords(String testName, Hashtable<String, String> testData, Xls_Reader xls ) throws Exception {
        test.log(LogStatus.INFO, "Starting the execution of keywords");

         xls = new Xls_Reader(System.getProperty("user.dir")+"/data/TestData.xlsx");
        String sheetTC="TestCases";
        String sheetTD="TestData";
        int rows = xls.getRowCount(sheetTC);
        // prop init
        appKeywords = new ApplicationKeywords(test);

        for(int rNum=2;rNum<=rows;rNum++){
            String tName  = xls.getCellData(sheetTC, "TCID", rNum);
            String keyword  = xls.getCellData(sheetTC, "Keyword", rNum);
            String objIdentifier  = xls.getCellData(sheetTC, "ObjectIdentifier", rNum);
            String dataKey  = xls.getCellData(sheetTC, "DataKey", rNum);
            String expectedData = xls.getCellData(sheetTC,"ExpectedData",rNum);
            String data = testData.get(dataKey);
            
            test.log(LogStatus.INFO,tName +" ---- "+ keyword +" --- "+ objIdentifier + " ---- "+ dataKey);

            switch(keyword) {
                case "openBrowser":
                    GenericKeywords.openBrowser(data);
                    break;
                case "navigate":
                    appKeywords.navigate(objIdentifier);
                    break;
                case "click":
                    appKeywords.click(objIdentifier);
                    break;
                case "input":
                    appKeywords.sendKeys(objIdentifier, data);
                    break;
                case "switchtoFrame":
                    appKeywords.switchtoFrame(objIdentifier);
                    break;
                case "exitFrame":
                    GenericKeywords.exitFrame();
                    break;
                case "validateLogin":
                    appKeywords.validateLogin();
                    break;
                case "waitForSpecificSeconds":
                    appKeywords.waitForSpecificSeconds(dataKey);
                    break;
                case "getVisibleElementCount":
                    String objCount = GenericKeywords.getVisibleElementCount().toString();
                    xls.setCellData(sheetTC,"ActualData", rNum, objCount);
                    actualDataAssertion(xls.getCellData(sheetTC,"ActualData",rNum),expectedData);
                    break;
                case "closeSpecificBrowser":
                    appKeywords.closeSpecificBrowser(data);
                    break;                
                case "assertBlank":
                    GenericKeywords.assertBlank(objIdentifier);
                    break;                
                case "select":
                        GenericKeywords.select(objIdentifier, data);
                    break;                
                case "verifyElementPresent":
                        GenericKeywords.verifyElementPresent(objIdentifier);
                    break;                
                case "verifyTitle":
                        GenericKeywords.verifyTitle(objIdentifier);
                    break;                
                case "getObjectList":
                        GenericKeywords.getObjectList(objIdentifier);
                    break;                

            }
            
        }
        test.log(LogStatus.INFO, "All keywords executed for test" + testName);
        
        
    }

    public void quit() {
        appKeywords.quit();
    }
}
