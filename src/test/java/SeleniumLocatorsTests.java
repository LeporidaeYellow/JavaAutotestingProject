import local.project.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumLocatorsTests {
    WebDriver driver;
    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(Constants.BASE_URL);
    }

    @AfterEach
        void close() {
        driver.close();
    }

    @Test
    void openUrlTest() {
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openWebFormTest() {
        String xpath = "//a[@href = 'loading-images.html']";
        WebElement webFormButton = driver.findElement(By.xpath(xpath));
        webFormButton.click();
        WebElement title = driver.findElement(By.xpath("//h1[@class = 'display-6']"));
        Assertions.assertEquals("Loading images", title.getText());
    }

}