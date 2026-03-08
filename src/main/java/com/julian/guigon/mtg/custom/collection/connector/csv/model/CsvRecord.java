package com.julian.guigon.mtg.custom.collection.connector.csv.model;

import java.util.Map;

public record CsvRecord(
		Map<String, String> values
) { }