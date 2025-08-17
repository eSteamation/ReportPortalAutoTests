package ReportPortal.API;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.InputStream;
import java.util.Map;

import static ReportPortal.Utilities.ConfigLoader.getProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DashboardCreator {
    protected Map<String, Object> requestBody;
    protected JsonPath jsonPath;
    protected Response response;

    public void jsonImport(String jsonName) {
        InputStream jsonStream = getClass().getClassLoader()
                .getResourceAsStream(jsonName);
        jsonPath = new JsonPath(jsonStream);
        assertNotNull(jsonStream);
    }

    public void jsonModifier(String name, String description) {
        requestBody = jsonPath.getMap("$");
        requestBody.put("name", name); // Не забыть сделать имя случайным, чтобы не выбивало 409
        requestBody.put("description", description);
    }

    public void jsonRequest(String project, String expectedCode, String API_KEY) {
        response = RestAssured.given()
                .baseUri(getProperty("URL_API"))
                .basePath("/api/v1/" + project + "/dashboard")
                .header("Authorization", "Bearer " + API_KEY)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(Integer.parseInt(expectedCode))
                .extract()
                .response();
    }

    public void jsonVerify(String name, String description) {

    }

}
