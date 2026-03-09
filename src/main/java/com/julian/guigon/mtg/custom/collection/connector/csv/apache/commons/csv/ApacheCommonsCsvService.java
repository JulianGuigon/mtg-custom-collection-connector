package com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv;

import com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv.mapper.CsvRecordMapper;
import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import com.julian.guigon.mtg.custom.collection.connector.csv.service.CsvReaderService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApacheCommonsCsvService implements CsvReaderService {
	
	private final CsvRecordMapper csvRecordMapper;

	private ApacheCommonsCsvService(final CsvRecordMapper csvRecordMapper) {
		this.csvRecordMapper = csvRecordMapper;
	}

	public List<CsvRecord> readCsvFromPath(String path, List<String> headers) {
		return wrapWithReader(path, headers, this::readCsvFromPath);
	}

	private List<CsvRecord> readCsvFromPath(Reader reader, List<String> headers) throws IOException {
		final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
				.setHeader(headers.toArray(String[]::new))
				.setSkipHeaderRecord(true)
				.get();
		final Iterable<CSVRecord> records = csvFormat.parse(reader);
		final List<CSVRecord> result = new ArrayList<>();
		records.forEach(result::add);
		return result.stream()
				.map(csvRecord -> csvRecordMapper.fromApacheCommonsCsvRecord(csvRecord, headers))
				.toList();
	}

	private List<CsvRecord> wrapWithReader(String path, List<String> headers, Wrappable method) {
		try (final Reader reader = new FileReader(path)) {
			return method.readCsv(reader, headers);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FunctionalInterface
	interface Wrappable {
		List<CsvRecord> readCsv(Reader reader, List<String> headers) throws IOException;
	}
}
