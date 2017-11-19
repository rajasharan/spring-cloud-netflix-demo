### This is a simple project to demonstrate the usage of `spring-cloud-netflix` modules

### Project is divided into three maven sub-modules
- eureka-server
- eureka-client
- user-app

### Build the jars

```sh
./mvnw clean install

# or to build individual module
./mvnw -pl eureka-client clean install
```

### Start the eureka discovery server

```sh
java -jar -DPORT=8761 eureka-server/target/eureka-server*.jar

...
2017-11-19 09:28:02.285  INFO 68034 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8761 (http)
2017-11-19 09:28:02.286  INFO 68034 --- [           main] .s.c.n.e.s.EurekaAutoServiceRegistration : Updating port to 8761
2017-11-19 09:28:02.290  INFO 68034 --- [           main] com.rajasharan.server.ServerApp          : Started ServerApp in 15.353 seconds (JVM running for 16.971)
...
```

### Register new eureka-clients with the server

```sh
java -jar -DPORT=8081 eureka-client/target/eureka-client*.jar

...
2017-11-19 09:48:28.729  INFO 70021 --- [nfoReplicator-0] com.netflix.discovery.DiscoveryClient    : DiscoveryClient_TEST-CLIENT/rs-macbook-air.home:test-client:8081: registering service...
2017-11-19 09:48:28.793  INFO 70021 --- [nfoReplicator-0] com.netflix.discovery.DiscoveryClient    : DiscoveryClient_TEST-CLIENT/rs-macbook-air.home:test-client:8081 - registration status: 204
2017-11-19 09:48:28.933  INFO 70021 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8081 (http)
2017-11-19 09:48:28.934  INFO 70021 --- [           main] .s.c.n.e.s.EurekaAutoServiceRegistration : Updating port to 8081
2017-11-19 09:48:28.938 DEBUG 70021 --- [           main] com.rajasharan.EurekaClientApplication   : Hello world: {}
2017-11-19 09:48:28.942  INFO 70021 --- [           main] com.rajasharan.EurekaClientApplication   : Started EurekaClientApplication in 9.132 seconds (JVM running for 9.89)
...
```

Run more clients on different ports

```sh
java -jar -DPORT=8082 eureka-client/target/eureka-client*.jar
java -jar -DPORT=8083 eureka-client/target/eureka-client*.jar
java -jar -DPORT=8084 eureka-client/target/eureka-client*.jar
java -jar -DPORT=8085 eureka-client/target/eureka-client*.jar
```

### Eureka dashboard
Navigate to [localhost:8761/](http://localhost:8761/) to see all the registered client servers.
The client ServiceName is set as `test-client`.

### Start the user-app

```sh
java -jar -DPORT=8888 user-app/target/user-app*.jar

...
2017-11-19 09:36:56.438  INFO 69106 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8888 (http)
2017-11-19 09:36:56.439  INFO 69106 --- [           main] .s.c.n.e.s.EurekaAutoServiceRegistration : Updating port to 8888
2017-11-19 09:36:56.869  INFO 69106 --- [           main] c.n.u.concurrent.ShutdownEnabledTimer    : Shutdown hook installed for: NFLoadBalancer-PingTimer-say-hello
2017-11-19 09:36:56.962  INFO 69106 --- [           main] c.netflix.loadbalancer.BaseLoadBalancer  : Client: say-hello instantiated a LoadBalancer: DynamicServerListLoadBalancer:{NFLoadBalancer:name=say-hello,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:null
2017-11-19 09:36:56.977  INFO 69106 --- [           main] c.n.l.DynamicServerListLoadBalancer      : Using serverListUpdater PollingServerListUpdater
2017-11-19 09:36:56.985  INFO 69106 --- [           main] c.n.l.DynamicServerListLoadBalancer      : DynamicServerListLoadBalancer for client say-hello initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=say-hello,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:DiscoveryEnabledNIWSServerList:; clientName:say-hello; Effective vipAddresses:test-client-1; isSecure:false; datacenter:null
2017-11-19 09:56:21.700  INFO 70536 --- [           main] c.n.l.DynamicServerListLoadBalancer      : DynamicServerListLoadBalancer for client say-hello initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=say-hello,current list of Servers=[rs-macbook-air.home:8083, rs-macbook-air.home:8081, rs-macbook-air.home:8082],
...
2017-11-19 09:56:21.783  INFO 70536 --- [           main] com.rajasharan.ExampleUserApp            : Started ExampleUserApp in 13.746 seconds (JVM running for 14.489)
...
```

### how user-app is configured
`localhost:8888/users/**` ----------------> `[eureka-client]/**` (via Zuul routes)
 - [localhost:8888/users/guest](http://localhost:8888/users/guest) --------------> `[eureka-client]/guest`
 - [localhost:8888/users/env](http://localhost:8888/users/env) -------------> `[eureka-client]/env`

[localhost:8888/hi](http://localhost:8888/hi) -------------> `[eureka-client]/guest` (via Feign/Ribbon clients)

### References and useful links
- [spring-cloud-netflix homepage](https://cloud.spring.io/spring-cloud-netflix/)
- [spring-cloud-netflix single page reference](http://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html)
- [Eureka Registration & Discovery guide](https://spring.io/guides/gs/service-registration-and-discovery/)
- [Load Balancing with Ribbon guide](https://spring.io/guides/gs/client-side-load-balancing/)
- [Zuul Routes & Filters guide](https://spring.io/guides/gs/routing-and-filtering/)
