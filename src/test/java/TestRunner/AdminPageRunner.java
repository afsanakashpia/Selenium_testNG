package TestRunner;

import config.RegisterDataSet;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.UserPage;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AdminPageRunner extends Setup {
    UserPage user;

    @BeforeMethod
    public void initPages() {
        user = new UserPage(driver);
    }

    @Test(priority = 1)
    public void AdminLogin() {
        driver.get("https://dailyfinance.roadtocareer.net/");
        user.loginSteps(System.getProperty("email"), System.getProperty("password"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Dashboard' and contains(@class, 'MuiTypography-h6')]")));
        Assert.assertEquals("Dashboard", dashboard.getText());

    }

    @Test(priority = 2)
    public void SearchGmail() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("src/test/resources/registertedUsers.json"));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        String email = jsonObject.get("Email").toString();
        WebElement searchinputfield = driver.findElement(By.cssSelector("input[type='text']"));
        searchinputfield.sendKeys(email);
        List<WebElement> tableRows = driver.findElements(By.xpath("//table//tbody//tr"));

        boolean RegisteredEmail = false;

        for (WebElement row : tableRows) {
            String rowText = row.getText();
            if (rowText.contains(email)) {
                RegisteredEmail = true;
            }
        }
        Assert.assertTrue(RegisteredEmail, "Registered Email not found on admin dashboard");
    }

    @Test(dataProvider = "RegisterCSVData", dataProviderClass = RegisterDataSet.class)
    public void RegisterCSVuser(String firstname, String lastname, String email, String password, String phoneNumber, String address) {
        driver.findElement(By.partialLinkText("Register")).click();
        user.DoRegister(firstname, lastname, email, password, phoneNumber, address);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

    }

    @Test
    public void getusers() throws IOException, InterruptedException {
        driver.get("https://dailyfinance.roadtocareer.net/");
        user.loginSteps(System.getProperty("email"), System.getProperty("password"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
        Thread.sleep(6000);
        //WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> Rows = driver.findElements(By.xpath("//table//tr"));
        System.out.println("Table found. Rows count: " + Rows.size());

        for (WebElement row : Rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                String cellText = cell.getText().trim();
                if (cellText.equalsIgnoreCase("view")) {
                    continue;
                }
                System.out.print(cellText + "  ");
            }
            System.out.println();
        }

    }
}
