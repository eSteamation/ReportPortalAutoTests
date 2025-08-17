package ReportPortal.Tests;

import ReportPortal.API.DashboardCreator;
import ReportPortal.Hooks.ApiTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static ReportPortal.Utilities.ConfigLoader.getProperty;

@ExtendWith(ApiTestExtension.class)
public class APITest {
    private final DashboardCreator dashboardCreator = new DashboardCreator();

    @Test
    void CreationTestPositive() {
        dashboardCreator.jsonImport("DashboardTemplate.json");
        dashboardCreator.jsonModifier(getProperty("dashboardAPIName"), getProperty("dashboardAPIDescription"));
        dashboardCreator.jsonRequest("default_personal", "201", getProperty("API_KEY"));
        dashboardCreator.jsonVerify(getProperty("dashboardAPIName"), getProperty("dashboardAPIDescription"));
    }
}
