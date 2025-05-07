package pages;

import config.Setup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class UserPage extends Setup {

    @FindBy(tagName = "input")
    List<WebElement> txtFields;

    @FindBy(id = "register")
    WebElement registbtn;

    @FindBy(tagName = "input")
    WebElement ResetEmailInput;

    @FindBy(tagName = "input")
    List<WebElement> resetEmailfield;


    public UserPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void DoRegister(String firstname, String lastname, String email, String password, String phoneNumber, String address) {
        txtFields.get(0).sendKeys(firstname);
        txtFields.get(1).sendKeys(lastname);
        txtFields.get(2).sendKeys(email);
        txtFields.get(3).sendKeys(password);
        txtFields.get(4).sendKeys(phoneNumber);
        txtFields.get(5).sendKeys(address);
        txtFields.get(6).click();
        txtFields.get(8).click();
        registbtn.click();
    }

    public void ResetPassword(String email) throws InterruptedException {

        ResetEmailInput.sendKeys(email);
        driver.findElement(By.cssSelector("[type=submit]")).click();
        Thread.sleep(20000);
    }

    public void ResetPass(String password) throws InterruptedException {

        resetEmailfield.get(0).sendKeys(password);
        resetEmailfield.get(1).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

    }

    public void loginSteps(String email, String Password) {
        WebElement loginemailfield = driver.findElement(By.id("email"));
        WebElement loginpassfield = driver.findElement(By.id("password"));
        loginemailfield.clear();
        loginemailfield.sendKeys(email);
        loginpassfield.clear();
        loginpassfield.sendKeys(Password);
        driver.findElement(By.tagName("button")).click();
    }

    public void additemMandatory(String item, String amount) {
        // WebElement itemields = driver.findElement(By.id("itemName"));
        driver.findElement(By.id("itemName")).sendKeys(item);
        driver.findElement(By.id("amount")).sendKeys(amount);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

    }

    public void additem(String item, String amount, String remarkText) {
        driver.findElement(By.id("itemName")).sendKeys(item);
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        buttons.get(2).click();
        driver.findElement(By.id("amount")).sendKeys(amount);
        WebElement date = driver.findElement(By.id("purchaseDate"));
        date.sendKeys(Keys.BACK_SPACE);
        date.sendKeys("12");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        Select month = new Select(driver.findElement(By.id("month")));
        month.selectByVisibleText("June");
        driver.findElement(By.id("remarks")).sendKeys(remarkText);
        buttons.get(3).click();

    }

    public void updateGmail(String email) throws InterruptedException {
        Thread.sleep(8000);
////        driver.findElement(By.tagName("path")).click();
//        List<WebElement> menubar = driver.findElements(By.cssSelector("[role=menuitem]"));
//        menubar.get(0).click();
//        Thread.sleep(6000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        driver.findElement(By.xpath("//button[text()='Edit']")).click();
        Thread.sleep(9000);
        WebElement emailfield = driver.findElement(By.name("email"));
        emailfield.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        emailfield.sendKeys(email);
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Update']"));
        submitButton.click();
    }
//    public void updateGmail(String email) {
//
//        // Scroll and click Edit
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
//
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Edit']"))).click();
//
//        // Clear and update email
//        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
//        emailField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
//        emailField.sendKeys(email);
//
//        // Click update button
//        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Update']"));
//        submitButton.click();
//    }


}
