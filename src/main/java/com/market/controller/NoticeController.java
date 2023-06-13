package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/notice")
@Controller
public class NoticeController {

    @GetMapping(value = "/list")
    public String noticeList() {
        return "notice/list";
    }
}
