package com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RecordBuilder
public record ParsedCards (
		List<ManaBoxCard> cards
) {
	public List<String> getCardsBinderNames() {
		return this.cards.stream()
				.map(ManaBoxCard::binderName)
				.collect(Collectors.toSet())
				.stream()
				.toList();
	}
}
