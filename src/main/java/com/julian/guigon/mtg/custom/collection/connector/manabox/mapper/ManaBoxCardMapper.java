package com.julian.guigon.mtg.custom.collection.connector.manabox.mapper;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import com.julian.guigon.mtg.custom.collection.connector.manabox.constant.ManaBoxConstants;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.*;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCardBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ManaBoxCardMapper {

	public abstract ManaBoxCard manaBoxCardFromManaBoxCardMb(ManaBoxCardMb manaBoxCardMb);

	public abstract ManaBoxCardMb manaBoxCardMbFromManaBoxCard(ManaBoxCard manaBoxCard);

	public abstract List<ManaBoxCard> manaBoxCardsFromManaBoxCardMbs(List<ManaBoxCardMb> manaBoxCardMbs);

	public abstract List<ManaBoxCardMb> manaBoxCardMbsFromManaBoxCards(List<ManaBoxCard> manaBoxCards);

	public List<ManaBoxCard> manaBoxCardsFromCsvRecords(List<CsvRecord> csvRecords, UUID idCollection) {
		if (idCollection == null || CollectionUtils.isEmpty(csvRecords)) {
			return List.of();
		}
		return csvRecords.stream()
				.map(csvRecord -> manaBoxCardFromCsvRecord(csvRecord, idCollection))
				.toList();
	}

	public ManaBoxCard manaBoxCardFromCsvRecord(CsvRecord csvRecord, UUID idCollection) {
		if (csvRecord == null || idCollection == null || MapUtils.isEmpty(csvRecord.values()) ||
			!csvRecord.values().keySet().containsAll(ManaBoxConstants.HEADERS)) {
			return null;
		}

		return ManaBoxCardBuilder.builder()
				.id(UUID.randomUUID().toString())
				.binderName(csvRecord.values().get(ManaBoxConstants.HEADERS.getFirst()))
				.binderType(csvRecord.values().get(ManaBoxConstants.HEADERS.get(1)))
				.name(csvRecord.values().get(ManaBoxConstants.HEADERS.get(2)))
				.setCode(csvRecord.values().get(ManaBoxConstants.HEADERS.get(3)))
				.setName(csvRecord.values().get(ManaBoxConstants.HEADERS.get(4)))
				.collectorNumber(csvRecord.values().get(ManaBoxConstants.HEADERS.get(5)))
				.foil(FoilEnum.fromString(csvRecord.values().get(ManaBoxConstants.HEADERS.get(6))))
				.rarity(RarityEnum.fromString(csvRecord.values().get(ManaBoxConstants.HEADERS.get(7))))
				.quantity(Integer.valueOf(csvRecord.values().get(ManaBoxConstants.HEADERS.get(8))))
				.manaboxId(csvRecord.values().get(ManaBoxConstants.HEADERS.get(9)))
				.scryfallId(csvRecord.values().get(ManaBoxConstants.HEADERS.get(10)))
				.purchasePrice(Float.valueOf(csvRecord.values().get(ManaBoxConstants.HEADERS.get(11))))
				.misprint(Boolean.parseBoolean(csvRecord.values().get(ManaBoxConstants.HEADERS.get(12))))
				.altered(Boolean.parseBoolean(csvRecord.values().get(ManaBoxConstants.HEADERS.get(13))))
				.condition(ConditionEnum.fromString(csvRecord.values().get(ManaBoxConstants.HEADERS.get(14))))
				.language(LanguageEnum.fromString(csvRecord.values().get(ManaBoxConstants.HEADERS.get(15))))
				.purchasePriceCurrency(CurrencyEnum.fromString(csvRecord.values().get(ManaBoxConstants.HEADERS.get(16))))
				.idCollection(idCollection)
				.build();
	}
}
