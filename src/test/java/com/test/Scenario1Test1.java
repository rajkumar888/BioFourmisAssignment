package com.test;

import excelUtility.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Scenario1Test1 {

	public String browser = "chrome";
	public WebDriver driver;
	public static Logger log = Logger.getLogger(Scenario1Test1.class);

	@Parameters("browser")
	@BeforeMethod
	public void setup(String browser) {
		log.info("Launch the chrome or firefox browser");

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "True");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver.exe");
			driver = new FirefoxDriver();
			log.info("Firefox Browser launched !!!");
		}
		driver.get("http://automationpractice.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	@DataProvider
	public Iterator<Object[]> getTestData() {
		log.info("method to get the test data and iterate it ");
		ArrayList<Object[]> testData = TestUtil.getDataFromExcel();
		return testData.iterator();
	}

	@Test(dataProvider = "getTestData")
	public void signup(String emailAddress, String firstName, String lastName, String password, String address,
					   String city, String state, String postalCode, String mobilePhone) {
		log.info("enter the email address for sign up ");
		driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email_create']")));
		driver.findElement(By.xpath("//input[@id='email_create']")).sendKeys(emailAddress);
		driver.findElement(By.xpath("//*[@id='SubmitCreate']/span")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Enter the sign up form details ");
		String pageHeading = driver.findElement(By.className("page-heading")).getText();
		log.info("Page Heading:" + pageHeading);

		driver.findElement(By.xpath("//input[@id='customer_firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@id='customer_lastname']")).isEnabled();
		driver.findElement(By.xpath("//input[@id='customer_lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@id='passwd']")).isEnabled();
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='address1']")).isEnabled();
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(address);
		driver.findElement(By.xpath("//input[@id='city']")).isEnabled();
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys(city);
		Select select = new Select(driver.findElement(By.xpath("//select[@id='id_state']")));
		select.selectByVisibleText(state);
		driver.findElement(By.xpath("//input[@id='postcode']")).isEnabled();
		driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys(postalCode);
		driver.findElement(By.xpath("//input[@id='phone_mobile']")).isEnabled();
		driver.findElement(By.xpath("//input[@id='phone_mobile']")).sendKeys(mobilePhone);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Create account and then sign out");
		driver.findElement(By.xpath("//*[@id='submitAccount']/span")).click();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@title='Log me out']")).click();
	}

	@AfterMethod
	public void tearDown() {
		log.info("Method to close the browser ");
		driver.quit();
	}
}
