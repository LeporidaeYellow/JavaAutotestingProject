package pages.selenide;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.AllureSteps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.codeborne.selenide.Selenide.$;

public class DownloadFilesPage {
    private final String DOWNLOAD_LOCATION = "./";

    AllureSteps allureSteps = new AllureSteps();

    public boolean selenideManagerLogo() throws InterruptedException, IOException {
        return downloadFile("webdrivermanager", ".png", $(By.linkText("WebDriverManager logo")));
    }

    public boolean selenideManagerDoc() throws InterruptedException, IOException {
        return downloadFile("webdrivermanager", ".pdf", $(By.linkText("WebDriverManager doc")));
    }

    public boolean selenideJupiterLogo() throws InterruptedException, IOException {
        return downloadFile("selenium-jupiter", ".png", $(By.linkText("Selenium-Jupiter logo")));
    }

    public boolean selenideJupiterDoc() throws IOException, InterruptedException {
        return downloadFile("selenium-jupiter", ".pdf", $(By.linkText("Selenium-Jupiter doc")));
    }

    private boolean downloadFile(String nameFile, String typeFile, WebElement webElement) throws InterruptedException, IOException, IOException {
        File file = new File(DOWNLOAD_LOCATION, nameFile + typeFile);
        if (file.exists()) Files.delete(Path.of(file.getPath()));
        allureSteps.download(webElement.getAttribute("href"), file);
        return file.exists();
    }


}
