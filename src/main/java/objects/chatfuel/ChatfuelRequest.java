package objects.chatfuel;

import lombok.Data;

@Data
public class ChatfuelRequest {
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

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getLocale() {
        return locale;
    }

    public String getSource() {
        return source;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public String getSigned_up() {
        return signed_up;
    }

    public String getSessions() {
        return sessions;
    }

    public String getLast_visited_block_name() {
        return last_visited_block_name;
    }

    public String getLast_visited_block_id() {
        return last_visited_block_id;
    }

    public String getLast_clicked_button_name() {
        return last_clicked_button_name;
    }
}
