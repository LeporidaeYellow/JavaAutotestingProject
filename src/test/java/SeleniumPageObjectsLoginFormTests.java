import extensions.AllureExtension;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;
import pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Allure report")
@ExtendWith(AllureExtension.class)
@Story("Login")
@Disabled
class SeleniumPageObjectsLoginFormTests {
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
    void loginSuccessTest() {
        LoginPage login = mainPage.loginFormPage();
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
        assertThat(login.invalidCredentialsBoxPresent()).isFalse();
    }

    @Step("Login Failure")
    @Test
    void loginFailureTest() {
        LoginPage login = mainPage.loginFormPage();
        login.with("test", "test");
        assertThat(login.successBoxPresent()).isFalse();
        assertThat(login.invalidCredentialsBoxPresent()).isTrue();
    }

    @Step("Login Page Titles")
    @Test
    void loginPageTitlesTest() {
        Assertions.assertTrue(mainPage.header().isTitleText("Hands-On Selenium WebDriver with Java"));
        Assertions.assertTrue(mainPage.header().isSubTitleText("Practice site"));
    }

    @Step("Login Page Footer Text")
    @Test
    void loginPageFooterTextTest() {
        Assertions.assertTrue(mainPage.footer().isFooterText("Copyright © 2021-2023 Boni García"));
    }
}