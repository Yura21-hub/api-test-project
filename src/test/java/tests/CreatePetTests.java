package tests;

import endpoints.PetApi;
import io.restassured.response.Response;
import models.PetModel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtil;

public class CreatePetTests extends TestUtil {
    private PetApi petApi;
    private PetModel petModel;

    @BeforeClass
    public void setup() {
        petApi = new PetApi();
        petModel = new PetModel();
    }

    @AfterClass
    public void cleanUpData() {
        petApi.deletePet(petModel.getId());
    }

    @Test
    public void testSuccessCreatePet() throws Exception {
        String requestBody = readFileAsString("src/test/resources/createPet/requestSuccessCreatePet.json");
        Response response = petApi.createPet(requestBody);
        assertStatusCode(response, 200);
        petModel = response.as(PetModel.class);
        verifyPetResponseBody(response, petModel);
    }

    @Test
    public void testCreatePetWithIncorrectInputData() throws Exception {
        String requestBody = readFileAsString("src/test/resources/createPet/requestFailedCreatePet.json");
        Response response = petApi.createPet(requestBody);
        assertStatusCode(response, 400);
        assertResponseMessage(response, "Input error: unable to convert input to io.swagger.petstore.model.Pet");
    }

    @Test
    public void testFailedCreatePetWithEmptyBody() throws Exception {
        Response response = petApi.createPet("{}");
        assertStatusCode(response, 500);
        assertResponseMessage(response, "There was an error processing your request. It has been logged");
    }
}
