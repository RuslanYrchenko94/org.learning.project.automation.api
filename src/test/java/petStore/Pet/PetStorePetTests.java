package petStore.Pet;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
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
    public void postPetStorePetTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema, String body) {

        Response pet =given().spec(specForRequest)
                .body(body)
                .when().post(format("%s%s", URL, endpoint));
        pet.then().spec(specForResponse).statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
        if(pet.statusCode() == 200){
            deletePet(petID, endpoint);
        }

    }
   /*@Test(description = "Deletes a pet with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "deletePetStorePet")
   public void deletePetTest(Integer petID, String endpoint) {

        given().spec(specForRequest)
                .when().delete(format("%s%s%s", URL, endpoint, petID))
                .then().log().all()
                .spec(specForResponse)
                .body("message", equalTo(petID.toString()));
    }*/
    private void deletePet(Integer petID, String endpoint) {
        given().when().delete(format("%s%s%s", URL, endpoint, petID));
    }
    private void createPet(Integer petID, String endpoint) {
        given().body(postCreatePet(petID))
                .when().post(format("%s%s", URL, endpoint));
    }
}
