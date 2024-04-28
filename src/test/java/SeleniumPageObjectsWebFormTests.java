import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.WebFormPage;
import pages.MainPage;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SeleniumPageObjectsWebFormTests {
    MainPage mainPage;

    @BeforeEach
    void setup() {
        mainPage = new MainPage("chrome");
    }

    @AfterEach
    void teardown() {
        mainPage.quit();
    }

    @Test
    void inputTextInputTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.inputTextInput("testing"));
    }

    @Test
    void inputPasswordTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.inputPassword("testing"));
    }

    @Test
    void inputTextAreaTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.inputTextArea("testing"));
    }

    @Test
    void inputDisabledInputTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertFalse(webFormPage.inputDisabledIsEnabled());
        Assertions.assertTrue(webFormPage.inputDisabledInputAssertThrows("testing"));
    }

    @Test
    void dropdownSelectFromListTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.dropdownSelectFromList("One"));
        Assertions.assertTrue(webFormPage.dropdownSelectFromList("Two"));
        Assertions.assertTrue(webFormPage.dropdownSelectFromList("Three"));
    }

    @Test
    void dropdownDatalistTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.dropdownDatalist("San Francisco"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("New York"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("Seattle"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("Los Angeles"));
        Assertions.assertTrue(webFormPage.dropdownDatalist("Chicago"));
    }

    @Test
    void fileInputTest() throws IOException {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.fileInput());
    }

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

    @Test
    void datePickerTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.datePicker());
    }

    @Test
    void exampleRangeTest() {
        WebFormPage webFormPage = mainPage.webFormPage();
        Assertions.assertTrue(webFormPage.exampleRange3StepsToLeft());
        Assertions.assertTrue(webFormPage.exampleRange4StepsToRight());
    }

    @Test
    void webFormPageHeaderTextTest() {
        Assertions.assertTrue(mainPage.header().isSubTitleText("Hands-On Selenium WebDriver with Java"));
        Assertions.assertTrue(mainPage.header().isSubTitleText("Practice site"));
    }

    @Test
    void webFormPageFooterTextTest() {
        Assertions.assertTrue(mainPage.footer().isFooterText("Copyright © 2021-2023 Boni García"));
    }
}
