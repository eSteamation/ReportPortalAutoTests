package ReportPortal.UI;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage extends BasePage {

    protected final SelenideElement dashboardFirstElement = $x("//a[contains(@class, 'dashboardTable__name')]").as("Первый результат");
    protected final SelenideElement widgetNew = $x("//span[text()='Add new widget']");
    protected final String WidgetHeaderXpath = "//div[contains(@class,'widgetHeader') and text()='%s']";

    public void dashboardList() {
        dashboard.shouldBe(clickable);
        dashboard.click();
    }

    public void dashboardFilter(String Input) {
        searchByName.shouldBe(clickable);
        searchByName.setValue(Input);
        SpinnerCheck();
    }

    public void dashboardOpen() {
        dashboardFirstElement.shouldBe(clickable);
        dashboardFirstElement.click();
    }

    public void widgetCreate() {
        widgetNew.shouldBe(clickable);
        widgetNew.click();
    }

    public SelenideElement widgetCheckName(String widgetNameInput) {
        String xpath = String.format(WidgetHeaderXpath, widgetNameInput);
        return $x(xpath);
    }

    public void nameVerify(String widgetNameInput) {
        widgetCheckName(widgetNameInput).should(exist);
    }
}
