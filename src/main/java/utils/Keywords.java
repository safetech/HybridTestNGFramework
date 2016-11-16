package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
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
            String  status = xls.getCellData(sheetTC,"Status",rNum);
            
            String data = testData.get(dataKey);
            
            test.log(LogStatus.INFO,tName +" ---- "+ keyword +" --- "+ objIdentifier + " ---- "+ dataKey);


            if(keyword.startsWith("get")){
                Method m = GenericKeywords.class.getMethod(keyword,String.class);
                String actualData = m.invoke(null,objIdentifier).toString();
                xls.setCellData(sheetTC,"ActualData", rNum, actualData);
                if(!expectedData.equals(""))
                    actualDataAssertion(xls.getCellData(sheetTC,"ActualData",rNum),expectedData);
            }else if (keyword.startsWith("set")) {
                WebElement element = GenericKeywords.getObject(objIdentifier);
                Method m = GenericKeywords.class.getMethod(keyword,WebElement.class,String.class);
                m.invoke(null,element,data);
            }
            else{
                switch (keyword) {
                    case "openBrowser":
                        GenericKeywords.openBrowser(data);
                        break;
                    case "navigate":
                        appKeywords.navigate(objIdentifier);
                        break;
                    case "click":
                        appKeywords.click(objIdentifier);
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
                    case "closeSpecificBrowser":
                        appKeywords.closeSpecificBrowser(dataKey);
                        break;
                    case "assertBlank":
                        GenericKeywords.assertBlank(objIdentifier);
                        break;
                    case "assertIsDisplayed":
                        GenericKeywords.assertIsDisplayed(objIdentifier);
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
                    case "selectDpsd":
                        GenericKeywords.selectDpsd(objIdentifier,data);
                        break;
                    case "hoverOver":
                        GenericKeywords.hoverOver(objIdentifier);
                        break;

                    default:
                        System.out.println(keyword +" is not defined in switch case");
                        break;

                }

            }
        }
        test.log(LogStatus.INFO, "All keywords executed for test" + testName);
        
        
    }

    public void quit() {
        appKeywords.quit();
    }

}
