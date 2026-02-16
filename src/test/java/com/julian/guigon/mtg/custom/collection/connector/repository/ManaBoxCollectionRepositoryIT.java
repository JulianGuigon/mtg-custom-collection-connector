package com.julian.guigon.mtg.custom.collection.connector.repository;

import com.julian.guigon.mtg.custom.collection.connector.AbstractPostgresIT;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCollectionMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCollectionRepository;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManaBoxCollectionRepositoryIT extends AbstractPostgresIT {
	private static final UUID ID_COLLECTION = UUID.fromString("a7397c6a-a29f-4495-898c-028355708f33");

	@Autowired
	private ManaBoxCollectionRepository sut;

	@Test
	@Sql(value = "/sql/import_1_manaboxcollection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findManaBoxCollectionById_nominal_returnOneResult() {
		// GIVEN
		// WHEN
		final Optional<ManaBoxCollectionMb> collectionInDb = sut.findManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(collectionInDb).isPresent();
	}


	@Test
	@Sql(value = "/sql/import_1_manaboxcollection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findManaBoxCollectionById_noData_returnNoResult() {
		// GIVEN
		// WHEN
		final Optional<ManaBoxCollectionMb> collectionInDb = sut.findManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(collectionInDb).isPresent();
	}

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void insertOneManaBoxCollection_nominal_oneInserted() {
		// GIVEN
		final ManaBoxCollectionMb manaBoxCollectionMb = Instancio.create(ManaBoxCollectionMb.class);

		// WHEN
		final int result = sut.insertOneManaBoxCollection(manaBoxCollectionMb);

		// THEN
		Assertions.assertThat(result).isEqualTo(1);
	}

	@Test
	@Sql(value = "/sql/import_1_manaboxcollection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void deleteManaBoxCollectionById_nominal_oneDeleted() {
		// GIVEN
		// WHEN
		final boolean isDeleted = sut.deleteManaBoxCollectionById(ID_COLLECTION);
		final Optional<ManaBoxCollectionMb> collectionInDb = sut.findManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(isDeleted).isTrue();
		Assertions.assertThat(collectionInDb).isEmpty();
	}

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void deleteManaBoxCollectionById_noData_nothingDeleted() {
		// GIVEN
		// WHEN
		final boolean isDeleted = sut.deleteManaBoxCollectionById(ID_COLLECTION);
		final Optional<ManaBoxCollectionMb> collectionInDb = sut.findManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(isDeleted).isFalse();
		Assertions.assertThat(collectionInDb).isEmpty();
	}
}
