import com.codeborne.selenide.Configuration;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import org.openqa.selenium.chrome.ChromeOptions;

import pages.selenide.DownloadFilesPage;
import pages.selenide.LoginPage;
import pages.selenide.WebFormPage;
import pages.selenide.HomePage;

import steps.AllureSteps;

import java.io.IOException;
import static com.codeborne.selenide.WebDriverRunner.url;

@Tag("selenide")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SelenidePageObjectTests {
    AllureSteps allureSteps = new AllureSteps();

    @BeforeAll
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless"); // without browser interface
        Configuration.browserCapabilities = options;
    }

    @Step("PageObject: Web form")
    @Test
    void pageObjectTest() {
        HomePage homePage = new HomePage();
        homePage.open();
        WebFormPage webFormPage = homePage.openWebForm();
        webFormPage.submit();
        org.assertj.core.api.Assertions.assertThat(url()).contains("https://bonigarcia.dev/selenium-webdriver-java/submitted-form.html");
    }


    @Step("PageObject: Web login")
    @Test
    void pageObjectLoginPageTest() {
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.loginFormPage();
        Assertions.assertTrue(loginPage.submit("user", "user"));
    }


    @Step("Web driver logo")
    @Test
    void pageObjectDownloadManagerLogo() throws InterruptedException, IOException {
        HomePage homePage = new HomePage();
        homePage.open();
        DownloadFilesPage downloadFilesPage = homePage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.selenideManagerLogo());
    }

    @Step("Web driver Doc")
    @Test
    void pageObjectDownloadManagerDoc() throws InterruptedException, IOException {
        HomePage homePage = new HomePage();
        homePage.open();
        DownloadFilesPage downloadFilesPage = homePage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.selenideManagerDoc());
    }

    @Step("Selenium Jupiter logo")
    @Test
    void pageObjectDownloadSeleniumLogo() throws InterruptedException, IOException {
        HomePage homePage = new HomePage();
        homePage.open();
        DownloadFilesPage downloadFilesPage = homePage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.selenideJupiterLogo());
    }

    @Step("Selenium Jupiter Doc")
    @Test
    void pageObjectDownloadSeleniumDoc() throws InterruptedException, IOException {
        HomePage homePage = new HomePage();
        homePage.open();
        DownloadFilesPage downloadFilesPage = homePage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.selenideJupiterDoc());
    }
}
