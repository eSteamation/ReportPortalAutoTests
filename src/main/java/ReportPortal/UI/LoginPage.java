package ReportPortal.UI;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Param;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.SetValueOptions.withText;
import static io.qameta.allure.model.Parameter.Mode.MASKED;

public class LoginPage {
    protected final SelenideElement usernameField = $x("//input[@name='login']").as("Поле ввода логина");
    protected final SelenideElement passwordField = $x("//input[@type='password']").as("Поле ввода пароля");
    protected final SelenideElement loginCommit = $x("//button[@type='submit']").as("Кнопка входа");

    @Step("Ввод логина")
    public void loginInput(String username, @Param(mode = MASKED) String password) {
        usernameField.setValue(username);
        setPassword(password);
        loginCommit.click();
    }

    @Step("Ввод пароля")
    public void setPassword(@Param(mode = MASKED) String password) {
        passwordField.setValue(withText(password).sensitive());
    }

    @Step("Подтверждение входа")
    public void loginWait() {
        loginCommit.shouldBe(clickable);
    }
}