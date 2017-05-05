package com.sv.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ping")
@CrossOrigin
@RestController
public class PingController {

	@GetMapping()
	public String pingme() {
		return "success";

	}
}
