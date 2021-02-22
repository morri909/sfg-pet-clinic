package com.spydrone.sfgpetclinic.controllers;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

	@Mock
	OwnerService ownerService;

	@InjectMocks
	OwnerController sut;

	Set<Owner> owners;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		owners = new HashSet<>();
		owners.add(Owner.builder().id(1L).build());
		owners.add(Owner.builder().id(2L).build());

		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
	}

	@Test
	public void list() throws Exception {
		Mockito.when(ownerService.findAllByLastNameLike(Mockito.anyString())).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"));
	}

	@Test
	public void find() throws Exception {
		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"))
				.andExpect(model().attribute("owner", Matchers.any(Owner.class)));

		Mockito.verifyNoInteractions(ownerService);
	}

	@Test
	public void processFindFormReturnMany() throws Exception {
		Mockito.when(ownerService.findAllByLastNameLike(Mockito.anyString())).thenReturn(new ArrayList<>(owners));

		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"))
				.andExpect(model().attribute("selections", Matchers.hasSize(2)));
	}

	@Test
	public void processFindFormReturnOne() throws Exception {
		List<Owner> ownerList = Arrays.asList(Owner.builder().id(1L).build());
		Mockito.when(ownerService.findAllByLastNameLike(Mockito.anyString())).thenReturn(ownerList);

		mockMvc.perform(get("/owners"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attribute("owner", Matchers.any(Owner.class)));
	}

	@Test
	void processFindFormEmptyReturnMany() throws Exception {
		Mockito.when(ownerService.findAllByLastNameLike(ArgumentMatchers.anyString()))
				.thenReturn(Arrays.asList(Owner.builder().id(1l).build(),
						Owner.builder().id(2l).build()));

		mockMvc.perform(get("/owners")
				.param("lastName",""))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"))
				.andExpect(model().attribute("selections", Matchers.hasSize(2)));;
	}

	@Test
	public void showOwner() throws Exception {
		Owner owner = Owner.builder().id(1L).build();
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);

		mockMvc.perform(get("/owners/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownerDetails"))
				.andExpect(model().attribute("owner", Matchers.any(Owner.class)));
	}

	@Test
	public void creationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"))
				.andExpect(model().attributeExists("owner"));
	}

	@Test
	public void processCreationForm() throws Exception {
		Mockito.when(ownerService.save(Mockito.any(Owner.class)))
				.thenReturn(Owner.builder().id(1L).build());

		mockMvc.perform(post("/owners/new"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attributeExists("owner"));

		Mockito.verify(ownerService).save(Mockito.any(Owner.class));
	}

	@Test
	public void updateForm() throws Exception {
		Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(Owner.builder().id(1L).build());

		mockMvc.perform(get("/owners/1/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"))
				.andExpect(model().attributeExists("owner"));
	}

	@Test
	public void processUpdateForm() throws Exception {
		Mockito.when(ownerService.save(Mockito.any(Owner.class))).thenReturn(Owner.builder().id(1L).build());

		mockMvc.perform(post("/owners/1/edit"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attributeExists("owner"));

		Mockito.verify(ownerService).save(Mockito.any(Owner.class));
	}
}