
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Automation {
//	static String username = "vamsi.kattamudi5694@gmail.com"; // Your username
//	static String authkey = "u004af767b4abaaf";
public static void main(String[] args) throws Exception {
//	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
	
	String data1[] = {"Vamsi", "MSS","QAAnalyst"};
	String data2[] = {"Rosalin","Sigma","SAPPI"};
	Object whole[][]=new Object[2][2];
	for(int i =0;i<whole.length;i++) {
		LinkedHashMap<String,String> hm=new LinkedHashMap<String,String>();
		if(i+1==1) {
			hm.put("Name", data1[0]);
			hm.put("Company", data1[1]);
			hm.put("Designation", data1[2]);
		}else {
			hm.put("Name", data2[0]);
			hm.put("Company", data2[1]);
			hm.put("Designation", data2[2]);
		}
		whole[i]=new Object[]{hm};
	}
	for(int i=0;i<whole.length;i++) {
		for(int j=0;j<whole[i].length;j++) {
			System.out.println(whole[i][j]);
		}
	}
	  File file=new File("C:\\Users\\miracle\\Desktop\\Stage_FCST.xlsx");
	  boolean fileIsLocked = !file.renameTo(file);
	  
	  	try {
	  		FileWriter fw= new FileWriter("C:\\Users\\miracle\\Desktop\\Stage_FCST.xlsx");
	  		fw.flush();
	  		fw.close();
			System.out.println("File is open");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Runtime.getRuntime().exec("taskkill /F /IM excel.exe");
		}
	

	ArrayList<String> list=(ArrayList<String>) WebDriverManager.chromedriver().getVersions();
	for(String s:list) {
		System.out.println(s);
	}
//	System.out.println(WebDriverManager.firefoxdriver().getBinaryPath());
	WebDriverManager.chromedriver().setup();
	ChromeOptions options=new ChromeOptions();
//	options.addArguments("--incognito");
	WebDriver driver=new ChromeDriver(options);
	System.out.println("Chrome is opened");
	System.out.println("WebdriverManager is working fine");
//	DesiredCapabilities caps = new DesiredCapabilities();
//
//	caps.setCapability("name", "Selenium Test Example");
//	caps.setCapability("build", "1.0");
//	caps.setCapability("browser_api_name", "Edge20");
//	caps.setCapability("os_api_name", "Win10");
//	caps.setCapability("screen_resolution", "1024x768");
//	caps.setCapability("selenium_version", "2.53.1");
//
//	RemoteWebDriver driver = new RemoteWebDriver(
//			new URL("http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub"), caps);
	ExtentReports er1=new ExtentReports("D:\\report.html",true);
	ExtentTest test=er1.startTest("Experiment");
	driver.get("http://automationpractice.com/index.php");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.findElement(By.xpath(".//a[contains(text(),'Sign in')]")).click();
//	TakesScreenshot ts= (TakesScreenshot) driver;
//	String screen=ts.getScreenshotAs(OutputType.BASE64);
//	System.out.println(screen);
	Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).withName("MyScreenShot1").save();
	byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\Oxygen\\Selenium\\screenshots\\MyScreenShot1.png"));
	String encodedString = Base64.getEncoder().encodeToString(fileContent);
	
	test.log(LogStatus.PASS, "This is screenshot "+test.addScreenCapture("data:image/gif;base64,"+encodedString));
	er1.endTest(test);
	er1.flush();
	
	driver.findElement(By.id("email")).sendKeys("vamsi.kattamudi5694@gmail.com");
	driver.findElement(By.id("passwd")).sendKeys("selenium");
	driver.findElement(By.name("SubmitLogin")).click();
	
	driver.findElement(By.id("search_query_top")).sendKeys("Dresses");
	driver.findElement(By.name("submit_search")).click();
	
	driver.findElement(By.xpath(".//ul[@class='product_list grid row']//img[1]")).click();
//	driver.navigate().refresh();
	driver.findElement(By.xpath(".//*[@id='color_to_pick_list']//li[1]")).click();
	
	Select select=new Select(driver.findElement(By.id("group_1")));
	select.selectByVisibleText("M");
	driver.quit();
	
}
}
