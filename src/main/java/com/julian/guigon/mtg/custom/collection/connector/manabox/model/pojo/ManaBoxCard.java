package com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.*;

public record ManaBoxCard(
		Integer id,
		String scryfallId,
		String binderName,
		String binderType,
		String name,
		String setCode,
		String setName,
		Integer collectorNumber,
		FoilEnum foil,
		RarityEnum rarity,
		Integer quantity,
		Float purchasePrice,
		boolean misprint,
		boolean altered,
		ConditionEnum condition,
		LanguageEnum language,
		CurrencyEnum purchasePriceCurrency
) {
}
