package com.julian.guigon.mtg.custom.collection.connector.csv.apache.commons.csv.mapper;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import org.apache.commons.csv.CSVRecord;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CsvRecordMapper {

	@Mapping(target = "values", expression = "java(this.mapValues(csvRecord.values(), headers))")
	public abstract CsvRecord fromApacheCommonsCsvRecord(CSVRecord csvRecord, List<String> headers);

	public Map<String, String> mapValues(String[] values, List<String> headers) {
		if(values.length != headers.size()) {
			return Collections.emptyMap();
		}
		return IntStream.range(0, headers.size()).boxed()
				.collect(Collectors.toMap(headers::get, Arrays.stream(values).toList()::get));
	}
}