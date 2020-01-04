package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	WebDriver driver;
	
	/*===========Elements Declaration ================
	===============Setting up the paths and the names for the elements=======================*/
	
		//All WebElements are identified by @FindBy annotation
		//txt_Username
		@FindBy(xpath="//input[@name='uname']")
		WebElement txtUserName_Admin;
		
		//txt_Password
		@FindBy(xpath="//input[@name='password']")
		WebElement txtPassword_Admin;
		
		//btn_Login Button
		@FindBy(xpath="//button[@type='submit']")
		WebElement btn_Login_Admin;
		
		//lbl_AdminTitle
		@FindBy(xpath="//label[contains(text(),'Role :Admin')]")
		WebElement lbl_AdminTitle;
		
		//lbl_Dashboard
		@FindBy(xpath="//h1[contains(text(),'Dashboard')]")
		WebElement lbl_Dashboard;
	
	/*===========Constructor Initializing the elements================
	===============Passing out the webdriver which comes from test class=======================*/
		public LoginPage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);//This initElement method will create all WebElements
		}
	
	
	
	/*===========Steps for the login scenario================
	===============Write steps and assign them to the above elements=======================*/
	
		//Type username
		public void EnterUserName(String UName_Admin) {
			txtUserName_Admin.sendKeys(UName_Admin);
		}
		
		//Type Password
		public void EnterPassword(String Pwrd_Admin) {
			txtPassword_Admin.sendKeys(Pwrd_Admin);
		}
		
		//Click on Login
		public void ClickOnLoginButton() {
			btn_Login_Admin.click();
		}
		
		//Get the title of the login page
		public String GetLoginTitle() {
			return lbl_AdminTitle.getText();
		}
		
		//Wait for page to be loaded
		public void WaitToBeLoaded(int seconds) {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.visibilityOf(lbl_Dashboard));
			
			
		}
}
