package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/health")
public class BogusHealthController {
    @GetMapping(produces = "application/json")
    public String health() {
        return "{\"status\":\"UP\",\"timeEntry\":{\"status\":\"UP\"},\"diskSpace\":{\"status\":\"UP\",\"total\":250790436864,\"free\":148118069248,\"threshold\":10485760},\"db\":{\"status\":\"UP\",\"database\":\"MySQL\",\"hello\":1}}";
    }
}
