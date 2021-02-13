package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

	@RequestMapping("/find")
	public String find(Model model) {
		model.addAttribute("owner", new Owner());
		return "owners/findOwners";
	}

	@GetMapping
	public String processFindForm(Owner owner, BindingResult result, Model model){
		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		List<Owner> results = ownerService.findAllByLastNameLike("%"+ owner.getLastName() + "%");

		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			// 1 owner found
			owner = results.get(0);
			return "redirect:/owners/" + owner.getId();
		} else {
			// multiple owners found
			model.addAttribute("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping("/{ownerId}")
	public String showOwner(@PathVariable Long ownerId, Model model) {
		model.addAttribute("owner", ownerService.findById(ownerId));
		return "owners/ownerDetails";
	}

	@GetMapping("/new")
	public String creationForm(Model model) {
		model.addAttribute("owner", new Owner());
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/new")
	public String processCreationForm(@Valid Owner owner, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();
		}
	}

	@GetMapping("/{id}/edit")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("owner", ownerService.findById(id));
		return "owners/createOrUpdateOwnerForm";
	}

	@PostMapping("/{id}/edit")
	public String processUpdateForm(@PathVariable Long id, @Valid Owner owner, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			owner.setId(id);
			ownerService.save(owner);
			return "redirect:/owners/" + id;
		}
	}
}
