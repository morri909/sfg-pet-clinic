package com.spydrone.sfgpetclinic.services.map;

import com.spydrone.sfgpetclinic.model.Pet;
import com.spydrone.sfgpetclinic.model.Specialty;
import com.spydrone.sfgpetclinic.model.Vet;
import com.spydrone.sfgpetclinic.services.SpecialtyService;
import com.spydrone.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
	private final SpecialtyService specialtyService;

	public VetMapService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	@Override
	public Set<Vet> findAll() {
		return super.findAll();
	}

	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}

	@Override
	public void delete(Vet vet) {
		super.delete(vet);
	}

	@Override
	public Vet save(Vet vet) {
		if (vet != null) {
			if (vet.getSpecialties() != null) {
				vet.getSpecialties().forEach(specialty -> {
					if (specialty.getId() == null) {
						Specialty savedSpecialty = specialtyService.save(specialty);
						specialty.setId(savedSpecialty.getId());
					}
				});
			}
			return super.save(vet);
		} else {
			return null;
		}
	}

	@Override
	public Vet findById(Long id) {
		return super.findById(id);
	}
}
