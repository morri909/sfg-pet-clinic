package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.model.Pet;
import com.spydrone.sfgpetclinic.model.PetType;
import com.spydrone.sfgpetclinic.services.OwnerService;
import com.spydrone.sfgpetclinic.services.PetService;
import com.spydrone.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

	@Mock
	PetService petService;
	@Mock
	OwnerService ownerService;
	@Mock
	PetTypeService petTypeService;

	@InjectMocks
	PetController petController;

	MockMvc mockMvc;
	Owner owner;
	Set<PetType> petTypes;

	@BeforeEach
	void setUp() {
		owner = Owner.builder().id(1L).build();
		petTypes = new HashSet<>();
		petTypes.add(PetType.builder().id(1L).name("Dog").build());
		petTypes.add(PetType.builder().id(2L).name("Cat").build());
		mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
	}

	@Test
	void initCreationForm() throws Exception {
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		Mockito.when(petTypeService.findAll()).thenReturn(petTypes);

		mockMvc.perform(get("/owners/1/pets/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"))
				.andExpect(model().attributeExists("pet"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void processCreationForm() throws Exception {
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		Mockito.when(petTypeService.findAll()).thenReturn(petTypes);

		mockMvc.perform(post("/owners/1/pets/new"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/owners/1"));

		Mockito.verify(petService).save(Mockito.any(Pet.class));
	}

	@Test
	void initUpdateForm() throws Exception {
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		Mockito.when(petTypeService.findAll()).thenReturn(petTypes);
		Mockito.when(petService.findById(Mockito.anyLong())).thenReturn(Pet.builder().id(2L).build());

		mockMvc.perform(get("/owners/1/pets/2/edit"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"))
				.andExpect(model().attributeExists("pet"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void processUpdateForm() throws Exception {
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		Mockito.when(petTypeService.findAll()).thenReturn(petTypes);

		mockMvc.perform(post("/owners/1/pets/2/edit"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/owners/1"));

		Mockito.verify(petService).save(Mockito.any(Pet.class));
	}
}