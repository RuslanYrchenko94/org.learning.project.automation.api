package petStore.Pet.DataProvider;

import org.testng.annotations.DataProvider;
import static config.enums.PetStoreEndpoint.PET;
import static config.enums.PetStoreEndpoint.PET_FIND_BY_STATUS;
import static dataModels.PetStoreData.postCreatePet;
import static globalConstants.Constants.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PetStorePetDataProvider {
    //Integer petID = 2023;
   /* String invalidID = "{\"id\":0000,\"category\":{\"id\":64598,\"name\":\"doggie\"},\"name\":\"PIKI\"," +
            "\"photoUrls\":[\"http://py.jpg\"]," +
            "\"tags\":[{\"id\":2312,\"name\":\"papy\"}],\"status\":\"available\"}";*/
   /* String PetStoreBodyWithInvalidBody = "{\"id\":2023,\"category\":{\"id\":64598,\"name\":\"doggie\"},\"name\":\"PIKI\"," +
            "\"photoUrls\":[\"http://py.jpg\"]," +
            "\"tags\":[[{\"id\":2312,\"name\":\"papy\"}],\"status\":\"available\"}";*/
    String Body = postCreatePet(petID).toString();

    @DataProvider(name = "getPetStorePetsByStatus")
    public Object[][] getPetStorePetWithParam() {
        return new Object[][]{
                // valid case Finds Pets by sold status
                {PET_FIND_BY_STATUS.getEndpoint(), 200, STATUS, "sold", "category"},
                // valid case Finds Pets by pending status
                {PET_FIND_BY_STATUS.getEndpoint(), 200, STATUS, "pending", "category"},
                // valid case Finds Pets by available status
                {PET_FIND_BY_STATUS.getEndpoint(), 200, STATUS, "available", "category"},
                // invalid case Finds Pets by available status with invalid endpoint
                {"/pet/findByStats", 404, STATUS, "available", "type"}
        };
    }

    @DataProvider(name = "postPetStorePet")
    public Object[][] postPetStorePetWithParam() {
        return new Object[][]{
                // valid case
                {PET.getEndpoint(), 200, petID, "json.schema.PetStore/receiveResponseCreatePet.json", Body},
                //create pet with invalid ID
                {PET.getEndpoint(), 400, 0, "json.schema.PetStore/receiveResponsePostPetWithInvalidResponse.json", PetStoreBodyWithInvalidID},
                //create pet with invalid body
                {PET.getEndpoint(), 500, 0, "json.schema.PetStore/receiveResponsePostPetWithInvalidResponse.json", PetStoreBodyWithInvalidBody}

        };
    }

    @DataProvider(name = "deletePetStorePet")
    public Object[][] deletePetStorePetWithParam() {
        return new Object[][]{
                // valid case
                {PET.getEndpoint(), 200, petID, "json.schema.PetStore/receiveResponseDeletePetResponse.json"},
                {PET.getEndpoint(), 404, petID, "json.schema.PetStore/receiveResponseDeletePetResponse.json"}
        };
    }
}
