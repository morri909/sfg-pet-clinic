package com.spydrone.sfgpetclinic.services.map;

import com.spydrone.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class OwnerMapServiceTest {
	private static final long ID = 1L;
	private static final String LAST_NAME = "test";
	private OwnerMapService ownerMapService;

	@BeforeEach
	void setUp() {
		ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
		ownerMapService.save(Owner.builder().id(1L).lastName(LAST_NAME).build());
	}

	@Test
	void findAll() {
		Set<Owner> ownerSet = ownerMapService.findAll();

		Assertions.assertEquals(ID, ownerSet.size());
	}

	@Test
	void deleteById() {
		ownerMapService.deleteById(ID);

		Assertions.assertEquals(0, ownerMapService.findAll().size());
	}

	@Test
	void delete() {
		ownerMapService.delete(ownerMapService.findById(ID));

		Assertions.assertEquals(0, ownerMapService.findAll().size());
	}

	@Test
	void saveExistingId() {
		long id = 2L;
		Owner owner2 = Owner.builder().id(id).build();
		Owner savedOwner = ownerMapService.save(owner2);

		Assertions.assertEquals(id, savedOwner.getId());
	}

	@Test
	void saveNoId() {
		Owner savedOwner = ownerMapService.save(Owner.builder().build());

		Assertions.assertNotNull(savedOwner);
		Assertions.assertNotNull(savedOwner.getId());
	}

	@Test
	void findById() {
		Owner owner = ownerMapService.findById(ID);

		Assertions.assertEquals(ID, owner.getId());
	}

	@Test
	void findByLastName() {
		Owner owner = ownerMapService.findByLastName(LAST_NAME);

		Assertions.assertNotNull(owner);
		Assertions.assertEquals(ID, owner.getId());
		Assertions.assertEquals(LAST_NAME, owner.getLastName());
	}

	@Test
	void findByLastNameNotFound() {
		Owner owner = ownerMapService.findByLastName("missing");

		Assertions.assertNull(owner);
	}
}