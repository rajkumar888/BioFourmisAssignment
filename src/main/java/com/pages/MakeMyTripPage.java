package com.pages;

import com.utils.GenericUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MakeMyTripPage {

    WebDriver driver;
    WebDriverWait webDriverWait;
    GenericUtil genericUtil;

    public String checkOutTotalPrice;
    public String cartTotalPrice;
    public static Logger log = Logger.getLogger(MakeMyTripPage.class);


    public MakeMyTripPage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 30);
        genericUtil = new GenericUtil(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//li[@data-cy='account']")
    WebElement btnSigIn;

    @FindBy(id = "username")
    WebElement inputEmail;


    @FindBy(id = "SubmitCreate")
    WebElement btnCreateAccount;

    @FindBy(xpath = "//span[contains(@class,'userNameIcon')]")
    WebElement iconUser;

    @FindBy(id = "fromCity")
    WebElement inputFromCity;

    @FindBy(xpath = "//span[@class='landingSprite swipIcon']")
    WebElement iconSwitch;

    @FindBy(xpath = "//input[contains(@class,'react-autosuggest__input--open')]")
    WebElement inputSuggestion;

    @FindBy(xpath = "//ul[contains(@role,'listbox')]//p[contains(@class,'blackText')]")
    List<WebElement> listCitiesFromDropdown;

    @FindBy(id = "toCity")
    WebElement inputToCity;

    @FindBy(xpath = "//div[contains(@class,'fsw_inputBox dates inactiveWidget')]")
    WebElement btnDepartureDate;

    @FindBy(xpath = "//div[contains(@class,'DayPicker-Day--selected')]/parent::div/following-sibling::div[@class='DayPicker-Week']//p[@class=' todayPrice']")
    List<WebElement> labelNextWeekFares;


    @FindBy(xpath = "//a[contains(@class,'widgetSearchBtn')]")
    WebElement btnSearch;

    @FindBy(xpath = "//p[text()='Popular Filters']/../div//span[@title='IndiGo']/../preceding-sibling::span[@class='customCheckbox']")
    WebElement chkBoxIndigoFilter;

    @FindBy(xpath = "//p[text()='Popular Filters']/../div//span[@title='Non Stop']/../preceding-sibling::span[@class='customCheckbox']")
    WebElement chkBoxNonStopFilter;

    @FindBy(xpath = "//div[@class='listingCard']")
    List<WebElement> listFlights;

    @FindBy(xpath = "//li[@class='moreItem menu_More']//a[@class='makeFlex hrtlCenter column']")
    WebElement menuMore;

    @FindBy(xpath = "//div[contains(@class,'moreOption')]/a")
    List<WebElement> listMoreSubMenu;

    @FindBy(id = "hp-widget__sfrom")
    WebElement inputDealsFromCity;

    @FindBy(id = "hp-widget__sTo")
    WebElement inputDealsToCity;

    @FindBy(xpath = "//li[contains(@class,'ui-menu-item')]//span[@class='autoCompleteItem__label']")
    List<WebElement> listCitiesResultsDeals;

    @FindBy(id = "hp-widget__depart")
    WebElement inputDepartDateDeals;

    @FindBy(xpath = "//td[@data-handler='selectDay' and @data-month='3']")
    List<WebElement> dateListDeals;

    @FindBy(id = "searchBtn")
    WebElement btnSearchDeals;


    public boolean selectSubMenuUnderMore(String subMenu) {
        boolean flag = false;
        try {
            genericUtil.jsScrollUp();
            genericUtil.jsClick(menuMore);
            genericUtil.moveMouseToElement(menuMore);
            int cnt = listMoreSubMenu.size();
            if (cnt > 0) {
                for (WebElement element : listMoreSubMenu) {
                    log.info("More Submenu Item name " + element.getText().trim());
                    if (element.getText().trim().equalsIgnoreCase(subMenu)) {
                        element.click();
                        List<String> tabs = genericUtil.getTabs();
                        genericUtil.switchToTab(tabs.get(1));
//                        genericUtil.closeTab(tabs.get(0));
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public List<String> getListOfMoreSubMenuItems() {
        List<String> ls = new ArrayList<>();
        try {
            genericUtil.jsScrollUp();
            genericUtil.waitUntilElementVisible(menuMore);
            genericUtil.moveMouseToElement(menuMore);
            genericUtil.waitUntilElementVisible(listMoreSubMenu.get(0));
            int cnt = listMoreSubMenu.size();
            if (cnt > 0) {
                for (WebElement element : listMoreSubMenu) {
                    log.info("More Submenu Item name " + element.getText().trim());
                    ls.add(element.getText().trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public List<String> getListOfFlightResults() {
        List<String> ls = new ArrayList<>();
        try {
            genericUtil.waitUntilElementVisible(listFlights.get(0));
            int cnt = listFlights.size();
            if (cnt > 0) {
                for (WebElement element : listFlights) {
                    log.info("Flight details " + element.getText().trim());
                    ls.add(element.getText().trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }


    public boolean selectFromCity(String fromCity) {
        boolean flag = false;
        try {
            genericUtil.waitUntilClickable(inputFromCity);
            genericUtil.jsClick(iconSwitch);
//            genericUtil.jsClick(inputFromCity);
            inputFromCity.click();
            inputSuggestion.sendKeys(fromCity);
            inputSuggestion.clear();
            inputSuggestion.sendKeys(fromCity);
            inputSuggestion.click();
            genericUtil.waitUntilClickable(listCitiesFromDropdown.get(0));
            if (listCitiesFromDropdown.size() > 0) {
                for (WebElement element : listCitiesFromDropdown) {
                    if (element.getText().trim().toLowerCase().contains(fromCity.toLowerCase())) {
                        element.click();
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean selectToCity(String toCity) {
        boolean flag = false;
        try {
            genericUtil.jsClick(inputToCity);
            genericUtil.jsClick(inputSuggestion);
            inputSuggestion.sendKeys(toCity);
            inputSuggestion.clear();
            inputSuggestion.sendKeys(toCity);
            inputSuggestion.click();
            Thread.sleep(200);
            genericUtil.waitUntilClickable(listCitiesFromDropdown.get(0));
            if (listCitiesFromDropdown.size() > 0) {
                for (WebElement element : listCitiesFromDropdown) {
                    if (element.getText().trim().toLowerCase().contains(toCity.toLowerCase())) {
                        element.click();
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean selectLowestFareDateInNextWeek() {
        boolean flag = false;
        try {
            List<String> ls = new ArrayList<>();
            genericUtil.waitUntilElementVisible(btnDepartureDate);
            btnDepartureDate.click();
            genericUtil.waitUntilElementVisible(labelNextWeekFares.get(0));
            for (WebElement element : labelNextWeekFares) {
                log.info("fare  " + element.getText().trim());
                ls.add(element.getText().trim());
            }
            int min = ls.indexOf(Collections.min(ls));
            log.info("min of index " + min);
            labelNextWeekFares.get(min).click();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean selectAnyDepDateInDeals() {
        boolean flag = false;
        try {
            genericUtil.waitUntilElementVisible(inputDepartDateDeals);
            inputDepartDateDeals.click();
            genericUtil.waitUntilElementVisible(dateListDeals.get(0));
            dateListDeals.get(3).click();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean selectIndigoNonStopFilter() {
        boolean flag = false;
        try {
            genericUtil.waitUntilClickable(chkBoxIndigoFilter);
            chkBoxIndigoFilter.click();
            chkBoxNonStopFilter.click();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean clickOnSearchFlights() {
        boolean flag = false;
        try {
            genericUtil.waitUntilElementVisible(btnSearch);
            btnSearch.click();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean selectFromCityOnDeals(String fromCity) {
        boolean flag = false;
        try {
            genericUtil.waitUntilElementVisible(inputDealsFromCity);
            genericUtil.jsClick(inputDealsFromCity);
            inputDealsFromCity.click();
            inputDealsFromCity.clear();
            inputDealsFromCity.sendKeys(fromCity);
            inputDealsFromCity.click();
            genericUtil.waitUntilClickable(listCitiesResultsDeals.get(0));
            log.info("Number of cities " + listCitiesResultsDeals.size());
            String cityName = null;
            driver.navigate().refresh();
            inputDealsFromCity.sendKeys(fromCity);
            if (listCitiesResultsDeals.size() > 0) {
                for (WebElement element : listCitiesResultsDeals) {
                    cityName = element.getText().trim().toLowerCase();
                    log.info("City name " + cityName);
                    if (cityName.contains(fromCity.toLowerCase()) || cityName.contains("bangalore")) {
                        log.info("Selected City name " + cityName);
                        genericUtil.jsClick(element);
//                        element.click();
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean selectToCityOnDeals(String toCity) {
        boolean flag = false;
        try {
            genericUtil.waitUntilElementVisible(inputDealsToCity);
            inputDealsToCity.click();
            inputDealsToCity.sendKeys(toCity);
            inputDealsToCity.clear();
            inputDealsToCity.sendKeys(toCity);
            genericUtil.waitUntilClickable(listCitiesResultsDeals.get(0));
            if (listCitiesResultsDeals.size() > 0) {
                for (WebElement element : listCitiesResultsDeals) {
                    if (element.getText().trim().toLowerCase().contains(toCity.toLowerCase())) {
                        element.click();
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean clickOnSearchDeals() {
        boolean flag = false;
        try {
            genericUtil.waitUntilElementVisible(btnSearchDeals);
            btnSearchDeals.click();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


}
