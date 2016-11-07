package testcases;

import org.testng.annotations.Test;

/**
 * Created by sislam13 on 10/4/16.
 */
public class Application {
    
    @Test(priority = 1)
    public void doRegister(){
        System.out.println("Registering Test");
//        Assert.assertEquals("xyz","xyz");
        boolean isElementPresent=true;
//        Assert.assertTrue(isElementPresent,"Element not found");
//        Assert.fail("Failure message");
//        throw new SkipException("Skipping the test");    
        
    }
    
    @Test(priority = 2)
    public void doLogin(){
        System.out.println("Logging in");
    }

    @Test(priority = 3)
    public void doSendMail(){
        System.out.println("Sending mail");
    }

    @Test(priority = 4)
    public void doLogout(){
        System.out.println("Logging out");
    }
    
}
