package sellist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/example")
public class ExampleController
{

    @GetMapping("/hello")
    public Map<String, String> hello()
        {
        return Map.of(
                "message",
                "Hello, world!");
        }

    @GetMapping("/items")
    public List<String> items()
        {
        return Arrays.asList(
                "alpha",
                "beta",
                "gamma");
        }

    @GetMapping("/text")
    public String text()
        {
        return "plain text";
        }
}
