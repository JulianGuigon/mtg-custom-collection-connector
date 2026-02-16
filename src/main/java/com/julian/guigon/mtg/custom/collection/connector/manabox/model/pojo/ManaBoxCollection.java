package com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record ManaBoxCollection(
		UUID id,
		String name,
		ZonedDateTime creationDate,
		List<String> binderNames
) {
}
