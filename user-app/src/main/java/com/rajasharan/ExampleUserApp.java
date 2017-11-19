package com.rajasharan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by raja on 11/19/17.
 */

@SpringBootApplication
@RestController
@Slf4j
@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients
//@RibbonClient(name = "say-hello", configuration = SayHelloConfiguration.class)
public class ExampleUserApp {
    public static void main(String ...args) {
        new SpringApplicationBuilder().web(true).sources(ExampleUserApp.class).run(args);
    }

//    @LoadBalanced
//    @Bean
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hi")
    public String hi() {
        //String result = restTemplate.getForObject("http://say-hello/guest", String.class);
        String result = helloService.sayHello();
        return result;
    }
}

@FeignClient(name = "say-hello")
interface HelloService {

    @RequestMapping("/guest")
    String sayHello();
}
