package sellist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ExceptionTestController
{

    @GetMapping("/illegal-arg")
    public String illegalArg()
        {
        throw new IllegalArgumentException("Invalid parameter provided");
        }

    @GetMapping("/runtime-error")
    public String runtimeError()
        {
        throw new RuntimeException("An unexpected error occurred");
        }
}
