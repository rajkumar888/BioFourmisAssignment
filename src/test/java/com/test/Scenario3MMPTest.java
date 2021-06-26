package com.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Scenario3MMPTest {

    public String browser = "chrome";
    public WebDriver driver;
    public static Logger log = Logger.getLogger(Scenario3MMPTest.class);

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "True");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            log.info("Chrome Browser launched !!!");

        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver.exe");
            driver = new FirefoxDriver();
            log.info("Firefox Browser launched !!!");
        }

        log.info("Open make my trip website ");
        driver.get("https://www.makemytrip.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

    }

    @Test
    public void verifyMMTTripDeals() {

        WebElement element;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);


        log.info("Select Bengaluru to Hyderabad flights");
        driver.findElement(By.xpath("//img[@alt='Make My Trip']")).click();

        WebElement moreBtn = driver.findElement(By.xpath("//span[@class='chNavIcon appendBottom2 chSprite chMore']"));
        Actions action = new Actions(driver);
        action.moveToElement(moreBtn).build().perform();

        List<WebElement> elements = driver.findElements(By.xpath("//*[@id='SW']/div[1]/div[2]/div/div/nav/ul/li[9]/div/a"));

        for (WebElement ele : elements) {
            log.info(ele.getText());
            System.out.println(ele.getText());
        }

        WebElement deals = driver.findElement(By.xpath("//*[@id='SW']/div[1]/div[2]/div/div/nav/ul/li[9]/div/a[3]"));
        deals.click();

        Set<String> st = driver.getWindowHandles();
        for (String handle : st) {
            driver.switchTo().window(handle);
            log.info(handle);
        }

        // click on flight icon
        driver.findElement(By.xpath(" //li[@data-cy='menu_Flights']")).click();
        List<WebElement> dynamicList1 = driver.findElements(By.cssSelector("p[class='font14 appendBottom5 blackText"));
        for (int j = 0; j < dynamicList1.size(); j++) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String text1 = dynamicList1.get(j).getText();
            log.info("Text is" + text1);
            if (text1.contains("Hyderabad")) {
                dynamicList1.get(j).click();
                break;
            }
        }
        log.info("Select calendar date after 2 weeks and then search the available flights");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 14);
        String departureDate = new SimpleDateFormat("MMM dd yyyy").format(cal.getTime());

        driver.findElement(By.xpath("//div[@class='DayPicker-Day'][contains(@aria-label, '" + departureDate + "')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Search')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Search')]")).click();

        log.info("Filter Indigo and Non stop filter criteria");
        driver.findElement(By.xpath("//button[text()='OKAY, GOT IT!']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='makeFlex hrtlCenter flexOne']//span[@title='IndiGo']")));
        WebElement ele = driver.findElement(By.xpath("//div[@class='makeFlex hrtlCenter flexOne']//span[@title='IndiGo']"));
        js.executeScript("arguments[0].click();", ele);

        driver.findElement(By.xpath("//div[@class='makeFlex hrtlCenter flexOne']//span[@title='Non Stop']")).click();
        driver.findElement(By.xpath("//*[@id='search-button']")).click();

        log.info("Get the number of flight details ");
        By eachSearchElement = By.xpath("//*[@id='left-side--wrapper']/div[3]");
        List<WebElement> allflightDetails = driver.findElements(eachSearchElement);
        int totalFlights = allflightDetails.size();
        log.info("There are " + totalFlights + " present on the page");
    }

    @AfterMethod
    public void tearDown() {
        log.info("Method to close the browser ");
//        driver.quit();
    }
}
