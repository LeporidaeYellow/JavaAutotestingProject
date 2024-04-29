import extensions.AllureExtension;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.WebFormPage;
import pages.MainPage;
import steps.AllureSteps;
import steps.BaseSteps;

import java.io.IOException;

@Feature("Allure report")
@ExtendWith(AllureExtension.class)
@Description("Site")
@Story("Web Form")
class SeleniumPageObjectsWebFormTests extends BaseSteps {
    AllureSteps allureSteps = new AllureSteps();
    MainPage mainPage;

    @BeforeEach
    void setup() {
        mainPage = new MainPage("chrome");
    }

    @AfterEach
    void teardown() {
        mainPage.quit();
    }

    @Step("Login Success")
    @Test
    void inputTextInputTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.inputTextInput("testing"));
        allureSteps.captureScreenshot(mainPage.getDriver());
        allureSteps.captureScreenshotSpoiler(mainPage.getDriver());
    }

    @Step("Input Password")
    @Test
    void inputPasswordTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.inputPassword("testing"));
    }

    @Step("Input Text Area")
    @Test
    void inputTextAreaTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.inputTextArea("testing"));
    }

    @Step("Input Disabled Input")
    @Test
    void inputDisabledInputTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertFalse(webFormPage.inputDisabledIsEnabled());
        Assertions.assertTrue(webFormPage.inputDisabledInputAssertThrows("testing"));
    }

    @Step("Dropdown Select From List")
    @Test
    void dropdownSelectFromListTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.dropdownSelectFromList("One"));
        Assertions.assertTrue(webFormPage.dropdownSelectFromList("Two"));
        Assertions.assertTrue(webFormPage.dropdownSelectFromList("Three"));
    }

    @Step("Dropdown Datalist")
    @Test
    void dropdownDatalistTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.dropdownDatalist("San Francisco"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("New York"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("Seattle"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("Los Angeles"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("Chicago"));
    }

    @Step("File Input")
    @Test
    void fileInputTest() throws IOException {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.fileInput());
    }

    @Step("Checkedbox And Radio")
    @Test
    void checkedboxAndRadioTests() {
        WebFormPage webFormPage = mainPage.webFormPage();

        Assertions.assertTrue(webFormPage.isCheckedCheckbox());
        webFormPage.setCheckedCheckbox();
        Assertions.assertFalse(webFormPage.isCheckedCheckbox());

        Assertions.assertFalse(webFormPage.isDefaultCheckbox());
        webFormPage.setDefaultCheckbox();
        Assertions.assertTrue(webFormPage.isDefaultCheckbox());

        Assertions.assertTrue(webFormPage.isCheckedRadio());
        Assertions.assertFalse(webFormPage.isDefaultRadio());
        webFormPage.setDefaultRadio();
        Assertions.assertFalse(webFormPage.isCheckedRadio());
        Assertions.assertTrue(webFormPage.isDefaultRadio());

        Assertions.assertFalse(webFormPage.isCheckedRadio());
        Assertions.assertTrue(webFormPage.isDefaultRadio());
        webFormPage.setCheckedRadio();
        Assertions.assertTrue(webFormPage.isCheckedRadio());
        Assertions.assertFalse(webFormPage.isDefaultRadio());
    }

    @Step("Date Picker")
    @Test
    void datePickerTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.datePicker());
    }

    @Step("Example Range")
    @Test
    void exampleRangeTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.exampleRange3StepsToLeft());
        Assertions.assertTrue(webFormPage.exampleRange4StepsToRight());
    }

    @Step("Web Form Page Header Text")
    @Flaky
    @Test
    void webFormPageHeaderTextTest() {
        Assertions.assertFalse(mainPage.header().isSubTitleText("Hands-On Selenium WebDriver with Java"));
        Assertions.assertTrue(mainPage.header().isSubTitleText("Practice site"));
    }

    @Step("Web Form Page Footer Text")
    @Test
    void webFormPageFooterTextTest() {
        Assertions.assertTrue(mainPage.footer().isFooterText("Copyright © 2021-2023 Boni García"));
    }
}
