package objects.chatfuel;

import lombok.Data;

@Data
public class ChatfuelAttachment {
    private String type; // video/template/audio/file
    private ChatfuelPayload payload;

}
