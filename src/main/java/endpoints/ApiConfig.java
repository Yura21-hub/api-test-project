package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiConfig {
    String baseUrl = Constants.getBaseUrl();

    protected Response post(String endpoint, Object body) {
        return RestAssured.given()
            .header("Content-Type", "application/json")
            .body(body)
            .post(baseUrl + endpoint);
    }

    protected Response put(String endpoint, Object body) {
        return RestAssured.given()
            .header("Content-Type", "application/json")
            .body(body)
            .put(baseUrl + endpoint);
    }

    protected Response get(String endpoint) {
        return RestAssured.given()
            .get(baseUrl + endpoint);
    }

    protected Response delete(String endpoint) {
        return RestAssured.given()
            .delete(baseUrl + endpoint);
    }
}
