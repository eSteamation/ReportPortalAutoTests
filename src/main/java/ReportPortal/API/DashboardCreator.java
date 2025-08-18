package ReportPortal.API;


import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import static ReportPortal.Utilities.ConfigLoader.getProperty;
import static io.qameta.allure.model.Parameter.Mode.MASKED;
import static org.junit.jupiter.api.Assertions.*;


public class DashboardCreator {
    protected Map<String, Object> requestBody;
    protected JsonPath jsonPath;
    protected Response response;

    protected String RandomModifier = String.valueOf(UUID.randomUUID());

    @Step("Импорт шаблона JSON: {jsonName}")
    public void jsonImport(String jsonName) {
        InputStream jsonStream = getClass().getClassLoader()
                .getResourceAsStream(jsonName);
        jsonPath = new JsonPath(jsonStream);
        assertNotNull(jsonStream);
    }

    @Step("Присвоение JSON-файлу параметров name и description")
    public void jsonModifier(String name, String description) {
        requestBody = jsonPath.getMap("$");
        requestBody.put("name", name + RandomModifier);
        requestBody.put("description", description);
    }

    @Step("Удаление параметра name у JSON-файла")
    public void jsonModifierFaulty() {
        requestBody = jsonPath.getMap("$");
        requestBody.remove("name");
    }

    @Step("Отправка запроса на создание дешборда")
    public void jsonRequest(String project, String expectedCode, @Param(mode = MASKED) String API_KEY) {
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

    @Step("Проверка результата запроса")
    public void jsonVerify(String project, @Param(mode = MASKED) String API_KEY) {
        int status = response.getStatusCode();

        if (status != 201) {
            String id = response.jsonPath().getString("id");
            assertNull(id);
            return;
        }

        int dashboardId = response.jsonPath().getInt("id");
        Response getResp = RestAssured.given()
                .baseUri(getProperty("URL_API"))
                .basePath("/api/v1/" + project + "/dashboard/" + dashboardId)
                .header("Authorization", "Bearer " + API_KEY)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = getResp.jsonPath();
        assertEquals(requestBody.get("name"), json.getString("name"));
        assertEquals(requestBody.get("description"), json.getString("description"));
    }


}
