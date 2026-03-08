package com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv.mapper.CsvRecordMapperImpl;
import org.apache.commons.csv.CSVRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ApacheCommonsCsvServiceTest {

	private static final String PATH = "src/test/resources/csv/book.csv";
	private static final List<String> HEADERS = List.of("author", "title");

	@InjectMocks
	private ApacheCommonsCsvService sut;

	@Mock
	private CsvRecordMapperImpl csvRecordMapper;

	@Test
	void readCsvFromPath_nominal_shouldReturnDataCsvRecords() {
		// GIVEN
		Mockito.when(csvRecordMapper.fromApacheCommonsCsvRecord(
				ArgumentMatchers.any(CSVRecord.class), ArgumentMatchers.anyList()
		)).thenAnswer(invocation -> {
			final CSVRecord csvRecord = invocation.getArgument(0);
			final List<String> headers = invocation.getArgument(1);
			final Map<String, String> result = new HashMap<>();
			for (int i = 0; i < csvRecord.values().length; i++) {
				result.put(headers.get(i), csvRecord.values()[i]);
			}
			return new CsvRecord(result);
		});

		// WHEN
		final List<CsvRecord> result = sut.readCsvFromPath(PATH, HEADERS);

		// THEN
		Assertions.assertThat(result)
				.isNotEmpty()
				.hasSize(2)
				.map(CsvRecord::values)
				.map(Map::entrySet)
				.satisfiesExactly(
						values1 -> Assertions.assertThat(values1).containsExactly(
								Assertions.entry(HEADERS.getFirst(), "Dan Simmons"),
								Assertions.entry(HEADERS.get(1), "Hyperion")
						),
						values2 -> Assertions.assertThat(values2).containsExactly(
								Assertions.entry(HEADERS.getFirst(), "Douglas Adams"),
								Assertions.entry(HEADERS.get(1), "The Hitchhiker's Guide to the Galaxy")
						)
				);
	}

	@Test
	void readCsvFromPath_wrongPath_shouldThrowRuntimeException() {
		// GIVEN
		// WHEN & THEN
		Assertions.assertThatThrownBy(() -> sut.readCsvFromPath("Wrong path", HEADERS))
				.isInstanceOf(RuntimeException.class);
	}
}
