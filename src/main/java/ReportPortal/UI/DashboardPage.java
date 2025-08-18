package ReportPortal.UI;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage extends BasePage {

    protected final SelenideElement dashboardFirstElement = $x("//a[contains(@class, 'dashboardTable__name')]").as("Первый результат");
    protected final SelenideElement widgetNew = $x("//span[text()='Add new widget']").as("Добавить новый виджет");
    protected final String WidgetHeaderXpath = "//div[contains(@class,'widgetHeader') and text()='%s']";

    @Step("Переход в раздел дешбордов")
    public void dashboardList() {
        dashboard.shouldBe(clickable);
        dashboard.click();
    }

    @Step("Поиск дешборда: {Input}")
    public void dashboardFilter(String Input) {
        searchByName.shouldBe(clickable);
        searchByName.setValue(Input);
        SpinnerCheck();
    }

    @Step("Переход к выбранному дешборду")
    public void dashboardOpen() {
        dashboardFirstElement.shouldBe(clickable);
        dashboardFirstElement.click();
    }

    @Step("Переход к созданию виджета")
    public void widgetCreate() {
        widgetNew.shouldBe(clickable);
        widgetNew.click();
    }

    @Step("Получение имени виджета")
    public SelenideElement widgetCheckName(String widgetNameInput) {
        String xpath = String.format(WidgetHeaderXpath, widgetNameInput);
        return $x(xpath);
    }

    @Step("Проверка успешного создания виджета")
    public void nameVerify(String widgetNameInput) {
        widgetCheckName(widgetNameInput).should(exist);
    }
}
