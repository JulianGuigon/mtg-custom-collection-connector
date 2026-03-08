package com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.*;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.UUID;

@RecordBuilder
public record ManaBoxCard(
		String id,
		String manaboxId,
		String scryfallId,
		String binderName,
		String binderType,
		String name,
		String setCode,
		String setName,
		String collectorNumber,
		FoilEnum foil,
		RarityEnum rarity,
		Integer quantity,
		Float purchasePrice,
		boolean misprint,
		boolean altered,
		ConditionEnum condition,
		LanguageEnum language,
		CurrencyEnum purchasePriceCurrency,
		UUID idCollection
) {
}
