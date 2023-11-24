package com.example.webfluxthreads;

import com.example.webfluxthreads.Aspect.ApiSecured;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DemoWebfluxController {


    @GetMapping("/public")
    public Mono<String> sendTestdata(){

        return Mono.just("Testdata");
    }

    @ApiSecured(apiKey = "geko")
    @GetMapping("/elevated")
    public Mono<String> gekosecured(){
        System.out.println("Info  geko");


        return Mono.just("geko ");
    }

    @ApiSecured(apiKey = "agent")
    @GetMapping("/elevated-agent")
    public Mono<String> agentsecured(){
        //System.out.println("Info  " + key);


        return Mono.just("geko ");
    }
}
