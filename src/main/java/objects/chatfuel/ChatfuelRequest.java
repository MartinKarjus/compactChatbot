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
    private String messenger_user_id;
    private String first_name;
    private String last_name;
    private String gender;
    private String profile_pic_url;
    private String timezone;
    private String locale;
    private String source;
    private String last_seen;
    private String signed_up;
    private String sessions;
    private String last_visited_block_name;
    private String last_visited_block_id;
    private String last_clicked_button_name;

    public String getMessenger_user_id() {
        return messenger_user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    @JsonProperty("question_id")
    private String questionId;

    @JsonProperty("token")
    private String token;
}
