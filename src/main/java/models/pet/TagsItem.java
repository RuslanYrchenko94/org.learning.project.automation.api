package models.pet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class TagsItem{

    @JsonProperty("name")
    private String tagName;

    @JsonProperty("id")
    private int tagId;

    @Override
    public String toString(){
        return
                "TagsItem{" +
                        "name = '" + tagName + '\'' +
                        ",id = '" + tagId + '\'' +
                        "}";
    }
}
