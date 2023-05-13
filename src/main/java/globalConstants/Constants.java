package globalConstants;

import java.time.LocalDateTime;

public class Constants {
    //status codes
    public static final Integer CODE_OK = 200;
    //params
    public static final String STATUS = "status";
    //URLs
    public static final String URL = "https://petstore.swagger.io/v2";

    public static final LocalDateTime datetime = LocalDateTime.now();

    public static final Integer petID = 2023;

    public static final String PetStoreBodyWithInvalidID = "{\"id\":0000,\"category\":{\"id\":64598,\"name\":\"doggie\"},\"name\":\"PIKI\"," +
            "\"photoUrls\":[\"http://py.jpg\"]," +
            "\"tags\":[{\"id\":2312,\"name\":\"papy\"}],\"status\":\"available\"}";

    public static final String PetStoreBodyWithInvalidBody = "{\"id\":2023,\"category\":{\"id\":64598,\"name\":\"doggie\"},\"name\":\"PIKI\"," +
            "\"photoUrls\":[\"http://py.jpg\"]," +
            "\"tags\":[[{\"id\":2312,\"name\":\"papy\"}],\"status\":\"available\"}";
}
