import extensions.AllureExtension;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.DownloadFilesPage;
import pages.MainPage;

import java.io.IOException;

@Feature("Allure report with upload")
@ExtendWith(AllureExtension.class)
@Story("Download")
class SeleniumPageObjectsDownloadFilesTests {

    MainPage mainPage;

    @BeforeEach
    void setup() {
        mainPage = new MainPage("chrome");
        WebDriver webDriver = BasePage.getDriver();
        webDriver.manage().window().maximize();
    }

    @AfterEach
    void teardown() {
        mainPage.quit();
    }


    @Step("Web driver logo")
    @Test
    void webDriverManagerLogo() throws InterruptedException, IOException {
        DownloadFilesPage downloadFilesPage = mainPage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.webDriverManagerLogo());
    }

    @Step("Web driver doc")
    @Test
    void webDriverManagerDoc() throws InterruptedException, IOException {
        DownloadFilesPage downloadFilesPage = mainPage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.webDriverManagerDoc());
    }

    @Step("Selenium Jupiter logo")
    @Test
    void seleniumJupiterLogo() throws InterruptedException, IOException {
        DownloadFilesPage downloadFilesPage = mainPage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.seleniumJupiterLogo());
    }

    @Step("Selenium Jupiter doc")
    @Test
    void seleniumJupiterDoc() throws InterruptedException, IOException {
        DownloadFilesPage downloadFilesPage = mainPage.downloadFilesPage();
        Assertions.assertTrue(downloadFilesPage.seleniumJupiterDoc());
    }
}
