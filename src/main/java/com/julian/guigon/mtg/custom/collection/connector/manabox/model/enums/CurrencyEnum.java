package com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CurrencyEnum {
	EURO("EUR");

	private final String name;

	CurrencyEnum(String name) {
		this.name = name;
	}

	public static CurrencyEnum fromString(String value) {
		return Arrays.stream(values())
				.filter(e -> e.name.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() ->
						new IllegalArgumentException("Unknown CurrencyEnum value: " + value));
	}
}
