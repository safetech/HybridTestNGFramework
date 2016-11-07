package integration;


    import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.sikuli.script.Screen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

    import static org.hamcrest.CoreMatchers.equalTo;
    import static org.hamcrest.MatcherAssert.assertThat;

public class PdfImageAssertions {

    public static WebDriver driver;
        public static void main(String args[]) {

            Screen screen = new Screen();

            ProfilesIni profile = new ProfilesIni();
            FirefoxProfile ffprofile = profile.getProfile("Selenium");
            driver = new FirefoxDriver(ffprofile);
            driver.manage().window().maximize();
            driver.get("file:///Users/sislam13/Desktop/TestImages/AGENT/full_PDF.pdf");

            try {
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e){}
                gotoPdfpage(8);
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){}
                File Agent1Actual = new File(screen.captureforHighlight(870,815,380,80).getFile("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/runtimeImages/"));
                File Agent1Expected = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/Agent1Page8-870-815-380-80.png");
                gotoPdfpage(6);
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){}
                File App1Actual = new File(screen.captureforHighlight(870,730,580,120).getFile("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/runtimeImages/"));
//                File App1Expected = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/Agent1Page8-870-815-380-80.png");
                File App1Expected = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/App1sigPage6-870-730-580-120.png");
                gotoPdfpage(7);
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){}
                File App2Actual = new File(screen.captureforHighlight(870,420,580,150).getFile("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/runtimeImages/"));
                File App2Expected = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/App2page7-870-420-580-150.png");
                
                File App3BlankExpected = new File(screen.captureforHighlight(870,845,580,180).getFile("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/runtimeImages/"));
                File App3BlankActual = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/App3BlankSigPage7-870-845-580-180.png");
                
                File MrcsExpected = new File(screen.captureforHighlight(870,1250,580,180).getFile("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/runtimeImages/"));
                File MrcsActual = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/MrcsMarktCodePage7-870-1250-580-180.png");
                gotoPdfpage(9);
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){}
                File AppEftExpected = new File(screen.captureforHighlight(905,1230,380,100).getFile("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/runtimeImages/"));
                File AppEftActual = new File("/Users/sislam13/dev/code/codehub/ole-qa-ui/src/main/resources/signatureImages/AppEftPage9-905-1230-380-100.png");
                
                assertThat(diff(Agent1Expected,Agent1Actual), equalTo(0.0));
                assertThat(diff(App1Expected,App1Actual), equalTo(0.0));
                assertThat(diff(App2Expected,App2Actual), equalTo(0.0));
                assertThat(diff(App3BlankExpected,App3BlankActual), equalTo(0.0));
                assertThat(diff(AppEftExpected,AppEftActual), equalTo(0.0));
                assertThat(diff(MrcsExpected,MrcsActual), equalTo(0.0));
                
            } catch (Exception e) {
                driver.close();
                e.printStackTrace();
            }
            
            driver.close();
        }
    public static Double diff(File expected, File actual) throws Exception {
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        
        img1 = ImageIO.read(expected);
        img2 = ImageIO.read(actual);
        
        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            return -1.0;
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >>  8) & 0xff;
                int b1 = (rgb1      ) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >>  8) & 0xff;
                int b2 = (rgb2      ) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        return  (p * 100.0);
    }
    public static void gotoPdfpage(int pageNumber){
        driver.findElement(By.id("pageNumber")).click();
        driver.findElement(By.id("pageNumber")).sendKeys(Keys.COMMAND+"a"+Keys.COMMAND);
        driver.findElement(By.id("pageNumber")).sendKeys(pageNumber+"\r");
    }

}
