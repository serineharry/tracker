package com.sv.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/secureping")
@CrossOrigin
@RestController
public class SecurePingController {

	@GetMapping
	public String pingme() {
		return "success";
	}

}
