package models.pet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class Category{

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("id")
    private int categoryId;

    @Override
    public String toString(){
        return
                "Category{" +
                        "name = '" + categoryName + '\'' +
                        ",id = '" + categoryId + '\'' +
                        "}";
    }
}