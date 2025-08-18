package ReportPortal.Hooks;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

import static ReportPortal.Utilities.ConfigLoader.getProperty;

public class ApiTestExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(@SuppressWarnings("NullableProblems") ExtensionContext context) {
        RestAssured.baseURI = getProperty("URL_API");
        LogConfig logConfig = LogConfig.logConfig().blacklistHeaders(List.of("Authorization"));
        RestAssured.config = RestAssured.config().logConfig(logConfig);
        RestAssured.filters(new AllureRestAssured());
    }
}
