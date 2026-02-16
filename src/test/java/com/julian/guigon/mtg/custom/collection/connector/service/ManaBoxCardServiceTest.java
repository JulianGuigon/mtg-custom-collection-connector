package com.julian.guigon.mtg.custom.collection.connector.service;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCardRepository;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCardService;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.PSQLException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ManaBoxCardServiceTest {

	private static final UUID ID_COLLECTION = UUID.fromString("a7397c6a-a29f-4495-898c-028355708f33");
	private static final List<ManaBoxCardMb> THREE_MANA_BOX_CARD_MBS = List.of(
			Instancio.create(ManaBoxCardMb.class),
			Instancio.create(ManaBoxCardMb.class),
			Instancio.create(ManaBoxCardMb.class)
	);
	private static final List<ManaBoxCard> THREE_MANA_BOX_CARDS = List.of(
			Instancio.create(ManaBoxCard.class),
			Instancio.create(ManaBoxCard.class),
			Instancio.create(ManaBoxCard.class)
	);

	@InjectMocks
	private ManaBoxCardService sut;

	@Mock
	private ManaBoxCardRepository manaBoxCardRepository;

	@Mock
	private ManaBoxCardMapper manaBoxCardMapper;

	@BeforeEach
	void buildUp() {
		Mockito.lenient().when(manaBoxCardRepository.insertAllManaBoxCardMb(ArgumentMatchers.anyList()))
				.thenAnswer(invocation ->
						invocation.getArgument(0, List.class).size()
				);
	}

	@Test
	void findManaBoxCardById_nominal_returnManaBoxCard() {
		// GIVEN
		final Integer id = 1;
		final ManaBoxCardMb manaBoxCardMb = Instancio.create(ManaBoxCardMb.class);
		final ManaBoxCard manaBoxCard = Instancio.create(ManaBoxCard.class);
		Mockito.when(manaBoxCardRepository.findManaBoxCardMbById(id)).thenReturn(Optional.of(manaBoxCardMb));
		Mockito.when(manaBoxCardMapper.manaBoxCardFromManaBoxCardMb(manaBoxCardMb)).thenReturn(manaBoxCard);

		// WHEN
		final Optional<ManaBoxCard> result = sut.findManaBoxCardById(id);

		// THEN
		Assertions.assertThat(result).isPresent();
	}

	@Test
	void findManaBoxCardById_noData_returnNothing() {
		// GIVEN
		final Integer id = 1;
		Mockito.when(manaBoxCardRepository.findManaBoxCardMbById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

		// WHEN
		final Optional<ManaBoxCard> result = sut.findManaBoxCardById(id);

		// THEN
		Assertions.assertThat(result).isEmpty();
	}

	@Test
	void findManaBoxCardById_nullId_returnNothing() {
		// GIVEN
		// WHEN
		final Optional<ManaBoxCard> result = sut.findManaBoxCardById(null);

		// THEN
		Assertions.assertThat(result).isEmpty();
		Mockito.verifyNoInteractions(manaBoxCardRepository);
	}

	@Test
	void findAllManaBoxCardsByIdCollection_nominal_returnCards() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.findAllManaBoxCardMbByIdCollection(ID_COLLECTION)).thenReturn(THREE_MANA_BOX_CARD_MBS);
		Mockito.when(manaBoxCardMapper.manaBoxCardsFromManaBoxCardMbs(THREE_MANA_BOX_CARD_MBS)).thenReturn(THREE_MANA_BOX_CARDS);

		// WHEN
		final List<ManaBoxCard> result = sut.findAllManaBoxCardsByIdCollection(ID_COLLECTION);

		// THEN
		Assertions.assertThat(result)
				.isNotEmpty()
				.hasSize(3);
	}

	@Test
	void findAllManaBoxCardsByIdCollection_noData_returnNothing() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.findAllManaBoxCardMbByIdCollection(ID_COLLECTION)).thenReturn(List.of());

		// WHEN
		final List<ManaBoxCard> result = sut.findAllManaBoxCardsByIdCollection(ID_COLLECTION);

		// THEN
		Assertions.assertThat(result).isEmpty();
	}

	@Test
	void findAllManaBoxCardsByIdCollection_nullIdCollection_returnNothing() {
		// GIVEN
		// WHEN
		final List<ManaBoxCard> result = sut.findAllManaBoxCardsByIdCollection(null);

		// THEN
		Assertions.assertThat(result).isEmpty();
		Mockito.verifyNoInteractions(manaBoxCardRepository);
	}

	@Test
	void insertAllManaBoxCard_nominal_insertAll() {
		// GIVEN
		Mockito.when(manaBoxCardMapper.manaBoxCardMbsFromManaBoxCards(THREE_MANA_BOX_CARDS)).thenReturn(THREE_MANA_BOX_CARD_MBS);
		Mockito.when(manaBoxCardRepository.insertAllManaBoxCardMb(THREE_MANA_BOX_CARD_MBS)).thenReturn(3);

		// WHEN
		final int result = sut.insertAllManaBoxCards(THREE_MANA_BOX_CARDS);

		// THEN
		Mockito.verify(manaBoxCardRepository).insertAllManaBoxCardMb(THREE_MANA_BOX_CARD_MBS);
		Assertions.assertThat(result).isEqualTo(3);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void insertAllManaBoxCard_wrongData_insertNothing(List<ManaBoxCard> cards) {
		// GIVEN
		// WHEN
		final int result = sut.insertAllManaBoxCards(cards);

		// THEN
		Mockito.verifyNoInteractions(manaBoxCardRepository);
		Assertions.assertThat(result).isEqualTo(0);
	}

	@Test
	void insertAllManaBoxCard_onException_insertNothing() {
		// GIVEN
		Mockito.when(manaBoxCardMapper.manaBoxCardMbsFromManaBoxCards(THREE_MANA_BOX_CARDS)).thenReturn(THREE_MANA_BOX_CARD_MBS);
		Mockito.when(manaBoxCardRepository.insertAllManaBoxCardMb(THREE_MANA_BOX_CARD_MBS)).thenThrow(Instancio.create(RuntimeException.class));

		// WHEN
		final int result = sut.insertAllManaBoxCards(THREE_MANA_BOX_CARDS);

		// THEN
		Assertions.assertThat(result).isEqualTo(0);
	}

	@Test
	void deleteAllManaBoxCardByIdCollection_nominal_deleteAll() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.deleteAllManaBoxCardMbByIdCollection(ID_COLLECTION)).thenReturn(true);

		// WHEN
		final boolean result = sut.deleteAllManaBoxCardsByIdCollection(ID_COLLECTION);

		// THEN
		Mockito.verify(manaBoxCardRepository).deleteAllManaBoxCardMbByIdCollection(ID_COLLECTION);
		Assertions.assertThat(result).isTrue();
	}

	@Test
	void deleteAllManaBoxCard_noData_deleteNothing() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.deleteAllManaBoxCardMbByIdCollection(ID_COLLECTION)).thenReturn(false);

		// WHEN
		final boolean result = sut.deleteAllManaBoxCardsByIdCollection(ID_COLLECTION);

		// THEN
		Mockito.verify(manaBoxCardRepository).deleteAllManaBoxCardMbByIdCollection(ID_COLLECTION);
		Assertions.assertThat(result).isFalse();
	}

	@Test
	void deleteAllManaBoxCard_nullIdCollection_deleteNothing() {
		// GIVEN
		// WHEN
		final boolean result = sut.deleteAllManaBoxCardsByIdCollection(null);

		// THEN
		Assertions.assertThat(result).isFalse();
		Mockito.verifyNoInteractions(manaBoxCardRepository);
	}
}
