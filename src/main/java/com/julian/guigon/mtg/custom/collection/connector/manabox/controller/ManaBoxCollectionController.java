package com.julian.guigon.mtg.custom.collection.connector.manabox.controller;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ImportResult;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCsvImportService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manabox")
public class ManaBoxCollectionController {

	private final ManaBoxCsvImportService manaBoxCsvImportService;

	public ManaBoxCollectionController(ManaBoxCsvImportService manaBoxCsvImportService) {
		this.manaBoxCsvImportService = manaBoxCsvImportService;
	}

	@PostMapping(path = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
	public ImportResult importCsv(@RequestParam String collectionName,
								  @RequestParam String path,
								  @RequestParam boolean newCollection) {
		return manaBoxCsvImportService.importManaBoxCsv(path, collectionName, newCollection);
	}
}
