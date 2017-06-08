package com.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CircuitBreakerService {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getBackupGuide", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10") })
	public void getGuide() {
		System.out.println("<inside getGuid>");
		restTemplate.getForObject("http://microservice1.cognizantone.org/", String.class);
		restTemplate.getForObject("http://microservice2.cognizantone.org/", String.class);
		restTemplate.getForObject("http://microservice3.cognizantone.org/", String.class);
	}

	public void getBackupGuide() {
		System.out.println("<inside getbackupGuide>");
	}
	
}