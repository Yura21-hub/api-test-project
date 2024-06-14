package utils;

import io.restassured.response.Response;
import models.PetModel;
import org.testng.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {
    protected void assertStatusCode(Response response, int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    protected void assertResponseMessage(Response response, String message) {
        Assert.assertTrue(response.getBody().jsonPath().get("message").toString().contains(message));
    }

    protected void assertBodyResponse(Response response, String message) {
        Assert.assertTrue(response.getBody().asString().contains(message));
    }

    protected void assertEqualsString(String string1, String string2) {
        Assert.assertEquals(string1, string1);
    }

    protected void verifyPetResponseBody(Response response, PetModel expectedPet) {
        PetModel actualPet = response.as(PetModel.class);
        Assert.assertEquals(actualPet.getId(), expectedPet.getId());
        Assert.assertEquals(actualPet.getName(), expectedPet.getName());
        Assert.assertEquals(actualPet.getCategory().getId(), expectedPet.getCategory().getId());
        Assert.assertEquals(actualPet.getCategory().getName(), expectedPet.getCategory().getName());
        Assert.assertEquals(actualPet.getPhotoUrls(), expectedPet.getPhotoUrls());
        Assert.assertEquals(actualPet.getTags().size(), expectedPet.getTags().size());
        Assert.assertEquals(actualPet.getTags().get(0).getId(), expectedPet.getTags().get(0).getId());
        Assert.assertEquals(actualPet.getTags().get(0).getName(), expectedPet.getTags().get(0).getName());
        Assert.assertEquals(actualPet.getStatus(), expectedPet.getStatus());
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
