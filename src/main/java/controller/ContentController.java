package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import util.contentreader.CompactContentUpdater;

@RestController
public class ContentController {

    @Autowired
    private CompactContentUpdater compactContentUpdater;


    @GetMapping("content")
    public String updateContent(){
        compactContentUpdater.updateContent();

        return "done";
    }


}
