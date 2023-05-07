package petStore.Pet.DataProvider;

import org.testng.annotations.DataProvider;
import static config.enums.PetStoreEndpoint.PET;
import static config.enums.PetStoreEndpoint.PET_FIND_BY_STATUS;
import static globalConstants.Constants.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
                {PET.getEndpoint(), 200, 2023, "json.schema.PetStore/receiveResponseCreatePet.json"}
        };
    }
}
