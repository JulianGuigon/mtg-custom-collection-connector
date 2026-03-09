package com.julian.guigon.mtg.custom.collection.connector.mapper;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapperImpl;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.*;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ManaBoxCardMapperTest {

	@InjectMocks
	private ManaBoxCardMapperImpl sut;

	@Test
	void manaBoxCardFromManaBoxCardMb_nominal_shouldMapWithoutError() {
		// GIVEN
		final ManaBoxCardMb manaBoxCardMb = Instancio.of(ManaBoxCardMb.class)
				.set(Select.field(ManaBoxCardMb::isMisprint), true)
				.set(Select.field(ManaBoxCardMb::isAltered), true)
				.create();

		// WHEN
		final ManaBoxCard result = sut.manaBoxCardFromManaBoxCardMb(manaBoxCardMb);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfies(manaBoxCard -> assertThatAllManaboxCardFieldsAreEqualsToManaBoxCardMb(manaBoxCard, manaBoxCardMb));
	}

	@Test
	void manaBoxCardMbFromManaBoxCard_nominal_shouldMapWithoutError() {
		// GIVEN
		final ManaBoxCard manaBoxCard = Instancio.of(ManaBoxCard.class)
				.set(Select.field(ManaBoxCard::misprint), true)
				.set(Select.field(ManaBoxCard::altered), true)
				.create();

		// WHEN
		final ManaBoxCardMb result = sut.manaBoxCardMbFromManaBoxCard(manaBoxCard);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfies(manaBoxCardMb -> assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(manaBoxCardMb, manaBoxCard));
	}

	@Test
	void manaBoxCardFromManaBoxCardMb_nullValue_shouldReturnNull() {
		// GIVEN
		final ManaBoxCardMb manaBoxCardMb = null;

		// WHEN
		final ManaBoxCard result = sut.manaBoxCardFromManaBoxCardMb(manaBoxCardMb);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}

	@Test
	void manaBoxCardMbFromManaBoxCard_nullValue_shouldReturnNull() {
		// GIVEN
		final ManaBoxCard manaBoxCard = null;

		// WHEN
		final ManaBoxCardMb result = sut.manaBoxCardMbFromManaBoxCard(manaBoxCard);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}

	@Test
	void manaBoxCardsFromManaBoxCardMbs_nominal_shouldMapWithoutError() {
		// GIVEN
		final List<ManaBoxCardMb> manaBoxCardMbs = List.of(
				Instancio.create(ManaBoxCardMb.class),
				Instancio.create(ManaBoxCardMb.class),
				Instancio.create(ManaBoxCardMb.class)
		);

		// WHEN
		final List<ManaBoxCard> result = sut.manaBoxCardsFromManaBoxCardMbs(manaBoxCardMbs);

		// THEN
		Assertions.assertThat(result)
				.isNotEmpty()
				.satisfiesExactly(
						manaBoxCard1 -> assertThatAllManaboxCardFieldsAreEqualsToManaBoxCardMb(manaBoxCard1, manaBoxCardMbs.getFirst()),
						manaBoxCard2 -> assertThatAllManaboxCardFieldsAreEqualsToManaBoxCardMb(manaBoxCard2, manaBoxCardMbs.get(1)),
						manaBoxCard3 -> assertThatAllManaboxCardFieldsAreEqualsToManaBoxCardMb(manaBoxCard3, manaBoxCardMbs.get(2))
				);
	}

	@Test
	void manaBoxCardMbsFromManaBoxCards_nominal_shouldMapWithoutError() {
		// GIVEN
		final List<ManaBoxCard> manaBoxCards = List.of(
				Instancio.create(ManaBoxCard.class),
				Instancio.create(ManaBoxCard.class),
				Instancio.create(ManaBoxCard.class)
		);

		// WHEN
		final List<ManaBoxCardMb> result = sut.manaBoxCardMbsFromManaBoxCards(manaBoxCards);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfiesExactly(
						manaBoxCardMb1 -> assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(manaBoxCardMb1, manaBoxCards.getFirst()),
						manaBoxCardMb2 -> assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(manaBoxCardMb2, manaBoxCards.get(1)),
						manaBoxCardMb3 -> assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(manaBoxCardMb3, manaBoxCards.get(2))
				);
	}

	@Test
	void manaBoxCardsFromManaBoxCardMbs_nullList_shouldReturnNull() {
		// GIVEN
		final List<ManaBoxCardMb> manaBoxCardMbs = null;

		// WHEN
		final List<ManaBoxCard> result = sut.manaBoxCardsFromManaBoxCardMbs(manaBoxCardMbs);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}

	@Test
	void manaBoxCardMbsFromManaBoxCards_nullList_shouldReturnNull() {
		// GIVEN
		final List<ManaBoxCard> manaBoxCards = null;

		// WHEN
		final List<ManaBoxCardMb> result = sut.manaBoxCardMbsFromManaBoxCards(manaBoxCards);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}

	@Test
	void manaBoxCardFromCsvRecord_nominal_shouldMapWithoutError() {
		// GIVEN
		final UUID idCollection = UUID.randomUUID();
		final CsvRecord csvRecord = Instancio.of(CsvRecord.class)
				.set(Select.field(CsvRecord::values), generateCsvRecordValues())
				.create();

		// WHEN
		final ManaBoxCard manaBoxCard = sut.manaBoxCardFromCsvRecord(csvRecord, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCard)
				.isNotNull()
				.satisfies(
						manaBoxCard1 -> {
							assertThatAllManaboxCardFieldsAreEqualsToCsvRecord(manaBoxCard1, idCollection);
						}
				);
	}

	private static @NotNull Map<String, String> generateCsvRecordValues() {
		final Map<String, String> values = new HashMap<>();
		values.put("Binder Name", "Hors-decks");
		values.put("Binder Type", "binder");
		values.put("Name", "Field Research");
		values.put("Set code", "ZNR");
		values.put("Set name", "Zendikar Rising");
		values.put("Collector number", "58");
		values.put("Foil", "normal");
		values.put("Rarity", "common");
		values.put("Quantity", "4");
		values.put("ManaBox ID", "54680");
		values.put("Scryfall ID", "a48c3a2f-678b-4e70-a53f-258457d88d29");
		values.put("Purchase price", "0.07");
		values.put("Misprint", "false");
		values.put("Altered", "false");
		values.put("Condition", "near_mint");
		values.put("Language", "fr");
		values.put("Purchase price currency", "EUR");
		return values;
	}

	@Test
	void manaBoxCardFromCsvRecord_nullIdCollection_shouldReturnNull() {
		// GIVEN
		final UUID idCollection = null;
		final CsvRecord csvRecord = Instancio.create(CsvRecord.class);

		// WHEN
		final ManaBoxCard manaBoxCard = sut.manaBoxCardFromCsvRecord(csvRecord, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCard)
				.isNull();
	}

	@Test
	void manaBoxCardFromCsvRecord_nullCsvRecord_shouldReturnNull() {
		// GIVEN
		final UUID idCollection = UUID.randomUUID();

		// WHEN
		final ManaBoxCard manaBoxCard = sut.manaBoxCardFromCsvRecord(null, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCard)
				.isNull();
	}

	@Test
	void manaBoxCardFromCsvRecord_nullCsvRecordValues_shouldReturnNull() {
		// GIVEN
		final UUID idCollection = UUID.randomUUID();
		final CsvRecord csvRecord = Instancio.of(CsvRecord.class)
				.set(Select.field(CsvRecord::values), null)
				.create();

		// WHEN
		final ManaBoxCard manaBoxCard = sut.manaBoxCardFromCsvRecord(csvRecord, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCard)
				.isNull();
	}

	@Test
	void manaBoxCardFromCsvRecord_wrongValuesAccordingToHeaders_shouldReturnNull() {
		// GIVEN
		final UUID idCollection = UUID.randomUUID();
		final Map<String, String> values = new HashMap<>();
		values.put("Wrong header 1", "Wrong value 1");
		values.put("Wrong header 2", "Wrong value 2");
		values.put("Wrong header 3", "Wrong value 3");
		final CsvRecord csvRecord = Instancio.of(CsvRecord.class)
				.set(Select.field(CsvRecord::values), values)
				.create();

		// WHEN
		final ManaBoxCard manaBoxCard = sut.manaBoxCardFromCsvRecord(csvRecord, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCard)
				.isNull();
	}

	@Test
	void manaBoxCardsFromCsvRecords_nominal_shouldMapWithoutError() {
		// GIVEN
		final UUID idCollection = UUID.randomUUID();
		final List<CsvRecord> csvRecords = List.of(
				Instancio.of(CsvRecord.class)
						.set(Select.field(CsvRecord::values), generateCsvRecordValues())
						.create(),
				Instancio.of(CsvRecord.class)
						.set(Select.field(CsvRecord::values), generateCsvRecordValues())
						.create(),
				Instancio.of(CsvRecord.class)
						.set(Select.field(CsvRecord::values), generateCsvRecordValues())
						.create()
		);

		// WHEN
		final List<ManaBoxCard> result = sut.manaBoxCardsFromCsvRecords(csvRecords, idCollection);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfiesExactly(
						manaBoxCard1 -> assertThatAllManaboxCardFieldsAreEqualsToCsvRecord(manaBoxCard1, idCollection),
						manaBoxCard2 -> assertThatAllManaboxCardFieldsAreEqualsToCsvRecord(manaBoxCard2, idCollection),
						manaBoxCard3 -> assertThatAllManaboxCardFieldsAreEqualsToCsvRecord(manaBoxCard3, idCollection)
				);
	}

	@Test
	void manaBoxCardsFromCsvRecords_nullIdCollection_shouldReturnEmptyList() {
		// GIVEN
		final UUID idCollection = null;
		final List<CsvRecord> csvRecords = List.of(
				Instancio.create(CsvRecord.class),
				Instancio.create(CsvRecord.class),
				Instancio.create(CsvRecord.class)
		);

		// WHEN
		final List<ManaBoxCard> manaBoxCards = sut.manaBoxCardsFromCsvRecords(csvRecords, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCards)
				.isEmpty();
	}

	@Test
	void manaBoxCardsFromCsvRecords_emptyCsvRecords_shouldReturnEmptyList() {
		// GIVEN
		final UUID idCollection = UUID.randomUUID();
		final List<CsvRecord> csvRecords = List.of();

		// WHEN
		final List<ManaBoxCard> manaBoxCards = sut.manaBoxCardsFromCsvRecords(csvRecords, idCollection);

		// THEN
		Assertions.assertThat(manaBoxCards)
				.isEmpty();
	}

	private static void assertThatAllManaboxCardFieldsAreEqualsToManaBoxCardMb(ManaBoxCard manaBoxCard, ManaBoxCardMb manaBoxCardMb) {
		Assertions.assertThat(manaBoxCard.id()).isNotBlank();
		Assertions.assertThat(manaBoxCard.manaboxId()).isEqualTo(manaBoxCardMb.getManaboxId());
		Assertions.assertThat(manaBoxCard.scryfallId()).isEqualTo(manaBoxCardMb.getScryfallId());
		Assertions.assertThat(manaBoxCard.binderName()).isEqualTo(manaBoxCardMb.getBinderName());
		Assertions.assertThat(manaBoxCard.binderType()).isEqualTo(manaBoxCardMb.getBinderType());
		Assertions.assertThat(manaBoxCard.name()).isEqualTo(manaBoxCardMb.getName());
		Assertions.assertThat(manaBoxCard.setCode()).isEqualTo(manaBoxCardMb.getSetCode());
		Assertions.assertThat(manaBoxCard.setName()).isEqualTo(manaBoxCardMb.getSetName());
		Assertions.assertThat(manaBoxCard.collectorNumber()).isEqualTo(manaBoxCardMb.getCollectorNumber());
		Assertions.assertThat(manaBoxCard.foil()).isEqualTo(manaBoxCardMb.getFoil());
		Assertions.assertThat(manaBoxCard.rarity()).isEqualTo(manaBoxCardMb.getRarity());
		Assertions.assertThat(manaBoxCard.quantity()).isEqualTo(manaBoxCardMb.getQuantity());
		Assertions.assertThat(manaBoxCard.purchasePrice()).isEqualTo(manaBoxCardMb.getPurchasePrice());
		Assertions.assertThat(manaBoxCard.misprint()).isEqualTo(manaBoxCardMb.isMisprint());
		Assertions.assertThat(manaBoxCard.altered()).isEqualTo(manaBoxCardMb.isAltered());
		Assertions.assertThat(manaBoxCard.condition()).isEqualTo(manaBoxCardMb.getCondition());
		Assertions.assertThat(manaBoxCard.language()).isEqualTo(manaBoxCardMb.getLanguage());
		Assertions.assertThat(manaBoxCard.purchasePriceCurrency()).isEqualTo(manaBoxCardMb.getPurchasePriceCurrency());
		Assertions.assertThat(manaBoxCard.idCollection()).isEqualTo(manaBoxCardMb.getIdCollection());
	}

	private static void assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(ManaBoxCardMb manaBoxCardMb, ManaBoxCard manaBoxCard) {
		Assertions.assertThat(manaBoxCardMb.getId()).isNotBlank();
		Assertions.assertThat(manaBoxCardMb.getManaboxId()).isEqualTo(manaBoxCard.manaboxId());
		Assertions.assertThat(manaBoxCardMb.getScryfallId()).isEqualTo(manaBoxCard.scryfallId());
		Assertions.assertThat(manaBoxCardMb.getBinderName()).isEqualTo(manaBoxCard.binderName());
		Assertions.assertThat(manaBoxCardMb.getBinderType()).isEqualTo(manaBoxCard.binderType());
		Assertions.assertThat(manaBoxCardMb.getName()).isEqualTo(manaBoxCard.name());
		Assertions.assertThat(manaBoxCardMb.getSetCode()).isEqualTo(manaBoxCard.setCode());
		Assertions.assertThat(manaBoxCardMb.getSetName()).isEqualTo(manaBoxCard.setName());
		Assertions.assertThat(manaBoxCardMb.getCollectorNumber()).isEqualTo(manaBoxCard.collectorNumber());
		Assertions.assertThat(manaBoxCardMb.getFoil()).isEqualTo(manaBoxCard.foil());
		Assertions.assertThat(manaBoxCardMb.getRarity()).isEqualTo(manaBoxCard.rarity());
		Assertions.assertThat(manaBoxCardMb.getQuantity()).isEqualTo(manaBoxCard.quantity());
		Assertions.assertThat(manaBoxCardMb.getPurchasePrice()).isEqualTo(manaBoxCard.purchasePrice());
		Assertions.assertThat(manaBoxCardMb.isMisprint()).isEqualTo(manaBoxCard.misprint());
		Assertions.assertThat(manaBoxCardMb.isAltered()).isEqualTo(manaBoxCard.altered());
		Assertions.assertThat(manaBoxCardMb.getCondition()).isEqualTo(manaBoxCard.condition());
		Assertions.assertThat(manaBoxCardMb.getLanguage()).isEqualTo(manaBoxCard.language());
		Assertions.assertThat(manaBoxCardMb.getPurchasePriceCurrency()).isEqualTo(manaBoxCard.purchasePriceCurrency());
		Assertions.assertThat(manaBoxCardMb.getIdCollection()).isEqualTo(manaBoxCard.idCollection());
	}

	private static void assertThatAllManaboxCardFieldsAreEqualsToCsvRecord(ManaBoxCard manaBoxCard1, UUID idCollection) {
		Assertions.assertThat(manaBoxCard1.id()).isNotBlank();
		Assertions.assertThat(manaBoxCard1.manaboxId()).isEqualTo("54680");
		Assertions.assertThat(manaBoxCard1.binderName()).isEqualTo("Hors-decks");
		Assertions.assertThat(manaBoxCard1.binderType()).isEqualTo("binder");
		Assertions.assertThat(manaBoxCard1.name()).isEqualTo("Field Research");
		Assertions.assertThat(manaBoxCard1.setCode()).isEqualTo("ZNR");
		Assertions.assertThat(manaBoxCard1.setName()).isEqualTo("Zendikar Rising");
		Assertions.assertThat(manaBoxCard1.collectorNumber()).isEqualTo("58");
		Assertions.assertThat(manaBoxCard1.foil()).isEqualTo(FoilEnum.NORMAL);
		Assertions.assertThat(manaBoxCard1.rarity()).isEqualTo(RarityEnum.COMMON);
		Assertions.assertThat(manaBoxCard1.quantity()).isEqualTo(4);
		Assertions.assertThat(manaBoxCard1.scryfallId()).isEqualTo("a48c3a2f-678b-4e70-a53f-258457d88d29");
		Assertions.assertThat(manaBoxCard1.purchasePrice()).isEqualTo(0.07f);
		Assertions.assertThat(manaBoxCard1.misprint()).isFalse();
		Assertions.assertThat(manaBoxCard1.altered()).isFalse();
		Assertions.assertThat(manaBoxCard1.condition()).isEqualTo(ConditionEnum.NEAR_MINT);
		Assertions.assertThat(manaBoxCard1.language()).isEqualTo(LanguageEnum.FRENCH);
		Assertions.assertThat(manaBoxCard1.purchasePriceCurrency()).isEqualTo(CurrencyEnum.EURO);
		Assertions.assertThat(manaBoxCard1.idCollection()).isEqualTo(idCollection);
	}
}
