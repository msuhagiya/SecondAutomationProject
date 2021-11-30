package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiTest {
    static WebDriver driver;

    public static void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static String currentTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhmmssa");
        return sdf.format(date);
    }

    public static void waitForClickable(By by, int timeInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForVisible(By by, int timeForSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeForSecond);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @BeforeMethod
    public void openBrowser() {
        System.out.println(currentTimeStamp());
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        //Open the URL
        driver.get("https://demo.nopcommerce.com/");
        //Maximize the screen
        driver.manage().window().maximize();
    }

    @Test
    public void toVerifyUserShouldBeAbleToRegisterSuccessfully()
    {
        //Click on register button
        clickOnElement((By.linkText("Register")));

        //print the "Your Personal Detail"
        String personalDetailHeading= getTextFromElement(By.xpath("//form/div[1]/div[@class=\"title\"]/strong"));
        System.out.println(personalDetailHeading);

        //enter the first name
        typeText(By.id("FirstName"), "Navya");

        //enter the lastname
        typeText(By.id("LastName"), "Patel");

        //Enter day of birth
        Select selectDay = new Select(driver.findElement(By.name("DateOfBirthDay")));
        selectDay.selectByVisibleText("21");
        //Enter month of birth
        Select selectMonth = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        selectMonth.selectByValue("6");
        //Enter year of birth
        Select selectYear = new Select(driver.findElement(By.name("DateOfBirthYear")));
        selectYear.selectByValue("2021");

        //Enter email address
        typeText(By.id("Email"), "def+" + currentTimeStamp() + "@gmail.com");

        //Enter password
        typeText(By.id("Password"), "abc123");

        //Confirm password
        typeText(By.id("ConfirmPassword"), "abc123");

        //click on Register
        waitForClickable(By.id("register-button"), 20);
        clickOnElement(By.id("register-button"));

        //store actual result
        String actualRegisterSuccessMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        //Store expected result
        String expectedRegisterSuccessMessage ="Your registration completed";
        //Compare actual result and expected result
        Assert.assertEquals(actualRegisterSuccessMessage,expectedRegisterSuccessMessage,"Register success is incorrect");

    }
    @Test
    public void toVerifyUserShouldBeAbleToNavigateToDesktopPage()
    {
        //Click on category Computer
        clickOnElement(By.linkText("Computers"));

        //Click on sub category Desktops
        clickOnElement((By.linkText("Desktops")));

        waitForVisible(By.linkText("Desktops"),30);
        //Check the url for word "desktops"
        Assert.assertTrue(driver.getCurrentUrl().contains("desktops"));
    }
    @Test
    public void toVerifyRegisterUserShouldBeAbleToReferAProductToFriend()
    {
        //User should be able to register successfully method called
        toVerifyUserShouldBeAbleToRegisterSuccessfully();
        //Click on computer
        clickOnElement(By.linkText("Computers"));
        //Click on Desktop
        clickOnElement(By.linkText("Desktops"));
        //Click on build your own computer
        clickOnElement(By.linkText("Build your own computer"));
        //Click on Email a friend
        clickOnElement(By.xpath("//form[@id='product-details-form']/div[2]/div/div[2]/div[10]/div[3]/button"));
        //Type friend email address
        typeText(By.id("FriendEmail"),"xyz@gmail.com");
        //Type personal message
        typeText(By.id("PersonalMessage"),"This product is very good");
        //Click on send email
        clickOnElement(By.xpath("//div[@class='buttons']/button"));
        //Confirmation message after sending email
        String actualMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        //Expected message result
        String expectedMessage = "Your message has been sent.";
        //Comparing actual and expected send email message
        Assert.assertEquals(actualMessage,expectedMessage,"Your message has not been sent.");
    }
    @Test
   public void toVerifyUserShouldBeAbleToPostNewCommentOnNewsDetailPage()
    {
        //Click on News
        clickOnElement(By.linkText("News"));
        //Click on details
        clickOnElement(By.linkText("DETAILS"));
        //Type the Title
        typeText(By.id("AddNewComment_CommentTitle"),"New");
        //Type comment
        typeText(By.id("AddNewComment_CommentText"),"This is new comment");
        //Click on new comment
        clickOnElement(By.xpath("//div[@class='buttons']/button"));
        //Confirming message after adding new comment
        String actualText = driver.findElement(By.xpath("//div[@class='notifications']/div")).getText();
        //Expected message result
        String expectedText = "News comment is successfully added.";
        //Comparing message News comment is successfully added with actual message.
        Assert.assertEquals(actualText,expectedText,"News comment has not been added");

    }
    @AfterMethod
   public void closeBrowser()
    {
       driver.close();
    }
}