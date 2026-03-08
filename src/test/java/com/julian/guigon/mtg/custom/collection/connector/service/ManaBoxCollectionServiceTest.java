package com.julian.guigon.mtg.custom.collection.connector.service;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCollectionMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCollectionMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCollection;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCollectionRepository;
import com.julian.guigon.mtg.custom.collection.connector.manabox.service.ManaBoxCollectionService;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ManaBoxCollectionServiceTest {

	private static final UUID ID_COLLECTION = UUID.fromString("a7397c6a-a29f-4495-898c-028355708f33");
	private static final String COLLECTION_NAME = "Collection Julian";

	@InjectMocks
	private ManaBoxCollectionService sut;

	@Mock
	private ManaBoxCollectionRepository manaBoxCollectionRepository;

	@Mock
	private ManaBoxCollectionMapper manaBoxCollectionMapper;

	@Test
	void findManaBoxCollectionById_nominal_returnManaBoxCollection() {
		// GIVEN
		final ManaBoxCollectionMb manaBoxCollectionMb = Instancio.create(ManaBoxCollectionMb.class);
		final ManaBoxCollection manaBoxCollection = Instancio.create(ManaBoxCollection.class);
		Mockito.when(manaBoxCollectionRepository.findManaBoxCollectionById(ID_COLLECTION)).thenReturn(Optional.of(manaBoxCollectionMb));
		Mockito.when(manaBoxCollectionMapper.manaBoxCollectionFromManaBoxCollectionMb(manaBoxCollectionMb)).thenReturn(manaBoxCollection);

		// WHEN
		final Optional<ManaBoxCollection> result = sut.findManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(result).isPresent();
	}

	@Test
	void findManaBoxCollectionById_noData_returnNothing() {
		// GIVEN
		Mockito.when(manaBoxCollectionRepository.findManaBoxCollectionById(ID_COLLECTION)).thenReturn(Optional.empty());

		// WHEN
		final Optional<ManaBoxCollection> result = sut.findManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(result).isEmpty();
	}

	@Test
	void findManaBoxCollectionById_nullUuid_returnNothing() {
		// GIVEN
		// WHEN
		final Optional<ManaBoxCollection> result = sut.findManaBoxCollectionById(null);

		// THEN
		Assertions.assertThat(result).isEmpty();
		Mockito.verifyNoInteractions(manaBoxCollectionRepository);
	}

	@Test
	void findManaBoxCollectionByName_nominal_returnManaBoxCollection() {
		// GIVEN
		final ManaBoxCollectionMb manaBoxCollectionMb = Instancio.create(ManaBoxCollectionMb.class);
		final ManaBoxCollection manaBoxCollection = Instancio.create(ManaBoxCollection.class);
		Mockito.when(manaBoxCollectionRepository.findManaBoxCollectionByName(COLLECTION_NAME)).thenReturn(Optional.of(manaBoxCollectionMb));
		Mockito.when(manaBoxCollectionMapper.manaBoxCollectionFromManaBoxCollectionMb(manaBoxCollectionMb)).thenReturn(manaBoxCollection);

		// WHEN
		final Optional<ManaBoxCollection> result = sut.findManaBoxCollectionByName(COLLECTION_NAME);

		// THEN
		Assertions.assertThat(result).isPresent();
	}

	@Test
	void findManaBoxCollectionByName_noData_returnNothing() {
		// GIVEN
		Mockito.when(manaBoxCollectionRepository.findManaBoxCollectionByName(COLLECTION_NAME)).thenReturn(Optional.empty());

		// WHEN
		final Optional<ManaBoxCollection> result = sut.findManaBoxCollectionByName(COLLECTION_NAME);

		// THEN
		Assertions.assertThat(result).isEmpty();
	}

	@Test
	void findManaBoxCollectionByName_nullUuid_returnNothing() {
		// GIVEN
		// WHEN
		final Optional<ManaBoxCollection> result = sut.findManaBoxCollectionByName(null);

		// THEN
		Assertions.assertThat(result).isEmpty();
		Mockito.verifyNoInteractions(manaBoxCollectionRepository);
	}

	@Test
	void insertOneManaBoxCollection_nominal_returnOne() {
		// GIVEN
		final ManaBoxCollection manaBoxCollection = Instancio.create(ManaBoxCollection.class);
		final ManaBoxCollectionMb manaBoxCollectionMb = Instancio.create(ManaBoxCollectionMb.class);
		Mockito.when(manaBoxCollectionMapper.manaBoxCollectionMbFromManaBoxCollection(manaBoxCollection)).thenReturn(manaBoxCollectionMb);
		Mockito.when(manaBoxCollectionRepository.insertOneManaBoxCollection(manaBoxCollectionMb)).thenReturn(1);

		// WHEN
		final int result = sut.insertOneManaBoxCollection(manaBoxCollection);

		// THEN
		Assertions.assertThat(result).isEqualTo(1);
	}

	@Test
	void insertOneManaBoxCollection_nullInserted_returnZero() {
		// GIVEN
		// WHEN
		final int result = sut.insertOneManaBoxCollection(null);

		// THEN
		Assertions.assertThat(result).isEqualTo(0);
		Mockito.verifyNoInteractions(manaBoxCollectionRepository);
	}

	@Test
	void updateManaBoxCollection_nominal_returnOne() {
		// GIVEN
		final ManaBoxCollection manaBoxCollection = Instancio.create(ManaBoxCollection.class);
		final ManaBoxCollectionMb manaBoxCollectionMb = Instancio.create(ManaBoxCollectionMb.class);
		Mockito.when(manaBoxCollectionMapper.manaBoxCollectionMbFromManaBoxCollection(manaBoxCollection)).thenReturn(manaBoxCollectionMb);
		Mockito.when(manaBoxCollectionRepository.updateManaBoxCollection(manaBoxCollectionMb)).thenReturn(1);

		// WHEN
		final int result = sut.updateManaBoxCollection(manaBoxCollection);

		// THEN
		Assertions.assertThat(result).isEqualTo(1);
	}

	@Test
	void updateManaBoxCollection_nullInserted_returnZero() {
		// GIVEN
		// WHEN
		final int result = sut.updateManaBoxCollection(null);

		// THEN
		Assertions.assertThat(result).isEqualTo(0);
		Mockito.verifyNoInteractions(manaBoxCollectionRepository);
	}

	@Test
	void deleteManaBoxCollectionById_nominal_deleteOne() {
		// GIVEN
		Mockito.when(manaBoxCollectionRepository.deleteManaBoxCollectionById(ID_COLLECTION)).thenReturn(true);

		// WHEN
		final boolean result = sut.deleteManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(result).isTrue();
	}

	@Test
	void deleteManaBoxCollectionById_noData_deleteNothing() {
		// GIVEN
		Mockito.when(manaBoxCollectionRepository.deleteManaBoxCollectionById(ID_COLLECTION)).thenReturn(false);

		// WHEN
		final boolean result = sut.deleteManaBoxCollectionById(ID_COLLECTION);

		// THEN
		Assertions.assertThat(result).isFalse();
	}

	@Test
	void deleteManaBoxCollectionById_nullUuid_deleteNothing() {
		// GIVEN
		// WHEN
		final boolean result = sut.deleteManaBoxCollectionById(null);

		// THEN
		Assertions.assertThat(result).isFalse();
		Mockito.verifyNoInteractions(manaBoxCollectionRepository);
	}
}
