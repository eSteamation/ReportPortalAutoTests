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
    private static final String USERNAME = getProperty("username");
    private static final String PASSWORD = getProperty("password");
    private static final String DASH_NAME = getProperty("dashboardName");
    private static final String FILTER = getProperty("filterName");
    private static final String WIDGET = getProperty("widgetName");
    private static final String WIDGET_DESC = getProperty("widgetDescription");

    @Test
    void LoginPageTest() {
        loginPage.loginWait();
        loginPage.loginInput(USERNAME, PASSWORD);
        dashboardPage.dashboardList();
        dashboardPage.dashboardFilter(DASH_NAME);
        dashboardPage.dashboardOpen();
        dashboardPage.widgetCreate();
        widgetPage.widgetCreation(FILTER, WIDGET, WIDGET_DESC);
        dashboardPage.nameVerify(WIDGET);
    }
}