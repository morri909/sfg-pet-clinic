package com.spydrone.sfgpetclinic.repositories;

import com.spydrone.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
