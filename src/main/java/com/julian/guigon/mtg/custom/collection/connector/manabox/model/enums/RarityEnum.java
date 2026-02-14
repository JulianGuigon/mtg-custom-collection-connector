package com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RarityEnum {
	COMMON("common"),
	UNCOMMON("uncommon"),
	RARE("rare"),
	MYTHIC("mythic");

	private final String name;

	RarityEnum(String name) {
		this.name = name;
	}

	public static RarityEnum fromString(String value) {
		return Arrays.stream(values())
				.filter(e -> e.name.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() ->
						new IllegalArgumentException("Unknown RarityEnum value: " + value));
	}
}
