package com.julian.guigon.mtg.custom.collection.connector.service;

import com.julian.guigon.mtg.custom.collection.connector.csv.model.CsvRecord;
import com.julian.guigon.mtg.custom.collection.connector.csv.service.CsvReaderService;
import com.julian.guigon.mtg.custom.collection.connector.manabox.constant.ManaBoxConstants;
import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ImportResult;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCollection;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCardService;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCollectionService;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCsvImportService;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Select;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ManaBoxCsvImportServiceTest {

	private static final String PATH = "csv/small_collection.csv";
	private static final String COLLECTION_NAME = "Collection Julian";
	private static final UUID ID_COLLECTION = UUID.randomUUID();
	private static final boolean NO_NEW_COLLECTION = false;

	@InjectMocks
	private ManaBoxCsvImportService sut;

	@Mock
	private CsvReaderService csvReaderService;

	@Mock
	private ManaBoxCardMapper manaBoxCardMapper;

	@Mock
	private ManaBoxCardService manaBoxCardService;

	@Mock
	private ManaBoxCollectionService manaBoxCollectionService;

	@Captor
	private ArgumentCaptor<List<ManaBoxCard>> acCards;

	@ParameterizedTest
	@NullAndEmptySource
	void importManaBoxCsv_nullOfEmptyPath_importNothingWithIdCollectionAndThrowError(String path) {
		// GIVEN
		// WHEN & THEN
		Assertions.assertThatThrownBy(() -> sut.importManaBoxCsv(path, COLLECTION_NAME, NO_NEW_COLLECTION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Le path vers le fichier csv ne devrait pas être vide.");
	}

	@ParameterizedTest
	@NullAndEmptySource
	void importManaBoxCsv_nullOfEmptycollectionName_importNothingWithIdCollectionAndThrowError(String collectionName) {
		// GIVEN
		// WHEN & THEN
		Assertions.assertThatThrownBy(() -> sut.importManaBoxCsv(PATH, collectionName, NO_NEW_COLLECTION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Le nom de la collection ne devrait pas être vide.");
	}

	@ParameterizedTest
	@CsvSource({"true", "false"})
	void importManaBoxCsv_nominal_importAllElementsWithIdCollection(boolean newCollection) {
		// GIVEN
		final ManaBoxCollection manaBoxCollection = generateRandomCollection();
		final List<CsvRecord> records = generate3RandomCsvRecords();
		final List<ManaBoxCard> manaBoxCards = generate3RandomManaBoxCards();
		Mockito.when(manaBoxCollectionService.findManaBoxCollectionByName(COLLECTION_NAME)).thenReturn(Optional.of(manaBoxCollection));
		Mockito.when(csvReaderService.readCsvFromPath(PATH, ManaBoxConstants.HEADERS)).thenReturn(records);
		Mockito.when(manaBoxCardMapper.manaBoxCardsFromCsvRecords(records, ID_COLLECTION)).thenReturn(manaBoxCards);
		Mockito.when(manaBoxCardService.insertAllManaBoxCards(acCards.capture())).thenAnswer(
				invocation -> ((List<?>) invocation.getArgument(0)).size()
		);
		Mockito.lenient().when(manaBoxCollectionService.insertOneManaBoxCollection(ArgumentMatchers.any(ManaBoxCollection.class))).thenReturn(1);

		// WHEN
		final ImportResult result = sut.importManaBoxCsv(PATH, COLLECTION_NAME, newCollection);

		// THEN
		Assertions.assertThat(result)
				.satisfies(
						importResult -> {
							Assertions.assertThat(importResult.idCollection()).isNotNull().isEqualTo(ID_COLLECTION);
							Assertions.assertThat(importResult.imported()).isEqualTo(3);
						}
				);
		Mockito.verify(manaBoxCollectionService, Mockito.times(newCollection ? 1 : 0)).insertOneManaBoxCollection(ArgumentMatchers.any());
		Mockito.verify(manaBoxCardService).deleteAllManaBoxCardsByIdCollection(ID_COLLECTION);
		Mockito.verify(manaBoxCollectionService).updateManaBoxCollection(ArgumentMatchers.any(ManaBoxCollection.class));
		Mockito.verify(manaBoxCardService).insertAllManaBoxCards(acCards.getValue());
	}

	@Test
	void importManaBoxCsv_collectionNotFound_importNothingWithIdCollectionAndThrowError() {
		// GIVEN
		Mockito.when(manaBoxCollectionService.findManaBoxCollectionByName(COLLECTION_NAME)).thenReturn(Optional.empty());

		// WHEN & THEN
		Assertions.assertThatThrownBy(() -> sut.importManaBoxCsv(PATH, COLLECTION_NAME, NO_NEW_COLLECTION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Aucune collection n'existe pour le nom spécifié. " +
							"Veuillez spécifier newCollection=true en paramètre de requête dans le " +
							"cas d'une création d'une nouvelle collection.");
	}

	@Test
	void importManaBoxCsv_csvFileIsCorrupted_importNothingWithIdCollectionAndThrowError() {
		// GIVEN
		final ManaBoxCollection manaBoxCollection = generateRandomCollection();
		Mockito.when(manaBoxCollectionService.findManaBoxCollectionByName(COLLECTION_NAME)).thenReturn(Optional.of(manaBoxCollection));
		final Throwable exception = new RuntimeException("Error at line 1");
		Mockito.when(csvReaderService.readCsvFromPath(PATH, ManaBoxConstants.HEADERS)).thenThrow(exception);

		// WHEN & THEN
		Assertions.assertThatThrownBy(() -> sut.importManaBoxCsv(PATH, COLLECTION_NAME, NO_NEW_COLLECTION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Erreur lors de la lecture du fichier csv. Cause : " + exception.getMessage());
	}

	private static ManaBoxCollection generateRandomCollection() {
		return Instancio.of(ManaBoxCollection.class)
				.set(Select.field(ManaBoxCollection::name), COLLECTION_NAME)
				.set(Select.field(ManaBoxCollection::id), ID_COLLECTION)
				.create();
	}

	private static @NotNull List<ManaBoxCard> generate3RandomManaBoxCards() {
		return List.of(
				Instancio.create(ManaBoxCard.class),
				Instancio.create(ManaBoxCard.class),
				Instancio.create(ManaBoxCard.class)
		);
	}

	private static @NotNull List<CsvRecord> generate3RandomCsvRecords() {
		return List.of(
				Instancio.create(CsvRecord.class),
				Instancio.create(CsvRecord.class),
				Instancio.create(CsvRecord.class)
		);
	}
}
