package models.pet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class PetStorePetModel {

    @JsonProperty("photoUrls")
    private String photoUrls;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("tags")
    private List<TagsItem> tags;

    @JsonProperty("status")
    private String status;

    @Override
    public String toString() {
        return
                "{\"id\":" + id + "," +
                        "\"category\":" + category + "," +
                        "\"name\":\"" + name + "\"," +
                        "\"photoUrls\":[\"" + photoUrls + "\"]," +
                        "\"tags\":" + tags + "," +
                        "\"status\":\"" + status + "\"}";
    }
}
