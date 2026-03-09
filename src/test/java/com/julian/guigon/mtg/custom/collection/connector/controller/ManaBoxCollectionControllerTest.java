package com.julian.guigon.mtg.custom.collection.connector.controller;

import com.julian.guigon.mtg.custom.collection.connector.manabox.controller.ManaBoxCollectionController;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ImportResult;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCsvImportService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ManaBoxCollectionController.class)
class ManaBoxCollectionControllerTest {

	private static final String COLLECTION_NAME = "Collection Julian";
	private static final String PATH = "src/test/resources/csv/book.csv";
	private static final boolean NO_NEW_COLLECTION = false;

	@Autowired
	private MockMvc sut;

	@MockitoBean
	private ManaBoxCsvImportService manaBoxCsvImportService;

	@Test
	void importCsv() throws Exception {
		// GIVEN
		final ImportResult importResult = Instancio.create(ImportResult.class);
		Mockito.when(manaBoxCsvImportService.importManaBoxCsv(PATH, COLLECTION_NAME, NO_NEW_COLLECTION)).thenReturn(importResult);

		// WHEN
		ResultActions result = sut.perform(MockMvcRequestBuilders.post("/manabox/import")
				.param("collectionName", COLLECTION_NAME)
				.param("path", PATH)
				.param("newCollection", "false")
		);

		// THEN
		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(
						new ObjectMapper().writeValueAsString(importResult)
				));
		Mockito.verify(manaBoxCsvImportService).importManaBoxCsv(PATH, COLLECTION_NAME, NO_NEW_COLLECTION);
	}
}
