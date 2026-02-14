package com.julian.guigon.mtg.custom.collection.connector.repository;

import com.julian.guigon.mtg.custom.collection.connector.AbstractPostgresIT;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCardRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManaBoxCardRepositoryIT extends AbstractPostgresIT {

	@Autowired
	private ManaBoxCardRepository sut;

	@Test
	@Sql(value = "/sql/import_5_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findManaBoxCardMbById_nominal_returnOneResult() {
		// Given
		final Integer id = 54680;

		// When
		final Optional<ManaBoxCardMb> cardsInDb = sut.findManaBoxCardMbById(id);

		// Then
		assertTrue(cardsInDb.isPresent());
	}

	@Test
	void findManaBoxCardMbById_noResult_returnNoResult() {
		// Given
		final Integer id = 54680;

		// When
		final Optional<ManaBoxCardMb> cardsInDb = sut.findManaBoxCardMbById(id);

		// Then
		assertFalse(cardsInDb.isPresent());
	}

	@Test
	@Sql(value = "/sql/import_5_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findAllManaBoxCardMb_nominal_returnResults() {
		// Given
		// When
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMb();

		// Then
		assertEquals(cardsInDb.size(), 5);
	}

	@Test
	void findAllManaBoxCardMb_noResult_returnNoResult() {
		// Given
		// When
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMb();

		// Then
		assertEquals(cardsInDb.size(), 0);
	}

	@Test
	@Sql(value = "/sql/delete_all_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void insertAllManaBoxCardMb_nominal_returnResults() {
		// Given
		final List<ManaBoxCardMb> cards = new ArrayList<>();
		cards.add(Instancio.create(ManaBoxCardMb.class));
		cards.add(Instancio.create(ManaBoxCardMb.class));

		// When
		final int cardsInDb = sut.insertAllManaBoxCardMb(cards);

		// Then
		assertEquals(cardsInDb, 2);
	}

	@Test
	void insertAllManaBoxCardMb_noRowsToInsert_returnNoResult() {
		// Given
		final List<ManaBoxCardMb> cards = new ArrayList<>();

		// When
		final int cardsInDb = sut.insertAllManaBoxCardMb(cards);

		// Then
		assertEquals(cardsInDb, 0);
	}

	@Test
	@Sql(value = "/sql/import_5_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_manaboxcards.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void deleteAllManaBoxCardMb_nominal_returnNoResult() {
		// Given
		// When
		final boolean isDeleted = sut.deleteAllManaBoxCardMb();
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMb();

		// Then
		assertTrue(isDeleted);
		assertEquals(cardsInDb.size(), 0);
	}
}
