package com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ConditionEnum {
	NEAR_MINT("near_mint"),
	MINT("mint");

	private final String name;

	ConditionEnum(String name) {
		this.name = name;
	}

	public static ConditionEnum fromString(String value) {
		return Arrays.stream(values())
				.filter(e -> e.name.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() ->
						new IllegalArgumentException("Unknown ConditionEnum value: " + value));
	}
}
