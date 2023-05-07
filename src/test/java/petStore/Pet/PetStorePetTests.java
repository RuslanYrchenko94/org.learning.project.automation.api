package petStore.Pet;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.mustache.Value;
import petStore.BaseTest;
import petStore.Pet.DataProvider.PetStorePetDataProvider;

import static dataModels.PetStoreData.postCreatePet;
import static globalConstants.Constants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class PetStorePetTests extends BaseTest {

    @Test(description = "Finds Pets by status", dataProviderClass = PetStorePetDataProvider.class, dataProvider = "getPetStorePetsByStatus")
    public void getPetStorePetsByStatusTest(String endpoint, Integer statusCode, String paramKey, String paramValue, String bodyKey) {
        given().spec(specForRequest)
                .param(paramKey, paramValue)
                .when().get(format("%s%s", URL, endpoint))
                .then()
                .spec(specForResponse)
                .statusCode(statusCode)
                .body(bodyKey, is(notNullValue()));
    }
    @Test(description = "Updates a pet in the store with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "postPetStorePet")
    public void postPetStorePetTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema) {

        given().spec(specForRequest)
                .body(postCreatePet(petID))
                .when().post(format("%s%s", URL, endpoint))
                .then().spec(specForResponse).statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
        deletePet(petID, endpoint);

    }
    private void deletePet(Integer petID, String endpoint) {

        given().spec(specForRequest)
                .when().delete(format("%s%s%s", URL, endpoint, petID))
                .then().log().all()
                .spec(specForResponse)
                .body("message", equalTo(petID.toString()));
    }
}
