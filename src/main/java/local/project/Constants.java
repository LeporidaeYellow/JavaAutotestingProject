package local.project;

public class Constants {
    public static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    public static final String TEXT_INPUT_CSS_SELECTOR = "input.form-control[id = 'my-text-id']";
    public static final String TEXT_INPUT_XPATH_SELECTOR = "//*[@id = 'my-text-id']";

    public static final String PASSWORD_CSS_SELECTOR = "input.form-control[type = 'password'][name = 'my-password']";
    public static final String PASSWORD_XPATH_SELECTOR = "//*[@type = 'password'][@name = 'my-password']";

    public static final String TEXTAREA_CSS_SELECTOR = "textarea.form-control[name = 'my-textarea']";
    public static final String TEXTAREA_XPATH_SELECTOR = "//textarea[@name = 'my-textarea']";

    public static final String DISABLED_INPUT_CSS_SELECTOR = "input.form-control[placeholder = 'Disabled input']";
    public static final String DISABLED_INPUT_XPATH_SELECTOR = "//*[@placeholder = 'Disabled input']";

    public static final String READONLY_INPUT_CSS_SELECTOR = "input.form-control[value = 'Readonly input']";
    public static final String READONLY_INPUT_XPATH_SELECTOR = "//*[@value = 'Readonly input']";

    public static final String RETURN_TO_INDEX_CSS_SELECTOR = "a[href *= 'index']";
    public static final String RETURN_TO_INDEX_XPATH_SELECTOR = "//*[contains(@href, 'index')]";

    public static final String DROPDOWN_SELECT_CSS_SELECTOR = "select.form-select option:nth-child(2)";
    public static final String DROPDOWN_SELECT_XPATH_SELECTOR = "//select/option[@value = 3]";

    public static final String DROPDOWN_DATALIST_INPUT_CSS_SELECTOR = "div:nth-child(2) label:nth-child(2) input";
    public static final String DROPDOWN_DATALIST_INPUT_XPATH_SELECTOR = "//*[@list = 'my-options']";

    public static final String DROPDOWN_DATALIST_CSS_SELECTOR = "label option[Value = 'Chicago']";
    public static final String DROPDOWN_DATALIST_XPATH_SELECTOR = "//*[@id = 'my-options']/option[3]";

    public static final String FILE_INPUT_CSS_SELECTOR = "[type = 'file']";
    public static final String FILE_INPUT_XPATH_SELECTOR = "//*[@type = 'file']";

    public static final String CHECKED_CHECKBOX_CSS_SELECTOR = "input[Id = 'my-check-1']";
    public static final String CHECKED_CHECKBOX_XPATH_SELECTOR = "//*[@id = 'my-check-1']";

    public static final String DEFAULT_CHECKBOX_CSS_SELECTOR = "input[Id = 'my-check-2']";
    public static final String DEFAULT_CHECKBOX_XPATH_SELECTOR = "//*[@id = 'my-check-2']";

    public static final String CHECKED_RADIO_CSS_SELECTOR = "input[Id = 'my-radio-1']";
    public static final String CHECKED_RADIO_XPATH_SELECTOR = "//*[@id = 'my-radio-1']";

    public static final String DEFAULT_RADIO_CSS_SELECTOR = "input[Id = 'my-radio-2']";
    public static final String DEFAULT_RADIO_XPATH_SELECTOR = "//*[@id = 'my-radio-2']";

    public static final String BUTTON_SUBMIT_CSS_SELECTOR = "button[Type = 'submit']";
    public static final String BUTTON_SUBMIT_XPATH_SELECTOR = "//button[text() = 'Submit']";

    public static final String COLOR_PICKER_CSS_SELECTOR = "[type = 'color']";
    public static final String COLOR_PICKER_XPATH_SELECTOR = "//*[@type = 'color']";

    public static final String DATE_PICKER_CSS_SELECTOR = "input[Name = 'my-date']";
    public static final String DATE_PICKER_XPATH_SELECTOR = "//label/input[@Class = 'form-control'][@Name = 'my-date']";

    public static final String EXAMPLE_RANGE_CSS_SELECTOR = "input.form-range";
    public static final String EXAMPLE_RANGE_XPATH_SELECTOR = "//label/input[@class = 'form-range']";
}
