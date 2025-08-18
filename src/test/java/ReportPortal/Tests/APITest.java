package ReportPortal.Tests;

import ReportPortal.API.DashboardCreator;
import ReportPortal.Hooks.ApiTestExtension;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static ReportPortal.Utilities.ConfigLoader.getProperty;
import static io.restassured.RestAssured.given;

@Epic("Dashboard API")
@Feature("Создание дешбордов при помощи API")
@Owner("Michael")
@Tag("API")
@ExtendWith(ApiTestExtension.class)
public class APITest {


    private static final String NAME = getProperty("dashboardAPIName");
    private static final String DESCRIPTION = getProperty("dashboardAPIDescription");
    private static final String PROJECT = getProperty("projectName");
    private static final String API_KEY = getProperty("API_KEY");
    private static final String TEMPLATE = getProperty("templateName");
    private final DashboardCreator dashboardCreator = new DashboardCreator();


    @Test
    @DisplayName("Проверка доступа к API")
    @Severity(SeverityLevel.BLOCKER)
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

    @DisplayName("Создание дешборда. Позитивный.")
    @Description("Создаем дешборд через API с заданными параметрами и проверяем, что он успешно создался.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void CreationTestPositive() {
        dashboardCreator.jsonImport(TEMPLATE);
        dashboardCreator.jsonModifier(NAME, DESCRIPTION);
        dashboardCreator.jsonRequest(PROJECT, "201", API_KEY);
        dashboardCreator.jsonVerify(PROJECT, API_KEY);
    }

    @DisplayName("Создание дешборда. Негативный.")
    @Description("Создаем дешборд с некорректными параметрами и проверяем, что он не был создан")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    void CreationTestNegative() {
        dashboardCreator.jsonImport(TEMPLATE);
        dashboardCreator.jsonModifierFaulty();
        dashboardCreator.jsonRequest(PROJECT, "400", API_KEY);
        dashboardCreator.jsonVerify(PROJECT, API_KEY);
    }
}
