package com.sv.controllers;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/asynctest")
@CrossOrigin
@RestController
public class AsyncTestController {

	@GetMapping
	public Callable<String> getAtInterval() {
		
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				Thread.sleep(3000);
				return "success";				
			}		
			
			
		};
		
		
	}

}
