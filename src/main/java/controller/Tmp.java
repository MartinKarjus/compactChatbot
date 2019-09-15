package controller;

import org.springframework.scheduling.annotation.Async;

public class Tmp {

    @Async
    public Integer cake() {
        for (int j = 0; j < 500; j++) {
            System.out.println(j);
        }
        return 1;
    }
}
