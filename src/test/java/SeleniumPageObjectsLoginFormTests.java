import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void loginSuccessTest() {
        LoginPage login = mainPage.loginFormPage();
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
        assertThat(login.invalidCredentialsBoxPresent()).isFalse();
    }

    @Test
    void loginFailureTest() {
        LoginPage login = mainPage.loginFormPage();
        login.with("test", "test");
        assertThat(login.successBoxPresent()).isFalse();
        assertThat(login.invalidCredentialsBoxPresent()).isTrue();
    }

    @Test
    void loginPageTitlesTest() {
        Assertions.assertTrue(mainPage.header().isTitleText("Hands-On Selenium WebDriver with Java"));
        Assertions.assertTrue(mainPage.header().isSubTitleText("Practice site"));
    }

    @Test
    void loginPageFooterTextTest() {
        Assertions.assertTrue(mainPage.footer().isFooterText("Copyright © 2021-2023 Boni García"));
    }
}