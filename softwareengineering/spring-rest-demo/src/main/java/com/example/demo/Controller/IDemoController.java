package com.example.demo.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Hello World", description = "Einfacher Hello-World-Endpunkt")
public interface IDemoController {

	@Operation(summary = "Gibt eine Begrüßung zurück")
	@GetMapping({"/", "/hello"})
	String hello(@RequestParam(defaultValue = "World") String name);

}
