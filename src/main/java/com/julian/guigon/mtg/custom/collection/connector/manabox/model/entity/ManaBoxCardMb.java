package com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ManaBoxCardMb {
	private Integer id;
	private String scryfallId;
	private String binderName;
	private String binderType;
	private String name;
	private String setCode;
	private String setName;
	private Integer collectorNumber;
	private FoilEnum foil;
	private RarityEnum rarity;
	private Integer quantity;
	private Float purchasePrice;
	private boolean misprint;
	private boolean altered;
	private ConditionEnum condition;
	private LanguageEnum language;
	private CurrencyEnum purchasePriceCurrency;
	private UUID idCollection;
}
