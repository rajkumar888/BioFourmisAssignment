package com.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Scenario1Test2 {

    public String browser = "chrome";
    public WebDriver driver;
    public static Logger log = Logger.getLogger(Scenario1Test2.class);

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "True");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver.exe");
            driver = new FirefoxDriver();
            //log.info("Firefox Browser launched !!!");
        }
        driver.get("http://automationpractice.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void validatePayment() {
        log.info("login with newly created user");
        driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email_create']")));
        driver.findElement(By.xpath("//*[@id='email']")).isEnabled();
        driver.findElement(By.xpath("//*[@id='email']")).sendKeys("rajkumar888@gmail.com");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='passwd']")).sendKeys("Welcome@123");
        driver.findElement(By.xpath("//*[@id='SubmitLogin']/span")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Mouser over to woment tshirt page");
        WebElement btnWomen = driver.findElement(By.xpath("//*[@id='block_top_menu']/ul/li[1]"));
        Actions action = new Actions(driver);
        action.moveToElement(btnWomen).build().perform();

        List<WebElement> anchorTags = btnWomen.findElements(By.tagName("a"));
        log.info("Numer of Elements" + anchorTags.size());

        for (WebElement menu : anchorTags) {
            if (menu.getText().equals("T-shirts")) {
                menu.click();
                break;
            }
        }
        log.info("Add to cart and proceed to checkout");
        driver.findElement(By.xpath("//*[@id='center_column']/ul/li/div/div[1]/div/a[1]/img")).click();

        log.info("Switched to frame to add to cart");
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//*[@id='add_to_cart']/button/span")).click();
        driver.switchTo().defaultContent();
        log.info("Switched to normal window");

        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer_cart']/div[1]/div[2]/div[4]/a")));
        driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[2]/div[4]/a")).click();
        log.info("Proceed to checkout");

        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='center_column']/p[2]/a[1]")));
        driver.findElement(By.xpath("//*[@id='center_column']/p[2]/a[1]")).click();

        driver.findElement(By.xpath("//*[@id='center_column']/form/p/button")).click();
        WebElement checkBox = driver.findElement(By.xpath("//*[@id='cgv']"));
        checkBox.click();
        driver.findElement(By.xpath("//*[@id='form']/p/button")).click();
        driver.findElement(By.xpath("//a[@title='Pay by bank wire']")).click();
        driver.findElement(By.xpath("//*[@id='cart_navigation']/button")).click();
    }

    @AfterMethod
    public void tearDown() {
        log.info("Method to close the browser ");
        driver.quit();
    }
}

