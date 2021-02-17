package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.model.Pet;
import com.spydrone.sfgpetclinic.model.PetType;
import com.spydrone.sfgpetclinic.services.OwnerService;
import com.spydrone.sfgpetclinic.services.PetService;
import com.spydrone.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
	public static final String PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

	private final PetService petService;
	private final OwnerService ownerService;
	private final PetTypeService petTypeService;

	public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.petTypeService = petTypeService;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetType() {
		return petTypeService.findAll();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable Long ownerId) {
		return ownerService.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, Model model) {
		Pet pet = new Pet();
		owner.getPets().add(pet);
		pet.setOwner(owner);
		model.addAttribute("pet", pet);
		return PETS_CREATE_OR_UPDATE_PET_FORM;
	}

	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {
		owner.getPets().add(pet);
		if (result.hasErrors()) {
			model.addAttribute("pet", pet);
			return PETS_CREATE_OR_UPDATE_PET_FORM;
		} else {
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable Long petId, Model model) {
		Pet pet = petService.findById(petId);
		model.addAttribute("pet", pet);
		return PETS_CREATE_OR_UPDATE_PET_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, Model model) {
		if (result.hasErrors()) {
			pet.setOwner(owner);
			model.addAttribute("pet", pet);
			return  PETS_CREATE_OR_UPDATE_PET_FORM;
		} else {
			owner.getPets().add(pet);
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
}
