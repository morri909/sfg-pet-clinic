package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	@Mock
	OwnerService ownerService;

	@InjectMocks
	OwnerController sut;

	Set<Owner> owners;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		owners = new HashSet<>();
		owners.add(Owner.builder().id(1L).build());
		owners.add(Owner.builder().id(2L).build());

		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
	}

	@Test
	void list() throws Exception {
		Mockito.when(ownerService.findAll()).thenReturn(owners);

		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", Matchers.hasSize(2)));
	}

	@Test
	void listByIndex() throws Exception {
		Mockito.when(ownerService.findAll()).thenReturn(owners);

		mockMvc.perform(get("/owners/index"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", Matchers.hasSize(2)));
	}

	@Test
	void find() throws Exception {
		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("notimplemented"));

		Mockito.verifyNoInteractions(ownerService);
	}

	@Test
	void showOwner() throws Exception {
		Owner owner = Owner.builder().id(1L).build();
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);

		mockMvc.perform(get("/owners/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownerDetails"))
				.andExpect(model().attribute("owner", Matchers.any(Owner.class)));
	}
}