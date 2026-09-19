package sellist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sellist.wrapper.ResponseWrapper;
import sellist.wrapper.Metadata;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.time.Instant;
import java.util.Arrays;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/hello")
    public ResponseWrapper<Map<String,String>> hello() {
        Metadata meta = new Metadata(UUID.randomUUID().toString(), Instant.now().toEpochMilli(), "1.0.0");
        Map<String,String> payload = Map.of("message", "Hello, world!");
        return new ResponseWrapper<>(meta, payload);
    }

    @GetMapping("/items")
    public ResponseWrapper<List<String>> items() {
        Metadata meta = new Metadata(UUID.randomUUID().toString(), Instant.now().toEpochMilli(), "1.0.0");
        List<String> items = Arrays.asList("alpha", "beta", "gamma");
        return new ResponseWrapper<>(meta, items);
    }
}
