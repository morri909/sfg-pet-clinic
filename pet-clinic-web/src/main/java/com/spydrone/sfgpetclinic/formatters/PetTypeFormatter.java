package com.spydrone.sfgpetclinic.formatters;

import com.spydrone.sfgpetclinic.model.PetType;
import com.spydrone.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

	private final PetTypeService petTypeService;

	public PetTypeFormatter(PetTypeService petTypeService) {
		this.petTypeService = petTypeService;
	}

	@Override
	public PetType parse(String name, Locale locale) throws ParseException {
		Set<PetType> petTypeSet = petTypeService.findAll();
		for (PetType petType : petTypeSet) {
			if (petType.getName().equals(name)) {
				return petType;
			}
		}
		return null;
	}

	@Override
	public String print(PetType petType, Locale locale) {
		return petType.getName();
	}
}
