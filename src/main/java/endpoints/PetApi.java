package endpoints;

import io.restassured.response.Response;

public class PetApi extends ApiConfig {

    public Response createPet(String requestBody) {
        return post("pet/", requestBody);
    }

    public Response updatePet(String requestBody) {
        return put("pet/", requestBody);
    }

    public Response getPetById(int petId) {
        return get("pet/" + petId);
    }

    public Response getPetsByStatus(String status) {
        return get("pet/findByStatus?status=" + status);
    }

    public Response deletePet(int petId) {
        return delete("pet/" + petId);
    }
}
