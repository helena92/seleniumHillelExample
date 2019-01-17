import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/elena/Git/selenium_example_project/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void testFindingTeam() {
        driver.get("https://slack.com");
        WebElement signInLink = driver.findElement(By.linkText("Sign in"));
        signInLink.click();
        WebElement teamName = driver.findElement(By.name("domain"));
        teamName.sendKeys("hillelnovember2018");
        teamName.submit();
        List<WebElement> signInHeaders = driver.findElements(By.id("signin_header"));
        Assert.assertTrue(signInHeaders.size() != 0);
    }

    @Test(priority = 2)
    public void testFailureLogin() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("echornobai@intersog.com");
        WebElement pwdField = driver.findElement(By.id("password"));
        pwdField.sendKeys("test");
        pwdField.submit();
        WebElement error = driver.findElement(By.cssSelector("p.alert.alert_error"));
        Assert.assertEquals(error.getText(), "Sorry, you entered an incorrect email address or password.");
    }

    @Test(priority = 3)
    public void testSuccessLogin() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys("echornobai@intersog.com");
        WebElement pwdField = driver.findElement(By.id("password"));
        pwdField.clear();
        pwdField.sendKeys("test12345678");
        pwdField.submit();
        WebElement userName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("team_menu_user_name")));
        Assert.assertEquals(userName.getText(), "Elena");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
