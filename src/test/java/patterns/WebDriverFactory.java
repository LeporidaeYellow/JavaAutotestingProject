package patterns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    public static WebDriver createWebDriver(String browser) {
//        System.setProperty("webdriver.chrome.driver", "tools/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=4000,2000");
        WebDriver chrome = new ChromeDriver(chromeOptions);

        WebDriver driver = switch (browser.toLowerCase()) {
            case "chrome" -> chrome;
            case "edge" -> new EdgeDriver();
            case "firefox","mozilla" -> new FirefoxDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        return driver;
    }
}