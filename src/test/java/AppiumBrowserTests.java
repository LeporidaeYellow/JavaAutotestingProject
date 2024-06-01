import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppiumBrowserTests {
    //Запуск: appium --allow-insecure chromedriver_autodownload
    private static final String SERVER = "http://127.0.0.1:4723/";
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    private AndroidDriver driver;

    @BeforeEach
    void setup() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options
                .setPlatformName("Android")
                .setPlatformVersion("9.0")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .noReset()
                .withBrowserName("Chrome");

        driver = new AndroidDriver(new URL(SERVER), options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Description("Check Web Form")
    @Test
    void webFormTest() {
        String item = "Web form";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.linkText(item))).click();

        WebElement title =  wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//h1[@class = 'display-6']")));
        assertThat(title.getText()).isEqualTo(item);
    }

    @Description("Check Login Form")
    @Test
    void successfulLoginTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//h1[@class = 'display-6']")));

        WebElement subTitle = driver.findElement(AppiumBy.className("display-6"));
        WebElement loginInput = driver.findElement(AppiumBy.id("username"));
        WebElement passwordInput = driver.findElement(AppiumBy.id("password"));
        WebElement submitButton = driver.findElement(AppiumBy.xpath("//button[@type='submit']"));

        loginInput.sendKeys("user");
        passwordInput.sendKeys("user");
        String textBeforeClick = subTitle.getText();
        submitButton.click();

        assertThat(textBeforeClick).isEqualTo("Login form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("success")));

        WebElement successMessage = driver.findElement(AppiumBy.id("success"));
        assertThat(successMessage.isDisplayed()).isTrue();
    }

    @Description("Check Slow Calculator")
    @Test
    void checkCalcSum() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//h1[@class = 'display-6']")));

        //locators
        var oneButtonLocator = AppiumBy.xpath("//div[@class='keys']/span[text()='1']");
        var plusButtonLocator = AppiumBy.xpath("//div[@class='keys']/span[text()='+']");
        var equalButtonLocator = AppiumBy.xpath("//div[@class='keys']/span[text()='=']");
        var resultField = AppiumBy.xpath("//div[@class='screen']");

        //actions
        driver.findElement(oneButtonLocator).click();
        driver.findElement(plusButtonLocator).click();
        driver.findElement(oneButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        //assertions
        wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.id("spinner")));
        assertEquals("2", driver.findElement(resultField).getText());
    }
}
