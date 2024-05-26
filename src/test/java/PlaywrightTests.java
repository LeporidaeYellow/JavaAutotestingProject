import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.AllureSteps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("playwright")
class PlaywrightTests {
    AllureSteps allureSteps = new AllureSteps();

    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Description("Successful Login")
    @Test
    void successfulLoginTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        Locator subTitle = page.locator(".display-6");
        Locator loginInput = page.locator("#username");
        Locator passwordInput = page.locator("#password");
        Locator submitButton = page.locator("xpath=//button[@type='submit']");

        loginInput.fill("user");
        passwordInput.fill("user");
        String textBeforeClick = subTitle.innerText();
        submitButton.click();

        assertThat(textBeforeClick).isEqualTo("Login form");
        Locator successMessage = page.locator("#success");
        assertThat(successMessage.isVisible()).isTrue();
    }

    @Description("Upload file")
    @Test
    void fileInputTest() throws IOException {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        String str = "Test";
        String fileName = "testFile.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(str);
        writer.close();

        File file = new File(fileName);
        String path = String.valueOf(file.getAbsoluteFile());

        page.setInputFiles("//input[@type='file']", Paths.get(path));

        Locator submit = page.locator("xpath=//button[text() = 'Submit']");
        submit.click();

        assertThat(page.url().contains(fileName));
    }


    @Description("Check Checkbox")
    @Test
    void checkCheckboxTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        Locator checkbox = page.locator("input[Id = 'my-check-1']");
        checkbox.click();
        assertTrue(checkbox.getAttribute("checked").equals(""));

        Locator submit = page.locator("xpath=//button[text() = 'Submit']");
        submit.click();

        assertThat(page.url().contains("my-check"));
    }

    @Description("Default Checkbox")
    @Test
    void defaultCheckboxTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        Locator checkbox = page.locator("input[Id = 'my-check-2']");
        checkbox.click();

        assertTrue(checkbox.getAttribute("checked") == null);

        Locator submit = page.locator("xpath=//button[text() = 'Submit']");
        submit.click();

        assertThat(page.url().contains("my-check=on&my-check=on"));
    }

    @Description("Date Picker")
    @Test
    void datePickerTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        String targetMonth = "June 2025";
        String expectDate = "06/10/2025";

        Locator datePicker = page.locator("body > main > div > form > div > div:nth-child(3) > label:nth-child(2) > input");
        datePicker.click();

        Locator button = page.locator("body > div > div.datepicker-days > table > thead > tr:nth-child(2) > th.next");
        while (true) {
            String monthText = page.locator("body > div > div.datepicker-days > table > thead > tr:nth-child(2) > th.datepicker-switch").innerText();
            if (monthText.equals(targetMonth)) {
                break;
            }
            button.click();
        }

        Locator dayButton = page.locator("xpath=/html/body/div/div[1]/table/tbody/tr[3]/td[3]");
        dayButton.click();

        Locator submit = page.locator("xpath=//button[text() = 'Submit']");
        submit.click();

        assertThat(page.url().contains(expectDate));
    }

    @Description("Example Range")
    @Test
    void exampleRangeTest() {
        String expectRange = "my-range=9";
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        Locator exampleRange = page.locator("input.form-range");
        exampleRange.press("ArrowRight");
        exampleRange.press("ArrowRight");
        exampleRange.press("ArrowRight");
        exampleRange.press("ArrowRight");

        Locator submit = page.locator("xpath=//button[text() = 'Submit']");
        submit.click();

        assertThat(page.url().contains(expectRange));
    }

    @Description("Simple Open Site")
    @Test
    void openSiteTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/");
        assertEquals("Hands-On Selenium WebDriver with Java", page.title());
    }

    @Description("Simple Open Form")
    @Test
    void openForm() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/");
        Locator webFormButton = page.locator("xpath=//div[@class = 'card-body']")
                .locator("xpath=.//a[contains(@class, 'btn')]")
                .first();
        webFormButton.click();
        Locator actualH1 = page.locator("css=.display-6");
        assertEquals("Web form", actualH1.innerText());
    }

    @Description("Loading Images Default Wait")
    @Test
    void loadingImagesDefaultWaitTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        Locator image = page.locator("#landscape");

        assertThat(image.getAttribute("src")).contains("landscape");
    }

    @Description("loading Images With Explicit Timeout Wait")
    @Test
    void loadingImagesWithExplicitTimeoutWaitTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        ElementHandle image = page.waitForSelector("#landscape", new Page.WaitForSelectorOptions().setTimeout(25_000));

        assertThat(image.getAttribute("src")).contains("landscape");
    }

    @Description("Loading Images With Custom Timeout")
    @Test
    void loadingImagesWithCustomTimeoutWaitTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        int expectedCount = 4;
        Locator images = page.locator("img");
        while (images.count() != expectedCount) {
            page.waitForTimeout(1000);
        }

        PlaywrightAssertions.assertThat(images).hasCount(expectedCount);
    }


}
