package com.darian.nacosprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;



@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @RestController
    class EchoController {
        @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
        public String echo(@PathVariable String string) {
            return "hello Nacos Discovery " + string;
        }

        @RequestMapping(value = "/divide", method = RequestMethod.GET)
        public String divide(@RequestParam Integer a, @RequestParam Integer b) {
            return String.valueOf(a / b);
        }
    }
}
