package TestRunner;

import Utils.utils;
import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.UserController;
import pages.UserPage;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserPageRunner extends Setup {
    UserPage user;
    String email, password, phoneNumber;
    private UserController userController;

    @BeforeClass
    public void initUserController() {
        userController = new UserController(prop);
    }

    @BeforeMethod
    public void initPages() {
        user = new UserPage(driver);
    }


     @Test(description = "UserRegister", priority = 1)
    public void userRegistration() throws IOException, InterruptedException, ParseException {
        driver.findElement(By.partialLinkText("Register")).click();
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        email = "afsanasharmilykashpia+" + utils.generateRandomNumber(100, 999) + "@gmail.com";
        password = String.valueOf(utils.generateRandomNumber(10000, 99999));
        phoneNumber = "017" + String.valueOf(utils.generateRandomNumber(10000000, 99999999));
        String address = faker.address().fullAddress();
        user.DoRegister(firstname, lastname, email, password, phoneNumber, address);
        JSONObject usersObj = new JSONObject();
        usersObj.put("Email", email);
        usersObj.put("Password", password);

        utils.saveData("src/test/resources/users.json", usersObj);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Reset it")));
    }

     @Test(description = "Assert congratulations Email", priority = 2)
    public void AssertEmail() throws ConfigurationException, IOException, ParseException, InterruptedException {
        UserModel userModel = new UserModel();
        Thread.sleep(20000);
        Response res = userController.ExtractGmailMessage(userModel);
        String messageActual = res.jsonPath().getString("snippet");
        Assert.assertTrue(messageActual.toLowerCase().contains("welcome to our platform"));
        Thread.sleep(20000);
    }

    @Test(priority = 3, description = "Reset Password Assertion")
    public void ResetpasswordInvalid() throws InterruptedException {
        Thread.sleep(20000);
        driver.findElement(By.partialLinkText("Reset it")).click();
        // Test Case 1: Empty Email Field
        String email = "";
        user.ResetPassword(email);
        WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", emailInput);
        System.out.println("Validation message: " + validationMessage);
        Assert.assertEquals(validationMessage, "Please fill out this field.");

        //Test Case 2: Unregistered Email
        String unregisteredEmail = "notemailid+" + utils.generateRandomNumber(1000, 9999) + "@gmail.com";
        emailInput.clear();
        user.ResetPassword(unregisteredEmail);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));
        Assert.assertTrue(successMessage.getText().contains("Your email is not registered"));
        Thread.sleep(8000);
    }

    @Test(priority = 4)
    public void Resetpasswordvalid() throws IOException, InterruptedException, ParseException {

        driver.get("https://dailyfinance.roadtocareer.net/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement Resetlink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Reset it")));
        Resetlink.click();
        JSONParser parser = new JSONParser();
        JSONArray usersArray = (JSONArray) parser.parse(new FileReader("src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) usersArray.get(usersArray.size() - 1);
        String validemail = userObj.get("Email").toString();
        user.ResetPassword(validemail);
        Thread.sleep(10000);
    }
@Test(priority = 5)
    public void ResetPassAssert() throws IOException, ParseException, InterruptedException {
        JSONParser parser = new JSONParser();
        JSONArray usersArray = (JSONArray) parser.parse(new FileReader("src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) usersArray.get(usersArray.size() - 1);
        String validemail = userObj.get("Email").toString();
        UserModel userModel = new UserModel();
        userModel.setEmail(validemail);
//
        String snippet = null;
        for (int i = 0; i < 20; i++) {
            Response res = userController.ExtractGmailMessage(userModel);
            snippet = res.jsonPath().getString("snippet");

            if (snippet != null && snippet.contains("Click on the following link")) {
                break;
            }
            Thread.sleep(4000);
        }

//        Response res = userController.ExtractGmailMessage(userModel);
//        String snippet = res.jsonPath().getString("snippet");

        System.out.println(snippet);

        Pattern urlPattern = Pattern.compile("https://dailyfinance\\.roadtocareer\\.net/reset-password\\?token=[^\\s]+");
        Matcher matcher = urlPattern.matcher(snippet);
        if (!matcher.find()) {
            throw new RuntimeException("Reset link not found in the email snippet.");
        }
        String resetlink = matcher.group();
        driver.navigate().to(resetlink);
        ;
        String newpass = "newpass1234";
        user.ResetPass(newpass);
        JSONObject usersObj = new JSONObject();
        usersObj.put("Email", email);
        usersObj.put("Password", newpass);
        utils.saveData("src/test/resources/users.json", usersObj);
    }

    @Test(priority = 6)
    public void userlogin() throws IOException, ParseException {
        driver.get("https://dailyfinance.roadtocareer.net/");

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("src/test/resources/users.json"));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        String email = jsonObject.get("Email").toString();
        String password = jsonObject.get("Password").toString();
        user.loginSteps(email, password);
    }

    // @Test(priority = 6)
    public void Additem() throws IOException, ParseException {
//        driver.get("https://dailyfinance.roadtocareer.net/");
//        user.dologin();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("add-cost-button")));
        addButton.click();
        String item1 = "Shoes";
        String item2 = "Bag";

        //Add item (mandatory fields)
        user.additemMandatory(item1, "2");
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();
        alert.accept();

        //Add item (all fields)
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement addButton2 = wait2.until(ExpectedConditions.elementToBeClickable(By.className("add-cost-button")));
        addButton2.click();
        user.additem(item2, "3", "This is a  Remarks textbox.");
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait3.until(ExpectedConditions.alertIsPresent());
        Alert alert2 = driver.switchTo().alert();
        alert2.accept();

        //Assertion
        List<WebElement> tableRows = driver.findElements(By.xpath("//table//tbody//tr"));

        boolean item1Found = false;
        boolean item2Found = false;

        for (WebElement row : tableRows) {
            String rowText = row.getText();
            if (rowText.contains(item1)) {
                item1Found = true;
            }
            if (rowText.contains(item2)) {
                item2Found = true;
            }
        }

        Assert.assertTrue(item1Found, "Item not found on the item list.");
        Assert.assertTrue(item2Found, "Item not found on the item list.");

    }

     @Test(priority = 7)
    public void updateGmail() throws IOException, ParseException, InterruptedException {
       // driver.get("https://dailyfinance.roadtocareer.net/");
//        user.loginSteps();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='button']"))).click();
         WebElement menubar = driver.findElement(By.cssSelector("[role='menuitem']"));
         menubar.click();
         WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
         wait1.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h4")));
         //WebElement firstMenuItem = menubar.get(0);
//         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menubar);
//         wait.until(ExpectedConditions.elementToBeClickable(menubar)).click();
        email = "afsanasharmilkashpia+" + utils.generateRandomNumber(100, 999) + "@gmail.com";
        password = "newpass1234";
        Thread.sleep(10000);
        user.updateGmail(email);
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        JSONObject usersObj = new JSONObject();
        usersObj.put("Email", email);
        usersObj.put("Password", password);
        utils.saveData("src/test/resources/registertedUsers.json", usersObj);
        Thread.sleep(5000);
    }

     @Test(priority = 8)
    public void LoginAssertionInvalid() throws ParseException, IOException, InterruptedException {
        driver.get("https://dailyfinance.roadtocareer.net/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("home-title")));

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("src/test/resources/users.json"));
        JSONObject invalidUser = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        String email = invalidUser.get("Email").toString();
        String password = invalidUser.get("Password").toString();
        Thread.sleep(10000);
        user.loginSteps(email, password);
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));
        String Message = driver.findElement(By.tagName("p")).getText();
        Thread.sleep(4000);
        Assert.assertTrue(Message.contains("Invalid email"));
    }

     @Test(priority = 9)   //Assert with new email
    public void loginAssertionValid() throws IOException, ParseException {
        driver.get("https://dailyfinance.roadtocareer.net/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("home-title")));
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray2 = (JSONArray) jsonParser.parse(new FileReader("src/test/resources/registertedUsers.json"));
        JSONObject validUser = (JSONObject) jsonArray2.get(jsonArray2.size() - 1);
        String validmail = validUser.get("Email").toString();
        String validPassword = validUser.get("Password").toString();
        user.loginSteps(validmail, validPassword);
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement dashboard = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-cost-button")));
        Assert.assertEquals("Add Cost", dashboard.getText());
    }

}





