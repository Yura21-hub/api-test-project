package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import endpoints.PetApi;
import io.restassured.response.Response;
import models.PetModel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtil;

public class UpdatePetTests extends TestUtil {
    private PetApi petApi;
    private PetModel petModel;

    @BeforeClass
    public void setup() throws Exception {
        petApi = new PetApi();
        petModel = new PetModel();
        String requestBody = readFileAsString("src/test/resources/createPet/requestSuccessCreatePet.json");
        petApi.createPet(requestBody);
    }

    @AfterClass
    public void cleanUpData() {
        petApi.deletePet(petModel.getId());
    }

    @Test
    public void testSuccessUpdatePet() throws Exception {
        String requestBody = readFileAsString("src/test/resources/updatePet/requestSuccessUpdatePet.json");
        String responseBody = readFileAsString("src/test/resources/updatePet/responseUpdatePet.json");
        ObjectMapper mapper = new ObjectMapper();
        petModel = mapper.readValue(responseBody, PetModel.class);
        Response response = petApi.updatePet(requestBody);
        assertStatusCode(response, 200);
        verifyPetResponseBody(response, petModel);
    }

    @Test
    public void testUpdatePetWithInvalidId() throws Exception {
        String requestBody = readFileAsString("src/test/resources/updatePet/requestInvalidIdUpdatePet.json");
        Response response = petApi.updatePet(requestBody);
        assertStatusCode(response, 400);
        assertResponseMessage(response, "Input error: unable to convert input to io.swagger.petstore.model.Pet");
    }

    @Test
    public void testUpdatePetWithNotFoundId() throws Exception {
        String requestBody = readFileAsString("src/test/resources/updatePet/requestNotFoundIdUpdatePet.json");
        Response response = petApi.updatePet(requestBody);
        assertStatusCode(response, 404);
        assertBodyResponse(response, "Pet not found");
    }

    @Test
    public void testUpdatePetWithEmptyBody() throws Exception {
        Response response = petApi.updatePet("{}");
        assertStatusCode(response, 500);
        assertResponseMessage(response, "There was an error processing your request. It has been logged");
    }

}
