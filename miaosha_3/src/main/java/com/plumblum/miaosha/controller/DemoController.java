package com.plumblum.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.plumblum.miaosha.result.CodeMsg;
import com.plumblum.miaosha.result.Result;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/7
 * Time:14:26
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello,everyone");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","nihao");
        return "hello";
    }


}
