import local.project.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SeleniumBrowserActionsTests {
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

    //TODO: Inﬁnite scroll
    @Test
    void infiniteScrollTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");

        WebElement footer = driver.findElement(By.tagName("footer"));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(footer);
        new Actions(driver)
                .scrollFromOrigin(scrollOrigin, 0, 0)
                .perform();

        Thread.sleep(1000);

        List<WebElement> list = driver.findElements(By.cssSelector("#content > p > b"));
        Assertions.assertTrue(list.size() > 1);
    }

    //TODO: Dialog boxes
    @Test
    void dialogBoxesTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");

        driver.findElement(By.id("my-alert")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("Hello world!");
        alert.accept();

        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().accept();
        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: true");
        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().dismiss();
        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: false");

        driver.findElement(By.id("my-prompt")).click();
        driver.switchTo().alert().sendKeys("dialogBoxesTest");
        driver.switchTo().alert().accept();
        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: dialogBoxesTest");

        driver.findElement(By.id("my-modal")).click();
        WebElement modelButton = driver.findElement(By.xpath("//button[normalize-space() = 'Save changes']"));
        wait.until(ExpectedConditions.elementToBeClickable(modelButton));
        modelButton.click();
    }

    //TODO: Cookies
    @Test
    void cookieTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();

        assertThat(cookies).hasSize(2);

        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        assertThat(username.getPath()).isEqualTo("/");

        driver.findElement(By.id("refresh-cookies")).click();

        Cookie ownCookie = new Cookie("own-cookie-key", "own-cookie-value");
        options.addCookie(ownCookie);
        String readValue = options.getCookieNamed(ownCookie.getName()).getValue();

        cookies = options.getCookies();

        assertThat(ownCookie.getValue()).isEqualTo(readValue);
        assertThat(cookies).hasSize(3);

        driver.findElement(By.id("refresh-cookies")).click();

        options.deleteCookie(username);
        options.deleteCookie(ownCookie);

        assertThat(options.getCookies()).hasSize(cookies.size() - 2);

        driver.findElement(By.id("refresh-cookies")).click();
    }

    //TODO: IFrames
    @Test
    void iframeTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");

        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("lead")));

        WebElement iframeElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iframeElement);

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("display-6")));
        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum");

        driver.switchTo().defaultContent();

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("lead")));
        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");
    }

    //TODO: Web storage
    @Test
    void testWebStorage() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");
        WebStorage webStorage = (WebStorage) driver;

        LocalStorage localStorage = webStorage.getLocalStorage();
        System.out.printf("Local storage elements: {%s}\n", localStorage.size());

        SessionStorage sessionStorage = webStorage.getSessionStorage();
        sessionStorage.keySet()
                .forEach(key -> System.out.printf("Session storage: {%s}={%s}\n", key, sessionStorage.getItem(key)));
        assertThat(sessionStorage.size()).isEqualTo(2);

        sessionStorage.setItem("new element", "new value");
        assertThat(sessionStorage.size()).isEqualTo(3);

        driver.findElement(By.id("display-session")).click();
    }

    //TODO: Shadow DOM
    @Test
    void testShadowDom() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html");

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("p")));

        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));

        assertThat(textElement.getText()).contains("Hello Shadow DOM");
    }
}