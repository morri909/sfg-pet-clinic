package com.spydrone.sfgpetclinic.services.jpa;

import com.spydrone.sfgpetclinic.model.Vet;
import com.spydrone.sfgpetclinic.repositories.VetRepository;
import com.spydrone.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class VetJpaService implements VetService {

	private final VetRepository vetRepository;

	public VetJpaService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Override
	public Set<Vet> findAll() {
		Set<Vet> vets =  new HashSet<>();
		vetRepository.findAll().forEach(vets::add);
		return vets;
	}

	@Override
	public Vet findById(Long id) {
		return vetRepository.findById(id).orElse(null);
	}

	@Override
	public Vet save(Vet vet) {
		return vetRepository.save(vet);
	}

	@Override
	public void delete(Vet vet) {
		vetRepository.delete(vet);
	}

	@Override
	public void deleteById(Long id) {
		vetRepository.deleteById(id);
	}
}