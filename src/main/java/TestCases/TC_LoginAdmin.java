package TestCases;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Utility.ReadExcel;

public class TC_LoginAdmin {
	WebDriver driver;
	String filePath=".\\DATA\\data.xlsx"; //Do not give the absolute path of your local machine
	String SheetNameAdmin ="AdminData";
	WebDriverWait wait;
	@BeforeTest
	public void openDriver() {
		System.setProperty("webdriver.chrome.driver", ".\\DRIVER\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();		
	}
	
	@Test
	public void loginToAdmin() throws IOException {
		try {
			ReadExcel rExcel = new ReadExcel();
			driver.get(rExcel.readExcel(1, 0, filePath, SheetNameAdmin));
			LoginPage pg_Login = new LoginPage(driver);
			pg_Login.EnterUserName(rExcel.readExcel(1, 1, filePath, SheetNameAdmin));
			pg_Login.EnterPassword(rExcel.readExcel(1, 2, filePath, SheetNameAdmin));
			pg_Login.ClickOnLoginButton();
			//Explicit wait until page load		
			pg_Login.WaitToBeLoaded(90);
			Assert.assertEquals(pg_Login.GetLoginTitle(), rExcel.readExcel(1,3, filePath, SheetNameAdmin));
           
        } catch (Exception e) {
            e.printStackTrace();
        }	
	}
	
	@AfterTest
	public void driverClose() {
		driver.close();
	}

}
