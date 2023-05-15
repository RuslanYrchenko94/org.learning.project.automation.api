package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)

public class PetStoreOrderModel{
    @JsonProperty("petId")
    private int petId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("id")
    private int id;
    @JsonProperty("shipDate")
    private String shipDate;
    @JsonProperty("complete")
    private boolean complete;
    @JsonProperty("status")
    private String status;

    @Override
    public String toString(){
        return
                "{\"id\":"+id+"," +
                        "\"petId\":"+petId+"," +
                        "\"quantity\":"+quantity+"," +
                        "\"shipDate\":\""+shipDate+"\"," +
                        "\"status\":\""+status+"\"," +
                        "\"complete\":"+complete+"}";
    }
}
