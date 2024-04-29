package pages;

import components.FooterComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import steps.AllureSteps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadFilesPage extends BasePage {

    @FindBy(linkText = "WebDriverManager logo")
    @CacheLookup
    WebElement webDriverManagerLogo;

    @FindBy(linkText = "WebDriverManager doc")
    @CacheLookup
    WebElement webDriverManagerDoc;

    @FindBy(linkText = "Selenium-Jupiter logo")
    @CacheLookup
    WebElement SeleniumJupiterLogo;


    @FindBy(linkText = "Selenium-Jupiter doc")
    @CacheLookup
    WebElement SeleniumJupiterDoc;

    private final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/download.html";
    private final String DOWNLOAD_LOCATION = "./";

    AllureSteps allureSteps = new AllureSteps();
    FooterComponent header;
    FooterComponent footer;

    public DownloadFilesPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        header = new FooterComponent(driver);
        visit(BASE_URL);
    }

    public DownloadFilesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        header = new FooterComponent(driver);
        footer = new FooterComponent(driver);
    }

    public boolean webDriverManagerLogo() throws IOException, InterruptedException {
        return downloadFile("webdrivermanager", ".png", webDriverManagerLogo);
    }

    public boolean webDriverManagerDoc() throws IOException, InterruptedException {
        return downloadFile("webdrivermanager", ".pdf", webDriverManagerDoc);
    }

    public boolean seleniumJupiterLogo() throws IOException, InterruptedException {
        return downloadFile("selenium-jupiter", ".png", SeleniumJupiterLogo);
    }

    public boolean seleniumJupiterDoc() throws IOException, InterruptedException {
        return downloadFile("selenium-jupiter", ".pdf", SeleniumJupiterDoc);
    }

    private boolean downloadFile(String nameFile, String typeFile, WebElement webDriver) throws InterruptedException, IOException {
        File file = new File(DOWNLOAD_LOCATION, nameFile + typeFile);
        if (file.exists()) Files.delete(Path.of(file.getPath()));
        allureSteps.download(webDriver.getAttribute("href"), file);
        return file.exists();
    }
}
