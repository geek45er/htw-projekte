package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController implements IDemoController {

	@Override
	public String hello(String name) {
		return "Hello " + name + "!";
	}

}
