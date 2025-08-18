package ReportPortal.API;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import static ReportPortal.Utilities.ConfigLoader.getProperty;
import static org.junit.jupiter.api.Assertions.*;


public class DashboardCreator {
    protected Map<String, Object> requestBody;
    protected JsonPath jsonPath;
    protected Response response;

    protected String RandomModifier = String.valueOf(UUID.randomUUID());

    public void jsonImport(String jsonName) {
        InputStream jsonStream = getClass().getClassLoader()
                .getResourceAsStream(jsonName);
        jsonPath = new JsonPath(jsonStream);
        assertNotNull(jsonStream);
    }

    public void jsonModifier(String name, String description) {
        requestBody = jsonPath.getMap("$");
        requestBody.put("name", name + RandomModifier);
        requestBody.put("description", description);
    }

    public void jsonModifierFaulty() {
        requestBody = jsonPath.getMap("$");
        requestBody.remove("name");
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

    public void jsonVerify(String project, String API_KEY) {
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
