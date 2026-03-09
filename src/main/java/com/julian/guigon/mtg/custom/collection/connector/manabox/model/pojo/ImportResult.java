package com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo;

import java.util.List;
import java.util.UUID;

public record ImportResult (
		UUID idCollection,
		Integer imported,
		List<String> binderNames
) {
}
