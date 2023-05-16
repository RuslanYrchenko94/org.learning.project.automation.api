package petStore.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import petStore.BaseTest;
import petStore.User.UserDataProvider.PetStoreUserDataProvider;

import static globalConstants.Constants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreUserTests extends BaseTest {

    @Test(description = "Create user", dataProviderClass = PetStoreUserDataProvider.class, dataProvider = "postPetStoreUser")
    public void postPetStoreUserTest(String endpoint, Integer statusCode, String jsonSchema, String body) {
        Response createUser = given().spec(specForRequestCTJson)
                .body(body)
                .when().post(format("%s%s", URL, endpoint));
        if (createUser.statusCode() == 200) {
        createUser.then().spec(specForResponse).statusCode(CODE_OK)
                .body(matchesJsonSchemaInClasspath(jsonSchema))
                .body("message", equalTo(userID.toString()));
            deleteUserByUsername(Username, endpoint);
        }
        else {
            createUser.then().log().all().statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("message", equalTo("bad input"));
        }
    }
    @Test(description = "Create user", dataProviderClass = PetStoreUserDataProvider.class, dataProvider = "getPetStoreUserByUserName")
    public void getPetStoreUserByUserName(String endpoint, Integer statusCode, String Username, String jsonSchema){

        if(statusCode.equals(CODE_OK)){
            createUser(endpoint);
            Response getUser =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s%s", URL, endpoint, Username));
            getUser.then().spec(specForResponse).statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("id", equalTo(userID))
                    .body("username", equalTo(Username));
            deleteUserByUsername(Username, endpoint);
        }
        else if (statusCode.equals(404)) {
            Response getUser =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s%s", URL, endpoint, Username));
            getUser.then().log().all().statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("message", equalTo("User not found"));
        }
        else {
            Response getUser =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s", URL, endpoint));
            getUser.then().log().all().statusCode(statusCode).contentType(ContentType.XML);
        }
    }
    @Test(description = "Create user", dataProviderClass = PetStoreUserDataProvider.class, dataProvider = "deletePetStoreUserByUserName")
    public void deletePetStoreUserByUserName(String endpoint, Integer statusCode, String Username, String jsonSchema){

        if(statusCode.equals(CODE_OK)){
            createUser(endpoint);
            Response deleteUser =given().spec(specForRequestCTJson)
                    .when().delete(format("%s%s%s", URL, endpoint, Username));
            deleteUser.then().spec(specForResponse).statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("message", equalTo(Username));
        }
        else if (statusCode.equals(404)) {
            Response deleteUser =given().spec(specForRequestCTJson)
                    .when().delete(format("%s%s%s", URL, endpoint, Username));
            deleteUser.then().log().all().statusCode(statusCode);
        }
        else {
            Response deleteUser =given().spec(specForRequestCTJson)
                    .when().delete(format("%s%s", URL, endpoint));
            deleteUser.then().log().all().statusCode(statusCode).contentType(ContentType.XML);
        }
    }
    private void deleteUserByUsername(String username, String endpoint) {
        given().when().delete(format("%s%s%s", URL, endpoint, username));
    }
    private void createUser(String endpoint) {
        given().spec(specForRequestCTJson).body(PetStoreUserValidBody)
                .when().post(format("%s%s", URL, endpoint));
    }
}
