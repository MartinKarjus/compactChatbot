package bot.temp;

import com.fasterxml.jackson.databind.ObjectMapper;
import objects.shared.ContentByPlatform;

import java.io.IOException;

public class Temp {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\n" +
                "  \"chatfuelResponse\": {\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"attachment\": {\n" +
                "          \"type\": \"cake\",\n" +
                "          \"payload\": {\n" +
                "            \"template_type\": \"\",\n" +
                "            \"text\": \"\",\n" +
                "            \"top_element_style\": \"\",\n" +
                "            \"url\": \"\",\n" +
                "            \"image_aspect_ratio\": \"\",\n" +
                "            \"buttons\": [\n" +
                "              {\n" +
                "                \"type\": \"\",\n" +
                "                \"title\": \"\",\n" +
                "                \"url\": \"\",\n" +
                "                \"block_names\": [],\n" +
                "                \"set_attributes\": {}\n" +
                "              }\n" +
                "            ],\n" +
                "            \"elements:\": [\n" +
                "              {\n" +
                "                \"media_type\": \"\",\n" +
                "                \"url\": \"\",\n" +
                "                \"image_url\": \"\",\n" +
                "                \"subtitle\": \"\",\n" +
                "                \"title\": \"\",\n" +
                "                \"default_action\": {\n" +
                "                  \"type\": \"\",\n" +
                "                  \"url\": \"\",\n" +
                "                  \"messenger_extensions\": false\n" +
                "                },\n" +
                "                \"buttons\": [\n" +
                "                  {\n" +
                "                    \"type\": \"\",\n" +
                "                    \"title\": \"\",\n" +
                "                    \"url\": \"\",\n" +
                "                    \"block_names\": [],\n" +
                "                    \"set_attributes\": {}\n" +
                "                  }\n" +
                "                ]\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"skypeResponse\": {\n" +
                "    \"foo\": \"bar\"\n" +
                "  }\n" +
                "}\n" +
                "\n";
        ContentByPlatform contentByPlatform = objectMapper.readValue(json, ContentByPlatform.class);
        System.out.println(contentByPlatform.getChatfuelResponse().getMessages().get(0).getAttachment().getType());


    }
}
