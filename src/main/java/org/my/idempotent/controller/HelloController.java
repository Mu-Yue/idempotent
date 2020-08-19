package org.my.idempotent.controller;

import org.my.idempotent.annotation.AutoIdempotent;
import org.my.idempotent.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("getToken")
    public String getToken() {
        return tokenService.createToken();
    }

    @PostMapping("/hello")
    @AutoIdempotent
    public String hello() {
        return "hello";
    }

    @PostMapping("/hello2")
    public String hello2() {
        return "hello2";
    }

}
