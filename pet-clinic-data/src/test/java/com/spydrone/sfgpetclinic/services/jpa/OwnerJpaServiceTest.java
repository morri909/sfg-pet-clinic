package com.spydrone.sfgpetclinic.services.jpa;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.repositories.OwnerRepository;
import com.spydrone.sfgpetclinic.repositories.PetRepository;
import com.spydrone.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {
	public static final String LAST_NAME = "Test";

	@Mock
	OwnerRepository ownerRepository;
	@Mock
	PetRepository petRepository;
	@Mock
	PetTypeRepository petTypeRepository;

	@InjectMocks
	OwnerJpaService sut;

	@Test
	void findAll() {
		Set<Owner> ownerSet = new HashSet<>();
		Owner owner = Owner.builder().id(1L).lastName(LAST_NAME).build();
		ownerSet.add(owner);
		Mockito.when(ownerRepository.findAll()).thenReturn(ownerSet);

		Set<Owner> resultSet = sut.findAll();

		Assertions.assertNotNull(resultSet);
		Assertions.assertEquals(1, resultSet.size());
		Assertions.assertTrue(resultSet.contains(owner));
		Mockito.verify(ownerRepository).findAll();
	}

	@Test
	void findById() {
		Long id = 1L;
		Owner owner = Owner.builder().id(id).lastName(LAST_NAME).build();
		Mockito.when(ownerRepository.findById(Mockito.any(Long.class)))
				.thenReturn(Optional.of(owner));

		Owner result = sut.findById(id);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(id, result.getId());
		Mockito.verify(ownerRepository).findById(Mockito.any(Long.class));
	}

	@Test
	void findByIdNotFound() {
		Long id = 1L;
		Mockito.when(ownerRepository.findById(Mockito.any(Long.class)))
				.thenReturn(Optional.empty());

		Owner result = sut.findById(id);

		Assertions.assertNull(result);
		Mockito.verify(ownerRepository).findById(Mockito.any(Long.class));
	}

	@Test
	void save() {
		Owner owner = Owner.builder().id(1L).lastName(LAST_NAME).build();
		Mockito.when(ownerRepository.save(Mockito.any(Owner.class))).thenReturn(owner);

		Owner result = sut.save(owner);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(owner, result);
		Mockito.verify(ownerRepository).save(Mockito.any(Owner.class));
	}

	@Test
	void delete() {
		sut.delete(Owner.builder().build());

		Mockito.verify(ownerRepository).delete(Mockito.any(Owner.class));
	}

	@Test
	void deleteById() {
		sut.deleteById(1L);

		Mockito.verify(ownerRepository).deleteById(Mockito.any(Long.class));
	}

	@Test
	void findByLastName() {
		Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
		Mockito.when(ownerRepository.findByLastName(Mockito.anyString()))
				.thenReturn(returnOwner);

		Owner owner = sut.findByLastName(LAST_NAME);

		Assertions.assertEquals(LAST_NAME, owner.getLastName());
		Mockito.verify(ownerRepository).findByLastName(Mockito.anyString());
	}
}