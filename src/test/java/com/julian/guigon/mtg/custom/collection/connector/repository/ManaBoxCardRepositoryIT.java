package com.julian.guigon.mtg.custom.collection.connector.repository;

import com.julian.guigon.mtg.custom.collection.connector.AbstractPostgresIT;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCardRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ManaBoxCardRepositoryIT extends AbstractPostgresIT {

	private static final UUID ID_COLLECTION = UUID.fromString("a7397c6a-a29f-4495-898c-028355708f33");

	@Autowired
	private ManaBoxCardRepository sut;

	@Test
	@Sql(value = "/sql/import_5_manaboxcards_from_2_collections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findManaBoxCardMbById_nominal_returnOneResult() {
		// GIVEN
		final String id = "ba017979-a99e-4129-b1a8-fc7643573ae3";

		// WHEN
		final Optional<ManaBoxCardMb> cardsInDb = sut.findManaBoxCardMbById(id);

		// THEN
		Assertions.assertThat(cardsInDb).isPresent();
	}

	@Test
	void findManaBoxCardMbById_noResult_returnNoResult() {
		// GIVEN
		final String id = "ba017979-a99e-4129-b1a8-fc7643573ae3";

		// WHEN
		final Optional<ManaBoxCardMb> cardsInDb = sut.findManaBoxCardMbById(id);

		// THEN
		Assertions.assertThat(cardsInDb).isEmpty();
	}

	@Test
	@Sql(value = "/sql/import_5_manaboxcards_from_2_collections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findAllManaBoxCardMbByIdCollection_nominal_returnResults() {
		// GIVEN
		// WHEN
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMbByIdCollection(ID_COLLECTION);

		// THEN
		Assertions.assertThat(cardsInDb)
				.isNotEmpty()
				.hasSize(3);
	}

	@Test
	void findAllManaBoxCardMbByIdCollection_noResult_returnNoResult() {
		// GIVEN
		// WHEN
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMbByIdCollection(ID_COLLECTION);

		// THEN
		Assertions.assertThat(cardsInDb).isEmpty();
	}

	@Test
	@Sql(value = "/sql/import_1_manaboxcollection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void insertAllManaBoxCardMb_nominal_returnResults() {
		// GIVEN
		final List<ManaBoxCardMb> cards = new ArrayList<>();
		cards.add(Instancio.of(ManaBoxCardMb.class)
				.set(Select.field(ManaBoxCardMb::getIdCollection), ID_COLLECTION)
				.create());
		cards.add(Instancio.of(ManaBoxCardMb.class)
				.set(Select.field(ManaBoxCardMb::getIdCollection), ID_COLLECTION)
				.create());

		// WHEN
		final int cardsInDb = sut.insertAllManaBoxCardMb(cards);

		// THEN
		Assertions.assertThat(cardsInDb).isEqualTo(2);
	}

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void insertAllManaBoxCardMb_noRowsToInsert_returnNoResult() {
		// GIVEN
		final List<ManaBoxCardMb> cards = new ArrayList<>();

		// WHEN
		final int cardsInDb = sut.insertAllManaBoxCardMb(cards);

		// THEN
		Assertions.assertThat(cardsInDb).isZero();
	}

	@Test
	@Sql(value = "/sql/import_5_manaboxcards_from_2_collections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void deleteAllManaBoxCardMbByIdCollection_nominal_returnNoResult() {
		// GIVEN


		// WHEN
		final boolean isDeleted = sut.deleteAllManaBoxCardMbByIdCollection(ID_COLLECTION);
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMbByIdCollection(ID_COLLECTION);

		// THEN
		Assertions.assertThat(isDeleted).isTrue();
		Assertions.assertThat(cardsInDb).isEmpty();
	}

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void deleteAllManaBoxCardMbByIdCollection_noData_returnNoResult() {
		// GIVEN


		// WHEN
		final boolean isDeleted = sut.deleteAllManaBoxCardMbByIdCollection(ID_COLLECTION);
		final List<ManaBoxCardMb> cardsInDb = sut.findAllManaBoxCardMbByIdCollection(ID_COLLECTION);

		// THEN
		Assertions.assertThat(isDeleted).isFalse();
		Assertions.assertThat(cardsInDb).isEmpty();
	}
}
