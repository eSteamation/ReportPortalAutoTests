package ReportPortal.UI;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class WidgetPage extends BasePage {
    protected final SelenideElement TestCasesGrowth = $x("//label[.//div[text()='Test-cases growth trend chart']]").as("Кнопка 'Test-cases growth trend chart'");
    protected final SelenideElement buttonNext = $x("//span[text()='Next step']").as("Кнопка 'Next step'");
    protected final SelenideElement filterPick = $x("//label[@tabindex='1']").as("Первый фильтр в списке");
    protected final SelenideElement widgetName = $x("//input[@placeholder='Enter widget name']").as("Поле ввода названия виджета");
    protected final SelenideElement widgetDescription = $x("//textarea[@placeholder='Enter widget description']").as("Поле ввода описания виджета");
    protected final SelenideElement buttonAdd = $x("//button[text()='Add']").as("Кнопка 'Add'");


    public void widgetCreation(String filterName, String name, String description) {
        widgetType();
        buttonNext.click();
        searchByName.shouldBe(clickable);
        searchByName.setValue(filterName);
        SpinnerCheck();
        widgetFilter();
        buttonNext.click();
        widgetInputs(name, description);
        buttonAdd.shouldBe(clickable);
        buttonAdd.click();
    }

    public void widgetType() {
        TestCasesGrowth.shouldBe(visible);
        TestCasesGrowth.click();
    }

    public void widgetInputs(String name, String description) {
        widgetName.shouldBe(visible);
        widgetName.setValue(name);
        widgetDescription.shouldBe(empty);
        widgetDescription.setValue(description);
    }

    public void widgetFilter() {
        filterPick.shouldBe(clickable);
        filterPick.click();
    }

}
