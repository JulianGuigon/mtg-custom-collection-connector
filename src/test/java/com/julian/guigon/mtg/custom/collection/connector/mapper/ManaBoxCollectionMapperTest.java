package com.julian.guigon.mtg.custom.collection.connector.mapper;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCollectionMapperImpl;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCollectionMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCollection;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ManaBoxCollectionMapperTest {

	@InjectMocks
	private ManaBoxCollectionMapperImpl sut;

	@Test
	void manaBoxCollectionFromManaBoxCollectionMb_nominal_shouldMapWithoutError() {
		// GIVEN
		final ManaBoxCollectionMb manaBoxCollectionMb = Instancio.create(ManaBoxCollectionMb.class);

		// WHEN
		final ManaBoxCollection result = sut.manaBoxCollectionFromManaBoxCollectionMb(manaBoxCollectionMb);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfies(manaBoxCollection -> {
					Assertions.assertThat(manaBoxCollection.id()).isEqualTo(manaBoxCollectionMb.getId());
					Assertions.assertThat(manaBoxCollection.name()).isEqualTo(manaBoxCollectionMb.getName());
					Assertions.assertThat(manaBoxCollection.creationDate()).isEqualTo(manaBoxCollectionMb.getCreationDate());
					Assertions.assertThat(manaBoxCollection.binderNames()).isEqualTo(manaBoxCollectionMb.getBinderNames());
				});
	}

	@Test
	void manaBoxCollectionMbFromManaBoxCollection_nominal_shouldMapWithoutError() {
		// GIVEN
		final ManaBoxCollection manaBoxCollection = Instancio.create(ManaBoxCollection.class);

		// WHEN
		final ManaBoxCollectionMb result = sut.manaBoxCollectionMbFromManaBoxCollection(manaBoxCollection);

		// THEN
		Assertions.assertThat(result)
				.isNotNull()
				.satisfies(manaBoxCollectionMb -> {
					Assertions.assertThat(manaBoxCollectionMb.getId()).isEqualTo(manaBoxCollection.id());
					Assertions.assertThat(manaBoxCollectionMb.getName()).isEqualTo(manaBoxCollection.name());
					Assertions.assertThat(manaBoxCollectionMb.getCreationDate()).isEqualTo(manaBoxCollection.creationDate());
					Assertions.assertThat(manaBoxCollectionMb.getBinderNames()).isEqualTo(manaBoxCollection.binderNames());
				});
	}

	@Test
	void manaBoxCollectionFromManaBoxCollectionMb_nullValue_shouldReturnNull() {
		// GIVEN
		final ManaBoxCollectionMb manaBoxCollectionMb = null;

		// WHEN
		final ManaBoxCollection result = sut.manaBoxCollectionFromManaBoxCollectionMb(manaBoxCollectionMb);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}

	@Test
	void manaBoxCollectionMbFromManaBoxCollection_nullValue_shouldReturnNull() {
		// GIVEN
		final ManaBoxCollection manaBoxCollection = null;

		// WHEN
		final ManaBoxCollectionMb result = sut.manaBoxCollectionMbFromManaBoxCollection(manaBoxCollection);

		// THEN
		Assertions.assertThat(result)
				.isNull();
	}
}
