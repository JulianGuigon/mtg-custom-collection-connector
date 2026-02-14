package com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FoilEnum {
	NORMAL("normal"),
	FOIL("foil");

	private final String name;

	FoilEnum(String name) {
		this.name = name;
	}

	public static FoilEnum fromString(String value) {
		return Arrays.stream(values())
				.filter(e -> e.name.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() ->
						new IllegalArgumentException("Unknown FoilEnum value: " + value));
	}
}
