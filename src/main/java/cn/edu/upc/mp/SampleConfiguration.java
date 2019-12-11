package cn.edu.upc.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class SampleConfiguration {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World! Dir:/";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleConfiguration.class, args);
    }
}