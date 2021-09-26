package com.wzj.blog.myblog.controller

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping




@Controller
@Slf4j
class IndexController {

    @RequestMapping("/")
    fun index(): String? {
        return "download"
    }
}