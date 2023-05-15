package petStore.Store.StoreDataProvider;

import org.testng.annotations.DataProvider;

import static config.enums.PetStoreEndpoint.*;
import static globalConstants.Constants.*;

public class PetStoreStoreDataProvider {
    @DataProvider(name = "getPetStoreStoreInventory")
    public Object[][] getPetStoreStoreInventoryOWithParam() {
        return new Object[][]{
                // valid case
                {STORE_INVENTORY.getEndpoint(), CODE_OK, "json.schema.PetStore/receiveResponseGetStoreInventory.json"},
        };
    }
    @DataProvider(name = "postPetStoreOrder")
    public Object[][] postPetStoreOrderWithParam() {
        return new Object[][]{
                // valid case
                {STORE_ORDER.getEndpoint(), CODE_OK, "json.schema.PetStore/receiveResponsePostOrder.json", PetStoreStoreOrderValidBody},
                {STORE_ORDER.getEndpoint(), 400, "json.schema.PetStore/receiveResponsePostWithInvalidBody.json", PetStoreStoreOrderWithInvalidBody},
        };
    }
    @DataProvider(name = "getPetStoreOrderById")
    public Object[][] getPetStoreOrderByIdWithParam() {
        return new Object[][]{
                // valid case
                {STORE_ORDER.getEndpoint(), CODE_OK, orderID, "json.schema.PetStore/receiveResponsePostOrder.json"},
                // no record found
                {STORE_ORDER.getEndpoint(), 404, orderID, "json.schema.PetStore/receiveResponseGetNotFound.json"},
                //invalid param
                {STORE_ORDER.getEndpoint(), 405, orderID, "json.schema/"}
        };
    }
    @DataProvider(name = "deletePetStoreOrder")
    public Object[][] deletePetStorePetByIdWithParam() {
        return new Object[][]{
                // valid case
                {STORE_ORDER.getEndpoint(), CODE_OK, orderID, "json.schema.PetStore/receiveResponseDeletePetOrOrder.json"},
                // no record found
                {STORE_ORDER.getEndpoint(), 404, orderID, "json.schema.PetStore/receiveResponseDeletePetOrOrder.json"},
                //invalid param
                {STORE_ORDER.getEndpoint(), 405, orderID, "json.schema/"}
        };
    }

}
