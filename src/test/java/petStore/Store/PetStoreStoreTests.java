package petStore.Store;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import petStore.BaseTest;
import petStore.Store.StoreDataProvider.PetStoreStoreDataProvider;

import static globalConstants.Constants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class PetStoreStoreTests extends BaseTest {
    @Test(description = "Returns pet inventories by status", dataProviderClass = PetStoreStoreDataProvider.class, dataProvider = "getPetStoreStoreInventory")
    public void getPetStoreInventoryTest(String endpoint, Integer statusCode, String jsonSchema) {
        given().spec(specForRequestCTJson)
                .when().get(format("%s%s", URL, endpoint))
                .then()
                .spec(specForResponse)
                .statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
    }
    @Test(description = "Place an order for a pet", dataProviderClass = PetStoreStoreDataProvider.class, dataProvider = "postPetStoreOrder")
    public void postPetStoreOrderTest(String endpoint, Integer statusCode, String jsonSchema, String body) {
        Response createOrder = given().spec(specForRequestCTJson)
                .body(body)
                .when().post(format("%s%s", URL, endpoint));
        createOrder.then().spec(specForResponse).statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema));
        if (createOrder.statusCode() == 200) {
            deleteOrderById(orderID, endpoint);
        }
    }
    @Test(description = "Find order by ID with form data",dataProviderClass = PetStoreStoreDataProvider.class, dataProvider = "getPetStoreOrderById")
    public void getPetStoreOrderByIDTest(String endpoint, Integer statusCode, Integer orderID, String jsonSchema) {

        if(statusCode == CODE_OK){
            createOrder(endpoint, PetStoreStoreOrderValidBody);
            Response getOrder =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s%s", URL, endpoint, orderID));
            getOrder.then().spec(specForResponse).statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("id", equalTo(orderID));
            deleteOrderById(orderID, endpoint);
        }
        else if (statusCode == 404) {
            Response getPet =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s%s", URL, endpoint, orderID));
            getPet.then().log().all().statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("message", equalTo("Order not found"));
        }
        else {
            Response getPet =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s", URL, endpoint));
            getPet.then().log().all().statusCode(statusCode).contentType(ContentType.XML);
        }
    }
    @Test(description = "Deletes a order with form data",dataProviderClass = PetStoreStoreDataProvider.class, dataProvider = "deletePetStoreOrder")
    public void deleteOrderByIDTest(String endpoint, Integer statusCode, Integer orderId, String jsonSchema) {

        if(statusCode == CODE_OK){
            createOrder(endpoint, PetStoreStoreOrderValidBody);
        }
        Response deleteOrder =given().spec(specForRequestCTJson)
                .when().delete(format("%s%s%s", URL, endpoint, orderId));
        if(statusCode == CODE_OK){
            deleteOrder.then().log().all().statusCode(statusCode)
                    .body(matchesJsonSchemaInClasspath(jsonSchema))
                    .body("message", equalTo(orderId.toString()));}
        else if (statusCode == 404){
            deleteOrder.then().log().all().statusCode(statusCode)
                .body(matchesJsonSchemaInClasspath(jsonSchema))
                .body("message", equalToIgnoringCase("Order not found"));
        }
        else {
            Response getPet =given().spec(specForRequestCTJson)
                    .when().get(format("%s%s", URL, endpoint));
            getPet.then().log().all().statusCode(statusCode).contentType(ContentType.XML);}
    }
    private void deleteOrderById(Integer orderID, String endpoint) {
        given().when().delete(format("%s%s%s", URL, endpoint, orderID));
    }
    private void createOrder(String endpoint, String body) {
        given().spec(specForRequestCTJson).body(body)
                .when().post(format("%s%s", URL, endpoint))
                .then().log().all();
    }
}
