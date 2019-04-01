package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetScreenshot {
    public static String timestamp()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    }
    public static void CaptureScreenshotForPassTestCase(WebDriver driver, String name)throws Exception
    {
        TakesScreenshot tk= (TakesScreenshot)driver;
        File source= tk.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("../app/src/screenshot/passed/"+name+"/"+timestamp()+".png"));
    }

    public static String CaptureScreenshotForFailTestCase(WebDriver driver, String name)throws Exception
    {
        TakesScreenshot tk= (TakesScreenshot)driver;
        String dest="../screenshot/failed/"+name+".png";
        File source= tk.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("../screenshot/failed/"+name+".png"));
        return dest;
    }
}
