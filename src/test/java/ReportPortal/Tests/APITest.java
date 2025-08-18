package ReportPortal.Tests;

import ReportPortal.API.DashboardCreator;
import ReportPortal.Hooks.ApiTestExtension;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static ReportPortal.Utilities.ConfigLoader.getProperty;
import static io.restassured.RestAssured.given;

@ExtendWith(ApiTestExtension.class)
public class APITest {


    private static final String NAME = getProperty("dashboardAPIName");
    private static final String DESCRIPTION = getProperty("dashboardAPIDescription");
    private static final String PROJECT = getProperty("projectName");
    private static final String API_KEY = getProperty("API_KEY");
    private static final String TEMPLATE = getProperty("templateName");
    private final DashboardCreator dashboardCreator = new DashboardCreator();

    @Test
    @Order(1)
    void HealthCheckAPI() {
        given()
                .baseUri(getProperty("URL_API"))
                .header("Authorization", "Bearer " + API_KEY)
                .when()
                .get("/api/v1/" + PROJECT + "/dashboard")
                .then()
                .statusCode(200);
    }

    @Test
    void CreationTestPositive() {
        dashboardCreator.jsonImport(TEMPLATE);
        dashboardCreator.jsonModifier(NAME, DESCRIPTION);
        dashboardCreator.jsonRequest(PROJECT, "201", API_KEY);
        dashboardCreator.jsonVerify(PROJECT, API_KEY);
    }

    @Test
    void CreationTestNegative() {
        dashboardCreator.jsonImport(TEMPLATE);
        dashboardCreator.jsonModifierFaulty();
        dashboardCreator.jsonRequest(PROJECT, "400", API_KEY);
        dashboardCreator.jsonVerify(PROJECT, API_KEY);
    }
}
