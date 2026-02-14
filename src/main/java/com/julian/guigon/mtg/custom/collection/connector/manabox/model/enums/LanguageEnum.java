package com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LanguageEnum {
	ENGLISH("en"),
	FRENCH("fr"),
	JAPANESE("jp");

	private final String name;

	LanguageEnum(String name) {
		this.name = name;
	}

	public static LanguageEnum fromString(String value) {
		return Arrays.stream(values())
				.filter(e -> e.name.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() ->
						new IllegalArgumentException("Unknown LanguageEnum value: " + value));
	}
}
