package com.spydrone.sfgpetclinic.repositories;

import com.spydrone.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
