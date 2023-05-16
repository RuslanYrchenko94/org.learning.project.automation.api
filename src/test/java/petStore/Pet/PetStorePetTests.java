package petStore.Pet;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import petStore.BaseTest;
import petStore.Pet.PetDataProvider.PetStorePetDataProvider;
import io.restassured.http.ContentType;

import static globalConstants.Constants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class PetStorePetTests extends BaseTest {

    @Test(description = "Finds Pets by status", dataProviderClass = PetStorePetDataProvider.class, dataProvider = "getPetStorePetsByStatus")
    public void getPetStorePetsByStatusTest(String endpoint, Integer statusCode, String paramKey, String paramValue, String bodyKey) {
        given().spec(specForRequestCTJson)
                .param(paramKey, paramValue)
                .when().get(format("%s%s", URL, endpoint))
                .then()
                .spec(specForResponse)
                .statusCode(statusCode)
                .body(bodyKey, is(notNullValue()));
    }
    @Test(description = "Updates a pet in the store with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "postPetStorePet")
    public void postPetStorePetTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema, String body) {

        Response createPet =given().spec(specForRequestCTJson)
                .body(body)
                .when().post(format("%s%s", URL, endpoint));
        createPet.then().spec(specForResponse).statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
        if(createPet.statusCode() == 200){
            deletePetById(petID, endpoint);
        }
    }
    @Test(description = "Updates a pet in the store by id with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "postPetStorePetById")
    public void postPetStorePetByIDTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema, String body) {
        if(statusCode == 200){
            createPet(endpoint);
        }
        Response updatePetById =given().spec(specForRequestCTFormData)
                .body(body)
                .when().post(format("%s%s%s", URL, endpoint, petID));
        updatePetById.then().spec(specForResponse).statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
        deletePetById(petID, endpoint);

    }
    @Test(description = "Deletes a pet with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "deletePetStorePet")
    public void deletePetByIDTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema) {

       if(statusCode == 200){
            createPet(endpoint);
       }
       Response deletePet =given().spec(specForRequestCTJson)
                .when().delete(format("%s%s%s", URL, endpoint, petID));
       if(statusCode == 200){
       deletePet.then().log().all().statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema))
                .body("message", equalTo(petID.toString()));}
       else {deletePet.then().log().all().statusCode(statusCode);
       }
    }
    @Test(description = "Find pet by ID with form data",dataProviderClass = PetStorePetDataProvider.class, dataProvider = "getPetStorePet")
    public void getPetByIDTest(String endpoint, Integer statusCode, Integer petID, String jsonSchema) {

        if(statusCode == 200){
            createPet(endpoint);
            Response getPet =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s%s", URL, endpoint, petID));
            getPet.then().log().all().statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("id", equalTo(petID));
            deletePetById(petID, endpoint);
        }
        else if (statusCode == 404) {
            Response getPet =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s%s", URL, endpoint, petID));
            getPet.then().log().all().statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("message", equalTo("Pet not found"));
        }
        else {
            Response getPet =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s", URL, endpoint));
            getPet.then().log().all().statusCode(statusCode).contentType(ContentType.XML);
        }
    }
    private void deletePetById(Integer petID, String endpoint) {
        given().when().delete(format("%s%s%s", URL, endpoint, petID));
    }
    private void createPet(String endpoint) {
        given().spec(specForRequestCTJson).body(PetStorePetValidBody)
                .when().post(format("%s%s", URL, endpoint));
    }
}
