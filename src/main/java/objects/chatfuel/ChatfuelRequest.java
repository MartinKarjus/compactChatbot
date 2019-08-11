package objects.chatfuel;

import lombok.Data;

@Data
public class ChatfuelRequest {
    private String id;

    public String getId() {
        return id;
    }

}
