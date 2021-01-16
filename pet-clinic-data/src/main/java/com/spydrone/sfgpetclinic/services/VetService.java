package com.spydrone.sfgpetclinic.services;

import com.spydrone.sfgpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

	Vet findByID(Long id);
	Vet save(Vet vet);
	Set<Vet> findAll();
}
