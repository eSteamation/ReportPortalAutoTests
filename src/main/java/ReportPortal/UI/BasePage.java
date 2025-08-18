package ReportPortal.UI;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    protected final SelenideElement dashboard = $x("//a[contains(@href,'dashboard')]").as("Кнопка боковой панели 'Dashboard'");

    protected final SelenideElement spinnerPreloader = $x("//div[contains(@class,'spinningPreloader')]").as("Спиннер загрузки");
    protected final SelenideElement searchByName = $x("//input[contains(@class, 'inputSearch')]").as("Поля для поиска");

    @Step("Ожидание прогрузки")
    public void SpinnerCheck() {
        try {
            spinnerPreloader.should(appear, Duration.ofMillis(300));
        } catch (UIAssertionError ignored) {
        }
        spinnerPreloader.should(disappear);
    }
}
