package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerController {

	private final OwnerService ownerService;

	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

	@RequestMapping({"", "/index", "/index.html"})
	public String list(Model model) {
		model.addAttribute("owners", ownerService.findAll());
		return "owners/index";
	}

	@RequestMapping("/find")
	public String find() {
		return "notimplemented";
	}

	@GetMapping("/{ownerId}")
	public String showOwner(@PathVariable Long ownerId, Model model) {
		model.addAttribute("owner", ownerService.findById(ownerId));
		return "owners/ownerDetails";
	}
}
