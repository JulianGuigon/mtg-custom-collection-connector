package com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv.mapper;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv.mapper.CsvRecordMapperImpl;
import org.apache.commons.csv.CSVRecord;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CsvRecordMapperTest {

	@InjectMocks
	private CsvRecordMapperImpl sut;

	@Test
	void fromApacheCommonsCsvRecord_nominal_shouldMapWithoutError() {
		// GIVEN
		final String[] values = new String[]{"Dan Simmons", "Hyperion"};
		final CSVRecord csvRecord = Instancio.of(CSVRecord.class)
				.set(Select.field(CSVRecord::values), values)
				.create();
		final List<String> headers = List.of("author", "title");

		// WHEN
		final CsvRecord result = sut.fromApacheCommonsCsvRecord(csvRecord, headers);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.extracting(CsvRecord::values)
				.asInstanceOf(InstanceOfAssertFactories.MAP)
				.contains(
						Assertions.entry("author", "Dan Simmons"),
						Assertions.entry("title", "Hyperion")
				);

	}

	@Test
	void fromApacheCommonsCsvRecord_noValues_shouldMapWithoutErrorWithNoValues() {
		// GIVEN
		final String[] values = new String[]{};
		final CSVRecord csvRecord = Instancio.of(CSVRecord.class)
				.set(Select.field(CSVRecord::values), values)
				.create();
		final List<String> headers = List.of("author", "title");

		// WHEN
		final CsvRecord result = sut.fromApacheCommonsCsvRecord(csvRecord, headers);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.extracting(CsvRecord::values)
				.asInstanceOf(InstanceOfAssertFactories.MAP)
				.isEmpty();
	}

	@Test
	void fromApacheCommonsCsvRecord_nullValues_shouldReturnNull() {
		// GIVEN
		// WHEN
		final CsvRecord result = sut.fromApacheCommonsCsvRecord(null, null);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}
}
