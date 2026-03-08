package com.julian.guigon.mtg.custom.collection.connector.manabox.service;

import com.julian.guigon.mtg.custom.collection.connector.csv.service.CsvReaderService;
import com.julian.guigon.mtg.custom.collection.connector.manabox.constant.ManaBoxConstants;
import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class ManaBoxCsvImportService {
	private final CsvReaderService csvReaderService;
	private final ManaBoxCardMapper manaBoxCardMapper;
	private final ManaBoxCardService manaBoxCardService;
	private final ManaBoxCollectionService manaBoxCollectionService;

	public ManaBoxCsvImportService(CsvReaderService csvReaderService, ManaBoxCardMapper manaBoxCardMapper, ManaBoxCardService manaBoxCardService, ManaBoxCollectionService manaBoxCollectionService) {
		this.csvReaderService = csvReaderService;
		this.manaBoxCardMapper = manaBoxCardMapper;
		this.manaBoxCardService = manaBoxCardService;
		this.manaBoxCollectionService = manaBoxCollectionService;
	}

	public ImportResult importManaBoxCsv(String path, String collectionName, boolean newCollection) {
		validateParams(path, collectionName);
		final ManaBoxCollection collection = getManaBoxCollection(collectionName, newCollection);
		final ParsedCards cards = ParsedCardsBuilder.builder()
				.cards(readCardsFromCsv(path, collection))
				.build();
		return clearAndCreateCollection(collection, cards);
	}

	@Transactional
	private ImportResult clearAndCreateCollection(ManaBoxCollection collection, ParsedCards cards) {
		manaBoxCardService.deleteAllManaBoxCardsByIdCollection(collection.id());
		final int insertedCount = manaBoxCardService.insertAllManaBoxCards(cards.cards());
		final ManaBoxCollection newCollection = new ManaBoxCollection(collection.id(), collection.name(), ZonedDateTime.now(), cards.getCardsBinderNames());
		manaBoxCollectionService.updateManaBoxCollection(newCollection);
		return new ImportResult(newCollection.id(), insertedCount, newCollection.binderNames());
	}

	private static void validateParams(String path, String collectionName) {
		if (StringUtils.isBlank(path)) {
			throw new IllegalArgumentException("Le path vers le fichier csv ne devrait pas être vide.");
		}
		if (StringUtils.isBlank(collectionName)) {
			throw new IllegalArgumentException("Le nom de la collection ne devrait pas être vide.");
		}
	}

	private ManaBoxCollection getManaBoxCollection(String collectionName, boolean newCollection) {
		if(newCollection) {
			manaBoxCollectionService.insertOneManaBoxCollection(
					new ManaBoxCollection(UUID.randomUUID(), collectionName, ZonedDateTime.now(), List.of())
			);
		}
		Optional<ManaBoxCollection> collection = manaBoxCollectionService.findManaBoxCollectionByName(collectionName);
		if (collection.isEmpty() && !newCollection) {
			throw new IllegalArgumentException("Aucune collection n'existe pour le nom spécifié. " +
											   "Veuillez spécifier newCollection=true en paramètre de requête dans le " +
											   "cas d'une création d'une nouvelle collection.");
		}
		return collection.get();
	}

	private List<ManaBoxCard> readCardsFromCsv(String path, ManaBoxCollection collection) {
		try {
			return Stream.of(csvReaderService.readCsvFromPath(path, ManaBoxConstants.HEADERS))
					.map(records -> manaBoxCardMapper.manaBoxCardsFromCsvRecords(records, collection.id()))
					.flatMap(List::stream)
					.toList();
		} catch (RuntimeException exception) {
			throw new IllegalArgumentException("Erreur lors de la lecture du fichier csv. Cause : "+exception.getMessage());
		}
	}
}
