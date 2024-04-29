package pages;

import components.FooterComponent;
import extensions.AllureExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WebFormPage extends BasePage {

    private final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    @FindBy(css = "input.form-control[id = 'my-text-id']")
    @CacheLookup
    private WebElement textInput;

    @FindBy(css = "input.form-control[type = 'password'][name = 'my-password']")
    @CacheLookup
    private WebElement password;

    @FindBy(css = "textarea.form-control[name = 'my-textarea']")
    @CacheLookup
    private WebElement textArea;

    @FindBy(css = "input.form-control[placeholder = 'Disabled input']")
    @CacheLookup
    private WebElement disabledInput;

    @FindBy(css = "select.form-select")
    @CacheLookup
    private WebElement dropdownSelectMenu;

    @FindBy(css = "datalist[id]")
    @CacheLookup
    private WebElement dropdownDatalist;

    @FindBy(css = "div:nth-child(2) label:nth-child(2) input")
    @CacheLookup
    private WebElement dropdownDatalistInputForm;

    @FindBy(name = "my-file")
    @CacheLookup
    private WebElement fileUpload;

    @FindBy(xpath = "//button[text() = 'Submit']")
    @CacheLookup
    private WebElement submit;

    @FindBy(css = "input[Id = 'my-check-1']")
    @CacheLookup
    private WebElement checkedCheckbox;

    @FindBy(css = "input[Id = 'my-check-2']")
    @CacheLookup
    private WebElement defaultCheckbox;

    @FindBy(css = "input[Id = 'my-radio-1']")
    @CacheLookup
    private WebElement checkedRadio;

    @FindBy(css = "input[Id = 'my-radio-2']")
    @CacheLookup
    private WebElement defaultRadio;

    @FindBy(css = "body > main > div > form > div > div:nth-child(3) > label:nth-child(2) > input")
    @CacheLookup
    private WebElement datePicker;

    @FindBy(css = "body > div > div.datepicker-days > table > thead > tr:nth-child(2) > th.next")
    @CacheLookup
    private WebElement buttonDatePicker;

    @FindBy(css = "body > div > div.datepicker-days > table > thead > tr:nth-child(2) > th.datepicker-switch")
    @CacheLookup
    private WebElement monthTextDatePicker;

    @FindBy(xpath = "/html/body/div/div[1]/table/tbody/tr[3]/td[3]")
    @CacheLookup
    private WebElement day10ButtonDatePicker;

    @FindBy(css = "input.form-range")
    @CacheLookup
    private WebElement exampleRange;

    FooterComponent header;
    FooterComponent footer;

    public WebFormPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        header = new FooterComponent(driver);
        visit(BASE_URL);
    }

    public WebFormPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        header = new FooterComponent(driver);
        footer = new FooterComponent(driver);
    }

    public Boolean inputTextInput(String string) {
        type(textInput, string);
        return string.equals(textInput.getAttribute("value"));
    }

    public Boolean inputPassword(String string) {
        type(password, string);
        return string.equals(password.getAttribute("value"));
    }

    public Boolean inputTextArea(String string) {
        type(textArea, string);
        return string.equals(textArea.getAttribute("value"));
    }

    public Boolean inputDisabledInputAssertThrows(String string) {
        try {
            type(disabledInput, string);
        } catch (ElementNotInteractableException ex) {
            return true;
        }
        return false;
    }

    public Boolean inputDisabledIsEnabled() {
        return disabledInput.isEnabled();
    }

    public Boolean dropdownSelectFromList(String string) {
        Select select = new Select(dropdownSelectMenu);
        String ccsString = ("option[text()='" + string + "']");
        select.selectByIndex(Integer.parseInt(dropdownSelectMenu.findElement(By.xpath(ccsString)).getAttribute("value")));
        if (select.getFirstSelectedOption().getText().equals(string)) {
            return true;
        } return false;
    }

    public Boolean dropdownDatalist(String string) {
        String cssString = "option[value = '" + string + "']";
        WebElement selectedOption = dropdownDatalist.findElement(By.cssSelector(cssString));
        clear(dropdownDatalistInputForm);
        type(dropdownDatalistInputForm, selectedOption.getAttribute("value"));
        return dropdownDatalistInputForm.getAttribute("value").equals(string);
    }

    public boolean fileInput() throws IOException {
        String str = "Test";
        String fileName = "testFile.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(str);
        writer.close();

        File file = new File(fileName);
        String path = String.valueOf(file.getAbsoluteFile());

        type(fileUpload, path);
        click(submit);

        return driver.getCurrentUrl().contains(fileName);
    }

    public Boolean elementIsChecked(WebElement element) {
        return element.getAttribute("checked") != null;
    }

    public void setCheckedCheckbox() {
        click(checkedCheckbox);
    }

    public Boolean isCheckedCheckbox() {
        return elementIsChecked(checkedCheckbox);
    }

    public void setDefaultCheckbox() {
        click(defaultCheckbox);
    }

    public Boolean isDefaultCheckbox() {
        return elementIsChecked(defaultCheckbox);
    }

    public void setCheckedRadio() {
        click(checkedRadio);
    }

    public Boolean isCheckedRadio() {
        return elementIsChecked(checkedRadio);
    }

    public void setDefaultRadio() {
        click(defaultRadio);
    }

    public Boolean isDefaultRadio() {
        return elementIsChecked(defaultRadio);
    }


    public Boolean datePicker() {
        String targetMonth = "June 2025";
        click(datePicker);
        while (true) {
            String monthText = monthTextDatePicker.getText();
            if (monthText.equals(targetMonth)) {
                break;
            }
            click(buttonDatePicker);
        }
        click(day10ButtonDatePicker);
        return datePicker.getAttribute("value").equals("06/10/2025");
    }

    public Boolean exampleRange4StepsToRight() {
        clear(exampleRange);
        type(exampleRange, Keys.RIGHT);
        type(exampleRange, Keys.RIGHT);
        type(exampleRange, Keys.RIGHT);
        type(exampleRange, Keys.RIGHT);
        return exampleRange.getAttribute("value").equals("9");
    }

    public Boolean exampleRange3StepsToLeft() {
        clear(exampleRange);
        type(exampleRange, Keys.LEFT);
        type(exampleRange, Keys.LEFT);
        type(exampleRange, Keys.LEFT);
        return exampleRange.getAttribute("value").equals("2");
    }
}
