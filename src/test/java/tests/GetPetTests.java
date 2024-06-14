package tests;

import endpoints.PetApi;
import io.restassured.response.Response;
import models.PetModel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestUtil;

import java.util.List;

public class GetPetTests extends TestUtil {
    private PetApi petApi;
    private PetModel petModel;

    @BeforeClass
    public void setup() throws Exception {
        petApi = new PetApi();
        String requestBody = readFileAsString("src/test/resources/createPet/requestSuccessCreatePet.json");
        Response response = petApi.createPet(requestBody);
        petModel = response.as(PetModel.class);
    }

    @AfterClass
    public void cleanUpData() {
        petApi.deletePet(petModel.getId());
    }

    @Test
    public void testSuccessGetPetById() {
        Response response = petApi.getPetById(petModel.getId());
        assertStatusCode(response, 200);
        verifyPetResponseBody(response, petModel);
    }

    @Test
    public void testGetPetWithNoExistId() {
        Response response = petApi.getPetById(9999);
        assertStatusCode(response, 404);
        assertBodyResponse(response, "Pet not found");
    }

    @DataProvider(name = "statusProvider")
    public static Object[][] statusProvider() {
        return new Object[][]{
            {"available"},
            {"pending"},
            {"sold"}
        };
    }

    @Test(dataProvider = "statusProvider")
    public void testSuccessGetPetByStatus(String statusName) {
        Response response = petApi.getPetsByStatus(statusName);
        assertStatusCode(response, 200);
        List<String> statuses = response.getBody().jsonPath().get("status");
        for (String status : statuses) {
            assertEqualsString(status, statusName);
        }
    }

    @Test
    public void testGetPetWithInvalidStatus() {
        Response response = petApi.getPetsByStatus("invalidStatus");
        assertStatusCode(response, 400);
        assertResponseMessage(response, "Input error: query parameter `status value `invalidStatus` is not in the allowable values `[available, pending, sold]`");
    }
}
