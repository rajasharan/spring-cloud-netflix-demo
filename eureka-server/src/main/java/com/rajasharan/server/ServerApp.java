package com.rajasharan.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by raja on 11/19/17.
 */

@SpringBootApplication
@EnableEurekaServer
public class ServerApp {
    public static void main(String ...args) {
        new SpringApplicationBuilder()
                .web(true)
                .sources(ServerApp.class)
                .run(args);
    }
}
