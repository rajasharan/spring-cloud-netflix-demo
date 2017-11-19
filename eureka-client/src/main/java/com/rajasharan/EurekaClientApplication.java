package com.rajasharan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableEurekaClient
//@EnableDiscoveryClient
public class EurekaClientApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaClientApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(true)
                .sources(EurekaClientApplication.class)
                .run(args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            LOGGER.debug("Hello world: {}", args);
        };
    }

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private Registration registration;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/")
    public void ping() {
        LOGGER.info("someone pinging me");
    }

    @RequestMapping("/guest")
    public Info home() {
        return new Info("raja", "henrico", registration.getServiceId(), port);
    }

    @RequestMapping("/service/{name}")
    public List<ServiceInstance> serviceInstancesByName(@PathVariable("name") String name) {
        LOGGER.debug("Registration (self-service-Id): {}", registration.getServiceId());
        LOGGER.debug("DiscoveryClient: {}", client);
        LOGGER.debug("Services: {}", client.getServices());
        LOGGER.debug("Description: {}", client.description());
        return client.getInstances(name);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Info {
    private String name;
    private String address;
    private String serviceName;
    private String port;
}
