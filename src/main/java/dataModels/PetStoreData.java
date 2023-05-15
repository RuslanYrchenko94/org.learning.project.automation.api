package dataModels;

import models.PetStoreOrderModel;
import models.pet.Category;
import models.pet.PetStorePetModel;
import models.PetStoreUserModel;
import models.pet.TagsItem;


import java.util.Collections;

import static globalConstants.Constants.datetime;


public class PetStoreData {
    public static PetStoreUserModel postCreateUser(int id, String userName) {

        return new PetStoreUserModel()
                .id(id)
                .username(userName)
                .firstName("Ruslan")
                .lastName("Yurchenko")
                .email("test@gmail.com")
                .password("testUser")
                .phone("22-00-23")
                .userStatus(0);
    }

    public static PetStorePetModel postCreatePet(Integer petId) {

        return new PetStorePetModel()

                .name("Piki")
                .photoUrls("http://py.jpg")
                .id(petId)
                .category(new Category()
                        .categoryId(555)
                        .categoryName("pekines"))
                .tags(Collections.singletonList(new TagsItem()
                        .tagName("lovely dog")
                        .tagId(1903)))
                .status("available");
    }

    public static PetStoreOrderModel postCreateOrder(Integer orderID) {

        return new PetStoreOrderModel()
                .id(orderID)
                .petId(777)
                .quantity(5)
                .shipDate(datetime.toString())
                .status("placed")
                .complete(true);

    }
}