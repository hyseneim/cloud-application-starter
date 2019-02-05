package it.hyseneim.cloud.application.microservice.one.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@CrossOrigin(origins = "*")
	@GetMapping("/hello-world")
	@Secured("ROLE_ADMIN")
	public String helloWorld() {
		return "Hello World!";
	}

}
