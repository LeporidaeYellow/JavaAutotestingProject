package steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;

public class AllureSteps {
    @Attachment(value = "Screenshot", type = "image/png")
    @Step("Capture screenshot")
    public byte[] captureScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    @Step("Capture screenshot with Selenide")
    public byte[] captureScreenshotSelenide() throws IOException {
        return com.google.common.io.Files.toByteArray(takeScreenShotAsFile());
    }

    @Step("Capture screenshot (spoiler)")
    public void captureScreenshotSpoiler(WebDriver driver) {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Step("Capture screenshot (extension)")
    public void captureScreenshotSpoiler() {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) BaseSteps.getDriver()).getScreenshotAs(OutputType.BYTES)));
    }

    @Step("Capture screenshot with Selenide (extension)")
    public void captureScreenshotSelenideSpoiler() throws IOException {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(com.google.common.io.Files.toByteArray(takeScreenShotAsFile())));
    }

    @Step("Download file: {destination}")
    public void download(String link, File destination) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpUriRequestBase request = new HttpGet(link);
            client.execute(request, (HttpClientResponseHandler<byte[]>) response -> {
                Files.write(destination.toPath(), response.getEntity().getContent().readAllBytes());
                Allure.addAttachment(destination.getName(), Files.newInputStream(destination.toPath()));
                return null;
            });
        }
    }
}
