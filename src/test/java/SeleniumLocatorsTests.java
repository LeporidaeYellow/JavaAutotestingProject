import local.project.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static local.project.Constants.*;

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
    void inputTextInputTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(TEXT_INPUT_CSS_SELECTOR));
        webFormByCss.sendKeys("webFormByCss ");
        Thread.sleep(1000);
        WebElement webFormByXpath = driver.findElement(By.xpath(TEXT_INPUT_XPATH_SELECTOR));
        webFormByCss.sendKeys("webFormByXpath ");
        Thread.sleep(2000);
    }

    @Test
    void inputPasswordTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(PASSWORD_CSS_SELECTOR));
        webFormByCss.sendKeys("webFormByCss ");
        Thread.sleep(1000);
        WebElement webFormByXpath = driver.findElement(By.xpath(PASSWORD_XPATH_SELECTOR));
        webFormByCss.sendKeys("webFormByXpath ");
        Thread.sleep(2000);
    }

    @Test
    void inputTextAreaTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(TEXTAREA_CSS_SELECTOR));
        webFormByCss.sendKeys("webFormByCss \n");
        Thread.sleep(1000);
        WebElement webFormByXpath = driver.findElement(By.xpath(TEXTAREA_XPATH_SELECTOR));
        webFormByCss.sendKeys("webFormByXpath ");
        Thread.sleep(2000);
    }

    @Test
    void inputDisabledInputTest() {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(DISABLED_INPUT_CSS_SELECTOR));
        String webFormByCssAttribute = webFormByCss.getAttribute("placeholder");
        Assertions.assertTrue(webFormByCssAttribute.contains("Disabled"));
        WebElement webFormByXpath = driver.findElement(By.xpath(DISABLED_INPUT_XPATH_SELECTOR));
        String webFormByXpathAttribute = webFormByXpath.getAttribute("placeholder");
        Assertions.assertTrue(webFormByXpathAttribute.contains("Disabled"));
    }

    @Test
    void inputReadonlyInputTest() {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(READONLY_INPUT_CSS_SELECTOR));
        String webFormByCssAttribute = webFormByCss.getAttribute("readonly");
        Assertions.assertTrue(webFormByCssAttribute.contains("true"));
        WebElement webFormByXpath = driver.findElement(By.xpath(READONLY_INPUT_XPATH_SELECTOR));
        String webFormByXpathAttribute = webFormByXpath.getAttribute("readonly");
        Assertions.assertTrue(webFormByXpathAttribute.contains("true"));
    }

    @Test
    void returnToIndexTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(RETURN_TO_INDEX_CSS_SELECTOR));
        webFormByCss.click();
        Thread.sleep(1000);
        String xpath = "//a[@href = 'web-form.html']";
        WebElement webFormButton = driver.findElement(By.xpath(xpath));
        webFormButton.click();
        Thread.sleep(1000);
        WebElement webFormByXpath = driver.findElement(By.xpath(RETURN_TO_INDEX_XPATH_SELECTOR));
        webFormByXpath.click();
        Thread.sleep(1000);
    }

    @Test
    void dropdownSelectTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        Thread.sleep(1000);
        WebElement webFormByCss = driver.findElement(By.cssSelector(DROPDOWN_SELECT_CSS_SELECTOR));
        webFormByCss.click();
        Thread.sleep(1000);
        WebElement webFormByXpath = driver.findElement(By.xpath(DROPDOWN_SELECT_XPATH_SELECTOR));
        webFormByXpath.click();
        Thread.sleep(1000);
    }

    @Test
    void dropdownDatalistTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement webDataByCss = driver.findElement(By.cssSelector(DROPDOWN_DATALIST_CSS_SELECTOR));
        WebElement webFormByCss = driver.findElement(By.cssSelector(DROPDOWN_DATALIST_INPUT_CSS_SELECTOR));
        webFormByCss.sendKeys(webDataByCss.getAttribute("value"));
        Thread.sleep(1000);
        WebElement webDataByXpath = driver.findElement(By.xpath(DROPDOWN_DATALIST_XPATH_SELECTOR));
        WebElement webFormByXpath = driver.findElement(By.xpath(DROPDOWN_DATALIST_INPUT_XPATH_SELECTOR));
        webFormByXpath.clear();
        webFormByXpath.sendKeys(webDataByXpath.getAttribute("value"));
        Thread.sleep(1000);
    }

    @Test
    void fileInputTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement webFormByCss = driver.findElement(By.cssSelector(FILE_INPUT_CSS_SELECTOR));
        Assertions.assertEquals("file", webFormByCss.getAttribute("type"));
        Thread.sleep(1000);
        WebElement webFormByXpath = driver.findElement(By.xpath(FILE_INPUT_XPATH_SELECTOR));
        Assertions.assertEquals("file", webFormByXpath.getAttribute("type"));
        Thread.sleep(1000);
    }

    @Test
    void checkCheckboxTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement checkboxByCss = driver.findElement(By.cssSelector(CHECKED_CHECKBOX_CSS_SELECTOR));
        checkboxByCss.click();
        Thread.sleep(1000);
        WebElement checkboxByXpath = driver.findElement(By.xpath(CHECKED_CHECKBOX_XPATH_SELECTOR));
        checkboxByXpath.click();
        Thread.sleep(1000);
    }

    @Test
    void defaultCheckboxTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement checkboxByCss = driver.findElement(By.cssSelector(DEFAULT_CHECKBOX_CSS_SELECTOR));
        checkboxByCss.click();
        Thread.sleep(1000);
        WebElement checkboxByXpath = driver.findElement(By.xpath(DEFAULT_CHECKBOX_XPATH_SELECTOR));
        checkboxByXpath.click();
        Thread.sleep(1000);
    }

    @Test
    void defaultAndCheckedRadioTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        Thread.sleep(1000);
        WebElement defaultRadioByCss = driver.findElement(By.cssSelector(DEFAULT_RADIO_CSS_SELECTOR));
        defaultRadioByCss.click();
        Thread.sleep(1000);
        WebElement checkedRadioByCss = driver.findElement(By.cssSelector(CHECKED_RADIO_CSS_SELECTOR));
        checkedRadioByCss.click();
        Thread.sleep(1000);
        WebElement defaultRadioByXpath = driver.findElement(By.xpath(DEFAULT_RADIO_XPATH_SELECTOR));
        defaultRadioByXpath.click();
        Thread.sleep(1000);
        WebElement checkedRadioByXpath = driver.findElement(By.xpath(CHECKED_RADIO_XPATH_SELECTOR));
        checkedRadioByXpath.click();
        Thread.sleep(1000);
    }

    @Test
    void submitButtonByCssTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement submitButtonByCss = driver.findElement(By.cssSelector(BUTTON_SUBMIT_CSS_SELECTOR));
        submitButtonByCss.click();
        Thread.sleep(1000);
    }

    @Test
    void submitButtonByXpathTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement submitButtonByXpath = driver.findElement(By.xpath(BUTTON_SUBMIT_XPATH_SELECTOR));
        submitButtonByXpath.click();
        Thread.sleep(1000);
    }

    @Test
    void colorPickerTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement changeColorByCss = driver.findElement(By.cssSelector(COLOR_PICKER_CSS_SELECTOR));
        changeColorByCss.sendKeys("#DDDDDD");
        Thread.sleep(1000);
        WebElement changeColorByXpath = driver.findElement(By.xpath(COLOR_PICKER_XPATH_SELECTOR));
        changeColorByXpath.sendKeys("#AAAAAA");
        Thread.sleep(1000);
    }

    @Test
    void datePickerTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement datePickerByCss = driver.findElement(By.cssSelector(DATE_PICKER_CSS_SELECTOR));
        datePickerByCss.sendKeys("03/19/2024");
        Thread.sleep(1000);
        datePickerByCss.clear();
        WebElement datePickerByXpath = driver.findElement(By.xpath(DATE_PICKER_XPATH_SELECTOR));
        datePickerByXpath.sendKeys("22/11/2000");
        Thread.sleep(1000);
    }

    @Test
    void exampleRangeTest() throws InterruptedException {
        driver.manage().window().fullscreen();
        WebElement exampleRangeByCss = driver.findElement(By.cssSelector(EXAMPLE_RANGE_CSS_SELECTOR));
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        Thread.sleep(1000);
        exampleRangeByCss.clear();
        WebElement exampleRangeByXpath = driver.findElement(By.xpath(EXAMPLE_RANGE_XPATH_SELECTOR));
        exampleRangeByXpath.sendKeys(Keys.LEFT);
        exampleRangeByXpath.sendKeys(Keys.LEFT);
        exampleRangeByXpath.sendKeys(Keys.LEFT);
        Thread.sleep(1000);
    }
}