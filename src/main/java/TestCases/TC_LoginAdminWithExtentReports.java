package TestCases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Pages.LoginPage;
import Utility.ReadExcel;

public class TC_LoginAdminWithExtentReports {
	WebDriver driver;
	String filePath = ".\\DATA\\data.xlsx"; // Do not give the absolute path of your local machine
	String SheetNameAdmin = "AdminData";
	LoginPage pg_Login;
	ReadExcel rExcel;
	WebDriverWait wait;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extntReports;
	ExtentTest extntTest;

	@BeforeTest
	public void setExntRep() {

		// System.getProperty("user.dir") ---> This method will give the location of the current directory
		// Give the path where you want to save the extent report html
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
		// ExtentHtmlReporter htmlReporter --> This object will always responsible for the look and feel
		htmlReporter.config().setDocumentTitle("Automation Execution Report");// Title of the page
		htmlReporter.config().setReportName("Functional Report");// Report name
		htmlReporter.config().setTheme(Theme.DARK);

		// ExtentReports extntReports; --> This is for the creation of test cases or
		// entries in report
		extntReports = new ExtentReports();
		extntReports.attachReporter(htmlReporter);
		extntReports.setSystemInfo("Hostname", "LocalHost");
		extntReports.setSystemInfo("OS", "Windows");
		extntReports.setSystemInfo("Browser", "Chrome");

	}

	@BeforeMethod
	public void openDriver() {
		System.setProperty("webdriver.chrome.driver", ".\\DRIVER\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	@Test(priority=1)
	public void loginToAdmin() throws IOException {
		try {
			
			//ExtentTest extntTest --> Maintaining entries saying pass or fail 
			extntTest = extntReports.createTest("TC_LoginAdmin");//This will create a new test case in your report
			
			rExcel = new ReadExcel();
			driver.get(rExcel.readExcel(1, 0, filePath, SheetNameAdmin));
			pg_Login = new LoginPage(driver);
			pg_Login.EnterUserName(rExcel.readExcel(1, 1, filePath, SheetNameAdmin));
			pg_Login.EnterPassword(rExcel.readExcel(1, 2, filePath, SheetNameAdmin));
			pg_Login.ClickOnLoginButton();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=2)
	public void VerifyLogin() throws IOException {
		try {
			extntTest = extntReports.createTest("TC_VerifyLoginAdmin");//This will create a new test case in your report
			// Explicit wait until page load
			pg_Login.WaitToBeLoaded(90);
			Assert.assertEquals(pg_Login.GetLoginTitle(), rExcel.readExcel(1, 3, filePath, SheetNameAdmin));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void tearDown(ITestResult extnt_Result) throws IOException {
		try {
			if(extnt_Result.getStatus()==ITestResult.FAILURE) {
				extntTest.log(Status.FAIL, "TEST CASE FAILED IS "+ extnt_Result.getName());//to add name in extent report
				extntTest.log(Status.FAIL, "TEST CASE FAILED IS "+ extnt_Result.getThrowable());//to add error/exception in extent report
				
				String screenshotPath = TC_LoginAdminWithExtentReports.getScreenshot(driver, extnt_Result.getName());
				extntTest.addScreenCaptureFromPath(screenshotPath);
				
			}else if (extnt_Result.getStatus()== ITestResult.SKIP) {
				extntTest.log(Status.SKIP, "TEST CASE SKIPPED IS "+ extnt_Result.getName());
			}else if (extnt_Result.getStatus()== ITestResult.SUCCESS) {
				extntTest.log(Status.SKIP, "TEST CASE PASSED IS "+ extnt_Result.getName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	//This is a supported method for attaching screenshots
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
		String dateName = new SimpleDateFormat("YYYYMMDDhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		//after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir")+"/Screenshots/"+screenshotName +dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
		
	}

	@AfterTest
	public void endReport() {
		extntReports.flush();
	}

	@AfterMethod
	public void closeDriver() {
		driver.close();

	}

}
