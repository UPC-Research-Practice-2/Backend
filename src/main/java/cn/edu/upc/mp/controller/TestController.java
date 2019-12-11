package cn.edu.upc.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestController {
    @RequestMapping(value = "/index")
    @ResponseBody
    public String index() {
        return "Hello, World! Dir:/test/index";
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseBody
    public String show(@RequestParam(value = "name") String name) {
        return "Hello, " + name;
    }
}