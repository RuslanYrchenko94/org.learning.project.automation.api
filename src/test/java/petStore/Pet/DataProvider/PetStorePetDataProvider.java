package petStore.Pet.DataProvider;

import org.testng.annotations.DataProvider;
import static config.enums.PetStoreEndpoint.PET;
import static config.enums.PetStoreEndpoint.PET_FIND_BY_STATUS;
import static globalConstants.Constants.*;


public class PetStorePetDataProvider {

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
                {PET.getEndpoint(), 200, petID, "json.schema.PetStore/receiveResponseCreatePet.json", PetStorePetBodyValidBody},
                //create pet with invalid ID
                {PET.getEndpoint(), 400, 0, "json.schema.PetStore/receiveResponsePostPetWithInvalidResponse.json", PetStorePetBodyWithInvalidID},
                //create pet with invalid body
                {PET.getEndpoint(), 500, 0, "json.schema.PetStore/receiveResponsePostPetWithInvalidResponse.json", PetStorePetBodyWithInvalidBody}

        };
    }
    @DataProvider(name = "postPetStorePetById")
    public Object[][] postPetStorePetByIdWithParam() {
        return new Object[][]{
                // valid case
                {PET.getEndpoint(), 200, petID, "json.schema.PetStore/receiveResponsePostPetByIdResponse.json", "name=Spike&status=sold"},
                // no record found
                {PET.getEndpoint(), 404, petID, "json.schema.PetStore/receiveResponseGetPetWithInvalidID.json", "name=Spike&status=sold"}
        };
    }

    @DataProvider(name = "deletePetStorePet")
    public Object[][] deletePetStorePetByIdWithParam() {
        return new Object[][]{
                // valid case
                {PET.getEndpoint(), 200, petID, "json.schema.PetStore/receiveResponseDeletePetResponse.json"},
                // no record found
                {PET.getEndpoint(), 404, petID, "json.schema.PetStore/receiveResponseDeletePetResponse.json"}
        };
    }
    @DataProvider(name = "getPetStorePet")
    public Object[][] getPetStorePetByIdWithParam() {
        return new Object[][]{
                // valid case
                {PET.getEndpoint(), 200, petID, "json.schema.PetStore/receiveResponseGetPet.json"},
                // no record found
                {PET.getEndpoint(), 404, petID, "json.schema.PetStore/receiveResponseGetPetWithInvalidID.json"},
                //invalid param
                {PET.getEndpoint(), 405, petID, "json.schema.PetStore/receiveResponseGetPetWithInvalidID.json"}
        };
    }
}
