package com.julian.guigon.mtg.custom.collection.connector.controller;

import com.julian.guigon.mtg.custom.collection.connector.AbstractPostgresIT;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ImportResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClientException;

@AutoConfigureTestRestTemplate
public class ManaBoxCollectionControllerIT extends AbstractPostgresIT {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_newCollectionNominal_shouldImport9Elements() {
		// GIVEN
		final String collectionName = "Collection Julian";
		final String path = "/Users/julian/Documents/Programmation/ProjetsPerso/mtg-custom-collection-connector/src/test/resources/csv/small_collection.csv";
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
		Assertions.assertThat(response.getBody()).satisfies((importResult) -> {
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
		final String path = "/Users/julian/Documents/Programmation/ProjetsPerso/mtg-custom-collection-connector/src/test/resources/csv/small_collection.csv";
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
		Assertions.assertThat(response.getBody()).satisfies((importResult) -> {
					Assertions.assertThat(importResult.idCollection()).isNotNull();
					Assertions.assertThat(importResult.imported()).isEqualTo(9);
					Assertions.assertThat(importResult.binderNames()).contains("Hors-decks");
				}
		);
	}

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_badStructureInFile_shouldImportNothing() {
		// GIVEN
		final String collectionName = "Collection Julian";
		final String path = "/Users/julian/Documents/Programmation/ProjetsPerso/mtg-custom-collection-connector/src/test/resources/csv/bad_structure.csv";
		// WHEN & THEN
		try {
			ResponseEntity<ImportResult> response =
					restTemplate.postForEntity(
							"/manabox/import?collectionName={c}&path={p}&newCollection={n}",
							null,
							ImportResult.class,
							collectionName,
							path,
							true
					);
			Assertions.fail("Should have thrown RestClientException");
		} catch (RestClientException ignored) {
		}
	}

	@Test
	@Sql(value = "/sql/import_5_manaboxcards_from_2_collections.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_noCollectionAtThisName_shouldImportNothing() {
		// GIVEN
		final String collectionName = "Wrong collection name";
		final String path = "/Users/julian/Documents/Programmation/ProjetsPerso/mtg-custom-collection-connector/src/test/resources/csv/small_collection.csv";
		// WHEN & THEN
		try {
			ResponseEntity<ImportResult> response =
					restTemplate.postForEntity(
							"/manabox/import?collectionName={c}&path={p}&newCollection={n}",
							null,
							ImportResult.class,
							collectionName,
							path,
							false
					);
			Assertions.fail("Should have thrown RestClientException");
		} catch (RestClientException ignored) {
		}
	}

	@Test
	@Sql(value = "/sql/delete_all_collections.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void importCsv_noFile_shouldImportNothing() {
		// GIVEN
		final String collectionName = "Collection TI";
		final String path = "/Users/julian/Documents/Programmation/ProjetsPerso/mtg-custom-collection-connector/src/test/resources/csv/inexistent_collection.csv";
		// WHEN & THEN
		try {
			ResponseEntity<ImportResult> response =
					restTemplate.postForEntity(
							"/manabox/import?collectionName={c}&path={p}&newCollection={n}",
							null,
							ImportResult.class,
							collectionName,
							path,
							true
					);
			Assertions.fail("Should have thrown RestClientException");
		} catch (RestClientException ignored) {
		}
	}
}
