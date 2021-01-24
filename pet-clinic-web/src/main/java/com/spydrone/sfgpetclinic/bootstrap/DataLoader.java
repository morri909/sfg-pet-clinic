package com.spydrone.sfgpetclinic.bootstrap;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.model.Pet;
import com.spydrone.sfgpetclinic.model.PetType;
import com.spydrone.sfgpetclinic.model.Vet;
import com.spydrone.sfgpetclinic.services.OwnerService;
import com.spydrone.sfgpetclinic.services.PetTypeService;
import com.spydrone.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;

	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
	}

	@Override
	public void run(String... args) throws Exception {
		PetType dog = new PetType();
		dog.setName("Dog");
		PetType savedDog = petTypeService.save(dog);

		PetType cat = new PetType();
		cat.setName("Cat");
		PetType savedCat = petTypeService.save(cat);

		System.out.println("Loaded pet types");

		Owner owner1 = new Owner();
		owner1.setFirstName("Aaron");
		owner1.setLastName("Rodgers");
		owner1.setAddress("123 Test Ave");
		owner1.setCity("Minneapolis");
		owner1.setTelephone("1231231234");

		Pet pet1 = new Pet();
		pet1.setPetType(savedDog);
		pet1.setOwner(owner1);
		pet1.setBirthDate(LocalDate.now());
		pet1.setName("Lucky");

		owner1.getPets().add(pet1);
		ownerService.save(owner1);

		Owner owner2 = new Owner();
		owner2.setFirstName("Davante");
		owner2.setLastName("Adams");
		owner2.setAddress("200 Test Street");
		owner2.setCity("Minneapolis");
		owner2.setTelephone("1111111111");

		Pet pet2 = new Pet();
		pet2.setPetType(savedCat);
		pet2.setOwner(owner2);
		pet2.setBirthDate(LocalDate.now());
		pet2.setName("Kuddles");

		owner2.getPets().add(pet2);
		ownerService.save(owner2);

		Owner owner3 = new Owner();
		owner3.setFirstName("Elgton");
		owner3.setLastName("Jenkins");
		owner2.setAddress("300 Test Road");
		owner2.setCity("Minneapolis");
		owner2.setTelephone("2222222222");
		ownerService.save(owner3);

		System.out.println("Loaded owners");

		Vet vet1 = new Vet();
		vet1.setFirstName("Aaron");
		vet1.setLastName("Jones");
		vetService.save(vet1);

		Vet vet2 = new Vet();
		vet2.setFirstName("Robert");
		vet2.setLastName("Tonyan");
		vetService.save(vet2);

		System.out.println("Loaded vets");
	}
}
