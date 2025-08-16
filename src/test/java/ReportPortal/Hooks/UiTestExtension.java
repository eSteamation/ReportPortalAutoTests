package ReportPortal.Hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ReportPortal.Utilities.ConfigLoader.getProperty;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class UiTestExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(@SuppressWarnings("NullableProblems") ExtensionContext context) {
        Configuration.baseUrl = "https://demo.reportportal.io/ui/";
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        open("");
        getWebDriver().manage().window().maximize();
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(Boolean.parseBoolean(getProperty("screenshots")))
                        .savePageSource(Boolean.parseBoolean(getProperty("savePageSource"))));
    }
}
