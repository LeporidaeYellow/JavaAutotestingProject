import local.project.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static local.project.Constants.*;

public class SeleniumActionsTests {
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

    //TODO: Ввод текста в поля и проверка значений
    @Test
    void inputTextInputTest() throws InterruptedException {
        String stringForInput = "webForm";
        WebElement webForm = driver.findElement(By.cssSelector("input.form-control[id = 'my-text-id']"));
        webForm.sendKeys(stringForInput);

        Assertions.assertEquals(stringForInput, webForm.getAttribute("value"));;
    }

    //TODO: Ввод текста в поля и проверка значений
    @Test
    void inputPasswordTest() throws InterruptedException {
        String stringForInput = "webForm";
        WebElement webForm = driver.findElement(By.cssSelector("input.form-control[type = 'password'][name = 'my-password']"));
        webForm.sendKeys(stringForInput);

        Assertions.assertEquals(stringForInput, webForm.getAttribute("value"));;
    }

    //TODO: Ввод текста в поля и проверка значений
    @Test
    void inputTextAreaTest() throws InterruptedException {
        String stringForInput = "webForm";
        WebElement webForm = driver.findElement(By.cssSelector("textarea.form-control[name = 'my-textarea']"));
        webForm.sendKeys(stringForInput);

        Assertions.assertEquals(stringForInput, webForm.getAttribute("value"));;
    }

    //TODO: попытка ввести в disabled поле
    @Test
    void inputDisabledInputTest() {
        String stringForInput = "webForm";
        WebElement webForm = driver.findElement(By.cssSelector("input.form-control[placeholder = 'Disabled input']"));

        Assertions.assertFalse(webForm.isEnabled());
        Assertions.assertThrows(ElementNotInteractableException.class, () -> webForm.sendKeys(stringForInput));
    }

    //TODO: работа со списками
    @Test
    void dropdownSelectFromListTest() throws InterruptedException {
        WebElement dropdownSelectMenu = driver.findElement(By.cssSelector("select.form-select"));
        Select select = new Select(dropdownSelectMenu);

        select.selectByIndex(1);
        Assertions.assertEquals("One", select.getFirstSelectedOption().getText());
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());

        select.selectByIndex(0);
        Assertions.assertEquals("Open this select menu", dropdownSelectMenu.getAttribute("value"));
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());

        select.selectByIndex(3);
        Assertions.assertEquals("Three", select.getFirstSelectedOption().getText());
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());

        select.selectByIndex(2);
        Assertions.assertEquals("Two", select.getFirstSelectedOption().getText());
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());

        if (select.isMultiple()) {
            select.deselectByValue("1");
            select.deselectByIndex(2);
            select.deselectByVisibleText("Three");
            select.deselectAll();
        } else {
            System.out.println("You may only deselect all options of a multi-select");
        }
    }


    //TODO: работа со списками
    @Test
    void dropdownDatalistTest() throws InterruptedException {
        List<WebElement> webElements = driver.findElements(By.cssSelector("datalist[id] option"));
        WebElement inputForm = driver.findElement(By.cssSelector("div:nth-child(2) label:nth-child(2) input"));

        inputForm.clear();
        inputForm.sendKeys(webElements.get(0).getAttribute("value"));
        Assertions.assertEquals("San Francisco", inputForm.getAttribute("value"));

        inputForm.clear();
        inputForm.sendKeys(webElements.get(1).getAttribute("value"));
        Assertions.assertEquals("New York", inputForm.getAttribute("value"));

        inputForm.clear();
        inputForm.sendKeys(webElements.get(2).getAttribute("value"));
        Assertions.assertEquals("Seattle", inputForm.getAttribute("value"));

        inputForm.clear();
        inputForm.sendKeys(webElements.get(3).getAttribute("value"));
        Assertions.assertEquals("Los Angeles", inputForm.getAttribute("value"));

        inputForm.clear();
        inputForm.sendKeys(webElements.get(4).getAttribute("value"));
        Assertions.assertEquals("Chicago", inputForm.getAttribute("value"));


    }

    // TODO: загрузка файла
    @Test
    void fileInputTest() throws IOException {
        String str = "Test";
        String fileName = "testFile.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(str);
        writer.close();

        File file = new File(fileName);
        String path = String.valueOf(file.getAbsoluteFile());

        WebElement fileUpload = driver.findElement(By.name("my-file"));
        fileUpload.sendKeys(path);

        WebElement submit = driver.findElement(By.xpath("//button[text() = 'Submit']"));
        submit.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains(fileName));
    }

    //TODO: чек-боксы
    @Test
    void checkCheckboxTest() {
        WebElement checkboxByCss = driver.findElement(By.cssSelector("input[Id = 'my-check-1']"));
        checkboxByCss.click();

        Assertions.assertEquals(null, checkboxByCss.getAttribute("checked"));

        WebElement checkboxByXpath = driver.findElement(By.xpath("//*[@id = 'my-check-1']"));
        checkboxByXpath.click();

        Assertions.assertEquals("true", checkboxByXpath.getAttribute("checked"));
    }

    //TODO: чек-боксы
    @Test
    void defaultCheckboxTest() {
        WebElement checkboxByCss = driver.findElement(By.cssSelector("input[Id = 'my-check-2']"));
        checkboxByCss.click();

        Assertions.assertEquals("true", checkboxByCss.getAttribute("checked"));

        WebElement checkboxByXpath = driver.findElement(By.xpath("//*[@id = 'my-check-2']"));
        checkboxByXpath.click();

        Assertions.assertEquals(null, checkboxByXpath.getAttribute("checked"));
    }

    //TODO: чек-боксы
    @Test
    void defaultAndCheckedRadioTest() {

        WebElement defaultRadioByCss = driver.findElement(By.cssSelector("input[Id = 'my-radio-2']"));
        defaultRadioByCss.click();

        Assertions.assertEquals("true", defaultRadioByCss.getAttribute("checked"));

        WebElement checkedRadioByCss = driver.findElement(By.cssSelector("input[Id = 'my-radio-1']"));
        checkedRadioByCss.click();

        Assertions.assertEquals("true", checkedRadioByCss.getAttribute("checked"));


        WebElement defaultRadioByXpath = driver.findElement(By.xpath("//*[@id = 'my-radio-2']"));
        defaultRadioByXpath.click();

        Assertions.assertEquals("true", defaultRadioByXpath.getAttribute("checked"));


        WebElement checkedRadioByXpath = driver.findElement(By.xpath("//*[@id = 'my-radio-1']"));
        checkedRadioByXpath.click();

        Assertions.assertEquals("true", checkedRadioByXpath.getAttribute("checked"));
    }


    // TODO: проверка ввода даты
    @Test
    void datePickerTest() {
        String targetMonth = "June 2025";

        WebElement datePickerByCss = driver.findElement(By.cssSelector("body > main > div > form > div > div:nth-child(3) > label:nth-child(2) > input"));
        datePickerByCss.click();

        WebElement button = driver.findElement(By.cssSelector("body > div > div.datepicker-days > table > thead > tr:nth-child(2) > th.next"));
        while (true) {
            String monthText = driver.findElement(By.cssSelector("body > div > div.datepicker-days > table > thead > tr:nth-child(2) > th.datepicker-switch")).getText();
            if (monthText.equals(targetMonth)) {
                break;
            }
            button.click();
        }

        WebElement dayButton = driver.findElement(By.xpath("/html/body/div/div[1]/table/tbody/tr[3]/td[3]"));
        dayButton.click();
        Assertions.assertEquals("06/10/2025",datePickerByCss.getAttribute("value") );
    }

    // TODO: проверка изменения диапазона ползунка
    @Test
    void exampleRangeTest() {
        WebElement exampleRangeByCss = driver.findElement(By.cssSelector("input.form-range"));
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        exampleRangeByCss.sendKeys(Keys.RIGHT);
        exampleRangeByCss.sendKeys(Keys.RIGHT);

        Assertions.assertEquals("9", exampleRangeByCss.getAttribute("value"));

        exampleRangeByCss.clear();
        WebElement exampleRangeByXpath = driver.findElement(By.xpath("//label/input[@class = 'form-range']"));
        exampleRangeByXpath.sendKeys(Keys.LEFT);
        exampleRangeByXpath.sendKeys(Keys.LEFT);
        exampleRangeByXpath.sendKeys(Keys.LEFT);

        Assertions.assertEquals("2", exampleRangeByCss.getAttribute("value"));
    }

    //TODO: drag & drop
    @Test
    void dragAndDropTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("target"));
        new Actions(driver)
                .dragAndDrop(draggable, target)
                .perform();

        Assertions.assertEquals(draggable.getLocation(), target.getLocation());
    }

    //TODO: проверка текста и навигации
    @Test
    void navigationTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html");

        WebElement buttonOne = driver.findElement(By.cssSelector("nav > ul > li:nth-child(3)"));
        buttonOne.click();

        Assertions.assertTrue(driver.findElement(By.className("lead")).getText().contains("Ut enim ad minim veniam"));

        WebElement buttonTwo = driver.findElement(By.cssSelector("nav > ul > li:nth-child(4)"));
        buttonTwo.click();

        Assertions.assertTrue(driver.findElement(By.className("lead")).getText().contains("Excepteur sint occaecat cupidatat"));
    }


    //TODO: проверка дропдаун
    @Test
    void actionAPIMouseClickTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");

        WebElement dropdown1 = driver.findElement(By.id("my-dropdown-1"));
        new Actions(driver)
                .click(dropdown1)
                .perform();

        WebElement dropdown1Show = driver.findElement(By.cssSelector("ul[class = 'dropdown-menu show']"));
        Assertions.assertEquals("dropdown-menu show", dropdown1Show.getAttribute("class"));

        WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
        new Actions(driver)
                .contextClick(dropdown2)
                .perform();

        WebElement dropdown2Show = driver.findElement(By.cssSelector("ul[id = 'context-menu-2']"));
        Assertions.assertEquals("display: block;", dropdown2Show.getAttribute("style"));

        WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
        new Actions(driver)
                .doubleClick(dropdown3)
                .perform();

        WebElement dropdown3Show = driver.findElement(By.cssSelector("ul[id = 'context-menu-3']"));
        Assertions.assertEquals("display: block;", dropdown3Show.getAttribute("style"));
    }
}