package tests;

import endpoints.PetApi;
import io.restassured.response.Response;
import models.PetModel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtil;

public class DeletePetTests extends TestUtil {
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
    public void testSuccessDeletePet() {
        Response response = petApi.deletePet(petModel.getId());
        assertStatusCode(response, 200);
        assertBodyResponse(response, "Pet deleted");
    }
}
