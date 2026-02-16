package com.julian.guigon.mtg.custom.collection.connector.mapper;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapperImpl;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ManaBoxCardMapperTest {

	@InjectMocks
	private ManaBoxCardMapperImpl sut;

	@Test
	void manaBoxCardFromManaBoxCardMb_nominal_shouldMapWithoutError() {
		// GIVEN
		final ManaBoxCardMb manaBoxCardMb = Instancio.create(ManaBoxCardMb.class);

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
		final ManaBoxCard manaBoxCard = Instancio.create(ManaBoxCard.class);

		// WHEN
		final ManaBoxCardMb result = sut.manaBoxCardMbFromManaBoxCard(manaBoxCard);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfies(manaBoxCardMb -> assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(manaBoxCardMb, manaBoxCard));
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

	private static void assertThatAllManaboxCardFieldsAreEqualsToManaBoxCardMb(ManaBoxCard manaBoxCard, ManaBoxCardMb manaBoxCardMb) {
		Assertions.assertThat(manaBoxCard.id()).isEqualTo(manaBoxCardMb.getId());
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
	}

	private static void assertThatAllManaboxCardMbFieldsAreEqualsToManaBoxCard(ManaBoxCardMb manaBoxCardMb, ManaBoxCard manaBoxCard) {
		Assertions.assertThat(manaBoxCardMb.getId()).isEqualTo(manaBoxCard.id());
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
	}
}
