package com.julian.guigon.mtg.custom.collection.connector.controller;

import com.julian.guigon.mtg.custom.collection.connector.AbstractPostgresIT;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ImportResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@AutoConfigureTestRestTemplate
class ManaBoxCollectionControllerIT extends AbstractPostgresIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_newCollectionNominal_shouldImport9Elements() {
		// GIVEN
		final String collectionName = "Collection Julian";
		final String path = "src/test/resources/csv/small_collection.csv";
		// WHEN & THEN
		ResponseEntity<ImportResult> response =
				restTemplate.postForEntity(
						"/manabox/import?collectionName={c}&path={p}&newCollection={n}",
						null,
						ImportResult.class,
						collectionName,
						path,
						true
				);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).satisfies(importResult -> {
					Assertions.assertThat(importResult.idCollection()).isNotNull();
					Assertions.assertThat(importResult.imported()).isEqualTo(9);
					Assertions.assertThat(importResult.binderNames()).contains("Hors-decks");
				}
		);
	}

	@Test
	@Sql(value = "/sql/import_5_manaboxcards_from_2_collections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_updateCollectionNominal_shouldImport9Elements() {
		// GIVEN
		final String collectionName = "Collection Julian";
		final String path = "src/test/resources/csv/small_collection.csv";
		// WHEN & THEN
		ResponseEntity<ImportResult> response =
				restTemplate.postForEntity(
						"/manabox/import?collectionName={c}&path={p}&newCollection={n}",
						null,
						ImportResult.class,
						collectionName,
						path,
						false
				);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).satisfies(importResult -> {
					Assertions.assertThat(importResult.idCollection()).isNotNull();
					Assertions.assertThat(importResult.imported()).isEqualTo(9);
					Assertions.assertThat(importResult.binderNames()).contains("Hors-decks");
				}
		);
	}

	@ParameterizedTest
	@CsvSource({
			"Collection Julian,src/test/resources/csv/bad_structure.csv,true",
			"Wrong collection name,src/test/resources/csv/small_collection.csv,false",
			"Collection TI,src/test/resources/csv/inexistent_collection.csv,true"
	})
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_invalidCases_shouldImportNothing(String collectionName, String path, boolean newCollection) {
		// GIVEN
		// WHEN
		final ImportResult response = restTemplate.postForEntity(
				"/manabox/import?collectionName={c}&path={p}&newCollection={n}",
				null,
				ImportResult.class,
				collectionName,
				path,
				newCollection
		).getBody();

		// THEN
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.idCollection()).isNull();
		Assertions.assertThat(response.imported()).isNull();
		Assertions.assertThat(response.binderNames()).isNull();
	}
}
