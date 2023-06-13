package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/inquery")
@Controller
public class InquiryController {

    @GetMapping(value = "/list")
    public String inqueryList() {
        return "inquery/list";
    }
}
