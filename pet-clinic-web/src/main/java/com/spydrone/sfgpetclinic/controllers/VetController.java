package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.model.Vet;
import com.spydrone.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {

	private final VetService vetService;

	public VetController(VetService vetService) {
		this.vetService = vetService;
	}

	@GetMapping({"/vets", "vets/index", "/vets/index.html", "/vets.html"})
	public String list(Model model) {
		model.addAttribute("vets", vetService.findAll());
		return "vets/index";
	}

	@GetMapping("/api/vets")
	@ResponseBody
	public Set<Vet> getVets() {
		return vetService.findAll();
	}
}
