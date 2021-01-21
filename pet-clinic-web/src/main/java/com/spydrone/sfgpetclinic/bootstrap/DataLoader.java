package com.spydrone.sfgpetclinic.bootstrap;

import com.spydrone.sfgpetclinic.model.Owner;
import com.spydrone.sfgpetclinic.model.Vet;
import com.spydrone.sfgpetclinic.services.OwnerService;
import com.spydrone.sfgpetclinic.services.VetService;
import com.spydrone.sfgpetclinic.services.map.OwnerMapService;
import com.spydrone.sfgpetclinic.services.map.VetMapService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;

	public DataLoader(OwnerService ownerService, VetService vetService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
	}

	@Override
	public void run(String... args) throws Exception {
		Owner owner1 = new Owner();
		owner1.setId(1L);
		owner1.setFirstName("Aaron");
		owner1.setLastName("Rodgers");
		ownerService.save(owner1);

		Owner owner2 = new Owner();
		owner2.setId(2L);
		owner2.setFirstName("Davante");
		owner2.setLastName("Adams");
		ownerService.save(owner2);

		System.out.println("Loaded owners");

		Vet vet1 = new Vet();
		vet1.setId(1L);
		vet1.setFirstName("Aaron");
		vet1.setLastName("Jones");
		vetService.save(vet1);

		Vet vet2 = new Vet();
		vet2.setId(2L);
		vet2.setFirstName("Robert");
		vet2.setLastName("Tonyan");
		vetService.save(vet2);

		System.out.println("Loaded vets");
	}
}
