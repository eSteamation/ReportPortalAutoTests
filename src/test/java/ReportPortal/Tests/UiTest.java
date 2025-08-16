package ReportPortal.Tests;

import ReportPortal.Hooks.UiTestExtension;
import ReportPortal.UI.DashboardPage;
import ReportPortal.UI.LoginPage;
import ReportPortal.UI.WidgetPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static ReportPortal.Utilities.ConfigLoader.getProperty;


@ExtendWith(UiTestExtension.class)
public class UiTest {
    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final WidgetPage widgetPage = new WidgetPage();

    @Test
    void LoginPageTest() {
        loginPage.loginWait();
        loginPage.loginInput(getProperty("username"), getProperty("password"));
        dashboardPage.dashboardList();
        dashboardPage.dashboardFilter(getProperty("dashboardName"));
        dashboardPage.dashboardOpen();
        dashboardPage.widgetCreate();
        widgetPage.widgetCreation(getProperty("filterName"), getProperty("widgetName"), getProperty("widgetDescription"));
        dashboardPage.nameVerify(getProperty("widgetName"));
    }
}