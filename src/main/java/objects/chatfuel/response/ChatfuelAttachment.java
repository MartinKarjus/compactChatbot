package objects.chatfuel.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelAttachment {
    private String type; // video/template/audio/file
    private ChatfuelPayload payload;

}
