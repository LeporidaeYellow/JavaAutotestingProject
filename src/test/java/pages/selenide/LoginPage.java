package pages.selenide;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Condition.text;
import static org.assertj.core.api.Assertions.assertThat;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public boolean submit(String username, String password) {
        SelenideElement subTitle = $(By.className("display-6"));

        String textBeforeClick = subTitle.getText();
        $("#username").sendKeys(username);
        $("#password").sendKeys(password);
        $(By.xpath("//button[@type='submit']")).click();

        assertThat(textBeforeClick).isEqualTo("Login form");
        subTitle.shouldHave(text("Login form"));
        WebElement successMessage = $("#success");
        return successMessage.isDisplayed();
    }
}
