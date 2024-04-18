package com.example.correos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.correos.entity.InfoEmail;
import com.example.correos.service.EnvioCorreoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class EnvioMailController {
	
	@Autowired
	private EnvioCorreoService service;
	
	@PostMapping("/envio")
	public InfoEmail send() {
		return service.sendEmail();
	}
	
	@GetMapping("/algo")
	public String getAlgo() {
		return "Hola esto";
	}

}
