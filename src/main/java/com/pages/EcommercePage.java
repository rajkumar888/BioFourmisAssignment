package com.pages;

import com.utils.GenericUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EcommercePage {

    WebDriver driver;
    WebDriverWait webDriverWait;
    GenericUtil genericUtil;

    public String checkOutTotalPrice;
    public String cartTotalPrice;
    public static Logger log = Logger.getLogger(EcommercePage.class);

    public EcommercePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, 30);
        genericUtil = new GenericUtil(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "login")
    WebElement btnSigIn;

    @FindBy(id = "email_create")
    WebElement inputEmailCreate;

    @FindBy(id = "SubmitCreate")
    WebElement btnCreateAccount;

    @FindBy(id = "uniform-id_gender1")
    WebElement radioMr;

    @FindBy(id = "uniform-id_gender2")
    WebElement radioMrs;

    @FindBy(id = "customer_firstname")
    WebElement inputFirstName;

    @FindBy(id = "customer_lastname")
    WebElement inputLastName;

    @FindBy(id = "email")
    WebElement inputEmail;

    @FindBy(id = "passwd")
    WebElement inputPassword;

    @FindBy(id = "SubmitLogin")
    WebElement btnLogin;

    @FindBy(id = "address1")
    WebElement inputAddress;

    @FindBy(id = "city")
    WebElement inputCity;

    @FindBy(xpath = "//select[@name='id_state']")
    WebElement selectState;

    @FindBy(id = "postcode")
    WebElement inputPostcode;

    @FindBy(id = "phone_mobile")
    WebElement inputMobilePhone;


    @FindBy(id = "submitAccount")
    WebElement btnRegisterAccount;

    @FindBy(className = "info-account")
    WebElement labelWelcomeToAccount;
    //Welcome to your account. Here you can manage all of your personal information and orders.
    //Welcome to your account. Here you can manage all of your personal information and orders.

    @FindBy(css = "a[title='Log me out']")
    WebElement btnSignOut;

    @FindBy(css = "a[title='Women']")
    WebElement menuWomen;

    @FindBy(xpath = "//li[@class='sfHover']//a[@title='Casual Dresses']")
    WebElement subMenuCasualDresses;

    @FindBy(xpath = "//a[@class='product_img_link']/img")
    List<WebElement> imgProducts;

    @FindBy(css = "a[title='Add to cart'] span")
    List<WebElement> btnAddToCart;

    @FindBy(css = "span[title='Continue shopping']")
    WebElement btnContinueShopping;

    @FindBy(css = "i[class='icon-ok']")
    WebElement iconOk;

    @FindBy(xpath = "//a[@class='button btn btn-default standard-checkout button-medium']")
    WebElement btnProceedToCheckOut;

    @FindBy(css = "p[class='cart_navigation clearfix'] a[title='Proceed to checkout']")
    WebElement btnConfirmCheckout;

    @FindBy(name = "processAddress")
    WebElement btnConfirmAddress;

    @FindBy(name = "processCarrier")
    WebElement btnConfirmCarrier;

    @FindBy(id = "uniform-cgv")
    WebElement chkBoxAgreeTndC;

    @FindBy(className = "bankwire")
    WebElement linkPayByBankwire;

    @FindBy(css = "button[class='button btn btn-default button-medium']")
    WebElement btnConfirmOrder;

    @FindBy(xpath = "//strong[text()='Your order on My Store is complete.']")
    WebElement labelOrderConfirmed;


    @FindBy(xpath = "//span[@class='ajax_block_cart_total']")
    WebElement labelCheckoutTotalPrice;

    @FindBy(css = "a[title='View my shopping cart']")
    WebElement viewMyCart;

    @FindBy(xpath = "//span[@class='price cart_block_total ajax_block_cart_total']")
    WebElement labelCartTotalPrice;

    @FindBy(id = "button_order_cart")
    WebElement btnCartCheckOut;

    @FindBy(css = "#uniform-selectProductSort")
    WebElement listSortBy;


    public boolean login(String un, String pwd) {
        boolean flag = false;
        try {
            btnSigIn.click();
            genericUtil.waitUntilClickable(inputEmail);
            inputEmail.sendKeys(un);
            inputPassword.sendKeys(pwd);
            btnLogin.click();
            return genericUtil.verifyElementVisible(labelWelcomeToAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean logOut() {
        boolean flag = false;
        try {
            btnSignOut.click();
            return genericUtil.verifyElementVisible(inputEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean selectSubMenuUnderWomen() {
        boolean flag = false;
        try {
            genericUtil.moveMouseToElement(menuWomen);
            genericUtil.waitUntilClickable(subMenuCasualDresses);
            subMenuCasualDresses.click();
            return genericUtil.verifyElementVisible(listSortBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean addItemToCart() {
        boolean flag = false;

        try {
            genericUtil.waitUntilElementVisible(listSortBy);
            if (imgProducts.size() > 0) {
                genericUtil.jsScrollDown();
                genericUtil.moveMouseToElement(imgProducts.get(0));
                btnAddToCart.get(0).click();
                genericUtil.waitUntilClickable(iconOk);
                this.checkOutTotalPrice = labelCheckoutTotalPrice.getText().trim();
                btnContinueShopping.click();
                Thread.sleep(3000);
                log.info("clicked on continue shopping");
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getCartItemTotalPrice() {
        try {
            genericUtil.jsScrollUp();
//            driver.navigate().refresh();
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("window.scrollBy(0,-350)");
//            genericUtil.waitUntilElementVisible(viewMyCart);
            genericUtil.moveMouseToElement(viewMyCart);
            Thread.sleep(4000);
            this.cartTotalPrice = labelCartTotalPrice.getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartTotalPrice;
    }

    public boolean signUpForNewUser(String un, String pwd) {
        genericUtil.waitUntilElementVisible(btnSigIn);
        btnSigIn.click();
        genericUtil.waitUntilElementVisible(inputEmailCreate);
        inputEmailCreate.sendKeys(un);
        btnCreateAccount.click();
        genericUtil.waitUntilElementVisible(inputFirstName);
        radioMr.click();
        inputFirstName.sendKeys("Test");
        inputLastName.sendKeys("User");
        inputPassword.sendKeys(pwd);
        inputAddress.sendKeys("61 Robinson Road");
        inputCity.sendKeys("New York");
        genericUtil.selectFromList(selectState, "New York");
        inputPostcode.sendKeys("10012");
        inputMobilePhone.sendKeys("212098765490");
        btnRegisterAccount.click();
        return genericUtil.verifyElementVisible(labelWelcomeToAccount);
    }

    public boolean purchaseAnItem() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-350)");
        genericUtil.waitUntilElementVisible(viewMyCart);
        genericUtil.moveMouseToElement(viewMyCart);
        btnCartCheckOut.click();
//        genericUtil.waitUntilElementVisible(btnProceedToCheckOut);
        btnProceedToCheckOut.click();
        genericUtil.waitUntilClickable(btnConfirmAddress);
        btnConfirmAddress.click();
        genericUtil.waitUntilClickable(chkBoxAgreeTndC);
        chkBoxAgreeTndC.click();
        btnConfirmCarrier.click();
        genericUtil.waitUntilClickable(linkPayByBankwire);
        linkPayByBankwire.click();
        genericUtil.waitUntilClickable(btnConfirmOrder);
        btnConfirmOrder.click();
        return genericUtil.verifyElementVisible(labelOrderConfirmed);

    }


}
