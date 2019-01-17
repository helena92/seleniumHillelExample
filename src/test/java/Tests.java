import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class Tests {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test(priority = 1)
    public void testFindingTeam() {
        driver.get("https://slack.com");
        WebElement signInLink = driver.findElement(By.linkText("Sign in"));
        signInLink.click();
        WebElement teamName = driver.findElement(By.name("domain"));
        teamName.sendKeys("hilleltestteam");
        teamName.submit();
        List<WebElement> signInHeaders = driver.findElements(By.id("signin_header"));
        Assert.assertTrue(signInHeaders.size() != 0);
    }

//    @Test(priority = 2)
//    public void testFailureLogin() {
//        WebElement emailField = driver.findElement(By.id("email"));
//        emailField.clear();
//        emailField.sendKeys("elena.chornobai@gmail.com");
//        WebElement pwdField = driver.findElement(By.id("password"));
//        pwdField.clear();
//        pwdField.sendKeys("test");
//        pwdField.submit();
//        WebElement error = driver.findElement(By.cssSelector("p.alert.alert_error"));
//        Assert.assertEquals(error.getText(), "Sorry, you entered an incorrect email address or password.");
//    }

    @Test(priority = 3)
    public void testSuccessLogin() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys("elena.chornobai@gmail.com");
        WebElement pwdField = driver.findElement(By.id("password"));
        pwdField.clear();
        pwdField.sendKeys("hillelproject123");
        pwdField.submit();
        WebElement userName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("team_menu_user_name")));
        Assert.assertEquals(userName.getText(), "elena");
    }

    @Test(priority = 4, dependsOnMethods = {"testSuccessLogin"})
    public void testOpenAdminSection() {
        WebElement teamMenu = driver.findElement(By.id("team_menu"));
        teamMenu.click();
        Actions builder = new Actions(driver);
        WebElement adminSection = driver.findElement(By.linkText("Administration"));
        builder.moveToElement(adminSection).build().perform();
        WebElement manageTeam = driver.findElement(By.cssSelector("#manage_team>a"));
        manageTeam.click();
        Set<String> handles = driver.getWindowHandles();
        handles.remove(driver.getWindowHandle());
        driver.switchTo().window(handles.iterator().next());
        Assert.assertEquals(driver.getTitle(), "Team Admin | hilleltestteam Slack");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
