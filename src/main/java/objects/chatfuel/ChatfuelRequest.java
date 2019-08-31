package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelRequest {
    @JsonProperty("user_id")
    private String chatfuelUserId;

    @JsonProperty("question_id")
    private String questionId;

    @JsonProperty("token")
    private String token;
}
