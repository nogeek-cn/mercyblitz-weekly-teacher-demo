package com.darian.nacosconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.darian.nacosconsumer.NacosConsumerApplication.*;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired(required = false)
    private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    public String rest(@PathVariable String str) {
        return restTemplate.getForObject("http://service-provider/echo/" + str,
                String.class);
    }

    @RequestMapping(value = "/notFound-feign", method = RequestMethod.GET)
    public String notFound() {
        return echoService.notFound();
    }

    @RequestMapping(value = "/divide-feign", method = RequestMethod.GET)
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return echoService.divide(a, b);
    }

    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }

    @RequestMapping(value = "/services/{service}", method = RequestMethod.GET)
    public Object client(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public Object services() {
        return discoveryClient.getServices();
    }

    @GetMapping("service/instances/{serviceName}")
    public List<String> serviceInstances(@PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName)
                .stream()
                .map(serviceInstance -> serviceInstance.getHost() + serviceInstance.getPort())
                .collect(Collectors.toList());
    }
}
