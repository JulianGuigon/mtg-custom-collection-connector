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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ManaBoxCardServiceTest {

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
	void findAllManaBoxCards_nominal_returnCards() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.findAllManaBoxCardMb()).thenReturn(List.of(
				Instancio.create(ManaBoxCardMb.class),
				Instancio.create(ManaBoxCardMb.class),
				Instancio.create(ManaBoxCardMb.class)
		));

		// WHEN
		final List<ManaBoxCard> result = sut.findAllManaBoxCards();

		// THEN
		Assertions.assertThat(result)
				.isNotEmpty()
				.hasSize(3);
	}

	@Test
	void findAllManaBoxCards_noData_returnNothing() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.findAllManaBoxCardMb()).thenReturn(List.of());

		// WHEN
		final List<ManaBoxCard> result = sut.findAllManaBoxCards();

		// THEN
		Assertions.assertThat(result).isEmpty();
	}

	@Test
	void insertAllManaBoxCard_nominal_insertAll() {
		// GIVEN
		List<ManaBoxCardMb> manaBoxCardMbs = List.of(
				Instancio.create(ManaBoxCardMb.class),
				Instancio.create(ManaBoxCardMb.class),
				Instancio.create(ManaBoxCardMb.class)
		);

		// WHEN
		final int result = sut.insertAllManaBoxCards(manaBoxCardMbs);

		// THEN
		Mockito.verify(manaBoxCardRepository).insertAllManaBoxCardMb(manaBoxCardMbs);
		Assertions.assertThat(result).isEqualTo(3);
	}

	@Test
	void insertAllManaBoxCard_noData_insertNothing() {
		// GIVEN
		// WHEN
		final int result = sut.insertAllManaBoxCards(List.of());

		// THEN
		Mockito.verifyNoInteractions(manaBoxCardRepository);
		Assertions.assertThat(result).isEqualTo(0);
	}

	@Test
	void deleteAllManaBoxCard_nominal_deleteAll() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.deleteAllManaBoxCardMb()).thenReturn(true);

		// WHEN
		final boolean result = sut.deleteAllManaBoxCards();

		// THEN
		Mockito.verify(manaBoxCardRepository).deleteAllManaBoxCardMb();
		Assertions.assertThat(result).isTrue();
	}

	@Test
	void deleteAllManaBoxCard_noData_deleteNothing() {
		// GIVEN
		Mockito.when(manaBoxCardRepository.deleteAllManaBoxCardMb()).thenReturn(false);

		// WHEN
		final boolean result = sut.deleteAllManaBoxCards();

		// THEN
		Mockito.verify(manaBoxCardRepository).deleteAllManaBoxCardMb();
		Assertions.assertThat(result).isFalse();
	}
}
