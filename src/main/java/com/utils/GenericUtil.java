package com.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class GenericUtil {
    WebDriver driver;
    WebDriverWait webDriverWait;

    public GenericUtil(final WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 30);
    }

    ///Generic Methods

    public boolean selectFromList(WebElement element, String option) {
        boolean flag = false;
        try {
            Select select = new Select(element);
            select.selectByVisibleText(option);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean waitUntilClickable(WebElement element) {
        boolean flag = false;
        try {
            webDriverWait.ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .until(ExpectedConditions.elementToBeClickable(element));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }


    public boolean waitUntilElementVisible(WebElement element) {
        boolean flag = false;
        try {
            webDriverWait.ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .ignoring(TimeoutException.class)
                    .until(ExpectedConditions.visibilityOf(element));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public boolean verifyElementVisible(WebElement element) {
        boolean flag = false;
        try {
            webDriverWait.ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class)
                    .ignoring(ElementNotVisibleException.class)
                    .until(ExpectedConditions.visibilityOf(element));
            if (element.isDisplayed() && element.isEnabled()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public void moveMouseToElement(WebElement element) {
        Actions actions = new Actions(this.driver);
        actions.moveToElement(webDriverWait.until(ExpectedConditions.elementToBeClickable(element))).perform();
    }

    public void switchToTab(String tab) {
        this.driver.switchTo().window(tab);
    }

    public void closeTab(String tab) {
        switchToTab(tab);
        this.driver.close();
    }

    public List<String> getTabs() {
        ArrayList<String> tabs = new ArrayList<String>(this.driver.getWindowHandles());
        return tabs;
    }

    public boolean jsClick(WebElement element) {
        boolean flag = false;
        try {
            JavascriptExecutor jse = (JavascriptExecutor) this.driver;
            jse.executeScript("arguments[0].click()", element);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean jsScrollUp() {
        boolean flag = false;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,-350)");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean jsScrollDown() {
        boolean flag = false;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,350)");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


}
