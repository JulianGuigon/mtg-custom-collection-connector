package com.julian.guigon.mtg.custom.collection.connector.manabox.service;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCollectionMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCollection;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ManaBoxCollectionService {

	private final ManaBoxCollectionRepository manaBoxCollectionRepository;
	private final ManaBoxCollectionMapper manaBoxCollectionMapper;

	public ManaBoxCollectionService(ManaBoxCollectionRepository manaBoxCollectionRepository, ManaBoxCollectionMapper manaBoxCollectionMapper) {
		this.manaBoxCollectionRepository = manaBoxCollectionRepository;
		this.manaBoxCollectionMapper = manaBoxCollectionMapper;
	}

	public Optional<ManaBoxCollection> findManaBoxCollectionById(UUID id) {
		if(id == null) {
			return Optional.empty();
		}
		return manaBoxCollectionRepository.findManaBoxCollectionById(id)
				.map(manaBoxCollectionMapper::manaBoxCollectionFromManaBoxCollectionMb);
	}

	public int insertOneManaBoxCollection(ManaBoxCollection manaBoxCollection) {
		if (manaBoxCollection == null) {
			return 0;
		}
		return Stream.of(manaBoxCollection)
				.map(manaBoxCollectionMapper::manaBoxCollectionMbFromManaBoxCollection)
				.map(manaBoxCollectionRepository::insertOneManaBoxCollection)
				.findFirst()
				.get();
	}

	public boolean deleteManaBoxCollectionById(UUID id) {
		if (id == null) {
			return false;
		}
		return manaBoxCollectionRepository.deleteManaBoxCollectionById(id);
	}
}
