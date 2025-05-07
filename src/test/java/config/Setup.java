package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Setup {
    public WebDriver driver;
    public Properties prop;

    @BeforeTest
    public void setup() throws IOException {
        prop = new Properties();
        FileInputStream fs = new FileInputStream("src/test/resources/config.properties");
        prop.load(fs);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://dailyfinance.roadtocareer.net/");
    }
    @AfterTest
    public void teardown() {
        driver.quit();

    }
}
