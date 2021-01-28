package com.spydrone.sfgpetclinic.services.jpa;

import com.spydrone.sfgpetclinic.model.Specialty;
import com.spydrone.sfgpetclinic.repositories.SpecialtyRepository;
import com.spydrone.sfgpetclinic.services.SpecialtyService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SpecialtyJpaService implements SpecialtyService {

	private final SpecialtyRepository specialtyRepository;

	public SpecialtyJpaService(SpecialtyRepository specialtyRepository) {
		this.specialtyRepository = specialtyRepository;
	}

	@Override
	public Set<Specialty> findAll() {
		Set<Specialty> specialties = new HashSet<>();
		specialtyRepository.findAll().forEach(specialties::add);
		return specialties;
	}

	@Override
	public Specialty findById(Long id) {
		return specialtyRepository.findById(id).orElse(null);
	}

	@Override
	public Specialty save(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	@Override
	public void delete(Specialty specialty) {
		specialtyRepository.delete(specialty);
	}

	@Override
	public void deleteById(Long id) {
		specialtyRepository.deleteById(id);
	}
}
