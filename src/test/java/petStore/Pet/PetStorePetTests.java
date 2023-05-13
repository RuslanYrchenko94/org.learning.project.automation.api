package petStore.Pet;

import io.restassured.response.Response;
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

        Response createPet =given().spec(specForRequest)
                .body(body)
                .when().post(format("%s%s", URL, endpoint));
        createPet.then().spec(specForResponse).statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
        if(createPet.statusCode() == 200){
            deletePetById(petID, endpoint);
        }

    }
   @Test(description = "Deletes a pet with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "deletePetStorePet")
   public void deletePetTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema) {

        if(statusCode == 200){
            createPet(petID, endpoint);
            getPetById(petID, endpoint);
        }
       Response deletePet =given().spec(specForRequest)
                .when().delete(format("%s%s%s", URL, endpoint, petID));
       if(statusCode == 200){
       deletePet.then().log().all().statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema))
                .body("message", equalTo(petID.toString()));}
       else {deletePet.then().log().all().statusCode(statusCode);
       }
    }
    private void deletePetById(Integer petID, String endpoint) {
        given().when().delete(format("%s%s%s", URL, endpoint, petID));
    }
    private void createPet(Integer petID, String endpoint) {
        given().spec(specForRequest).body(postCreatePet(petID).toString())
                .when().post(format("%s%s", URL, endpoint))
                .then().log().all();
    }
    private void getPetById(Integer petID, String endpoint) {
        given().log().all().get(format("%s%s%s", URL, endpoint, petID));
    }
}
