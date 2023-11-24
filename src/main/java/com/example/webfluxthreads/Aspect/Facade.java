package com.example.webfluxthreads.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;

@Slf4j
@Component
@Aspect
public class Facade {


    @Autowired
    Environment env;


    //nur Requests von diesem Server (gemockt in Postman) können auf die API zugreifen
    Map<String, String> vault  = new HashMap<String, String>() {{
        put("http://www.server1.com/test", "myvalue1");
        put("http://www.server2.com/test", "othervalue");
    }};


    @Before("@annotation(ApiSecured) && args(apikey, exchange)")
    public void checkApiKey(JoinPoint joinPoint, String apikey, ServerWebExchange exchange) throws Exception{
        System.out.println("In Facade logging" +         joinPoint.getArgs().length);
        URI servername = exchange.getRequest().getURI();


        //System.out.println("Enviroment ist" +         env.getProperty("apikey.first.value"));
        //System.out.println("Address from Aspect  " +  servername);

        //currently not needed
        //String server = servername.toString();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String declaredApikeyType = signature.getMethod().getAnnotation(ApiSecured.class).apiKey();

        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        //extract api key from request headder
        String keyValue = "";
        for (int i = 0; i < parameterNames.length; i++) {
            System.out.println("Request Parameter: " +parameterValues[i]  + "    of   "    +  parameterNames[i]);
            if (parameterNames[i].equals("apikey")){
                keyValue = parameterValues[i].toString();
            }

        }


        //match api Key to value in enviroments or throw error
        String declaredApikeyValue = env.getProperty("apikey."+declaredApikeyType);
        System.out.println("API Key value requested by method:  " + declaredApikeyType );
        System.out.println("API Key value from enviroment:  " + env.getProperty("apikey."+declaredApikeyType) );
        System.out.println("Actual delivered API Key:  " + keyValue );


        /*
        if(!vault.get(server).equals(keyValue)){
            throw new MyCustomException ("keys do not match");
        }

         */
        //String expectedKey = signature.getMethod().getAnnotation(ApiKeyProtected.class).apiKey();
        //System.out.println("Server Name  " +  server + "     value" +vault.get(server));


        //Arrays.stream(parameters).forEach(System.out::println);

        System.out.println("In Facade logging END" );
        /*
        String realApiKey = parser.getRealApiKey();

        log.info("logging signature: " + String.valueOf(signature));
        log.info("expected key:" + expectedKey);
        log.info("real key:" + parser.getRealApiKey());

        KeyRegistry registry = new KeyRegistry();
        registry.check(realApiKey);

         */







    }

}
