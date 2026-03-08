package com.julian.guigon.mtg.custom.collection.connector.csv.service;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;

import java.util.List;

public interface CsvReaderService {
	List<CsvRecord> readCsvFromPath(String path, List<String> headers);
}
