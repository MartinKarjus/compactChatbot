package controller;


import bot.temp.ChatfuelFileContentReader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class ContentController {

    private void cake() throws IOException {

    }

    @GetMapping("content")
    public String simpleAsyncTask() throws IOException {
        cake();

//        ContentReader main = new ContentReader();
//        File file = main.getFileFromResources("/json/contentForTesting/content4.json");
//
//        main.printFile(file);

        return "done";
    }



    @PostMapping("content")
    public String emojiContent(@RequestBody String body) {

        return "\uD83D\uDE0D\uD83C\uDF89\uD83D\uDC4D";

    }

}
