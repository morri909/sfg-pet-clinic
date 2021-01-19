package com.spydrone.sfgpetclinic.services;

import com.spydrone.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
	Owner findByLastName(String lastName);
}
