package petStore.User.UserDataProvider;

import org.testng.annotations.DataProvider;

import static config.enums.PetStoreEndpoint.USER;
import static globalConstants.Constants.*;

public class PetStoreUserDataProvider {
    @DataProvider(name = "postPetStoreUser")
    public Object[][] postPetStoreUserWithParam() {
        return new Object[][]{
                // valid case
                {USER.getEndpoint(), CODE_OK, "json.schema.PetStore/receiveResponsePostUser.json", PetStoreUserValidBody},
                {USER.getEndpoint(), 400, "json.schema.PetStore/receiveResponsePostUser.json", PetStoreUserWithInvalidBody},
        };
    }
    @DataProvider(name = "getPetStoreUserByUserName")
    public Object[][] getPetStoreUserWithParam() {
        return new Object[][]{
                // valid case
                {USER.getEndpoint(), CODE_OK, Username, "json.schema.PetStore/receiveResponseGetUser.json"},
                // no record found
                {USER.getEndpoint(), 404, Username, "json.schema.PetStore/receiveResponseGetNotFound.json"},
                //invalid param
                {USER.getEndpoint(), 405, Username, "json.schema/"}
        };
    }
    @DataProvider(name = "deletePetStoreUserByUserName")
    public Object[][] deletePetStoreUserWithParam() {
        return new Object[][]{
                // valid case
                {USER.getEndpoint(), CODE_OK, Username, "json.schema.PetStore/receiveResponseDelete.json"},
                // no record found
                {USER.getEndpoint(), 404, Username, "json.schema/"},
                //invalid param
                {USER.getEndpoint(), 405, Username, "json.schema/"}
        };
    }
}
