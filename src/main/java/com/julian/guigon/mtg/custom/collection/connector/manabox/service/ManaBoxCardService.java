package com.julian.guigon.mtg.custom.collection.connector.manabox.service;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCardRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ManaBoxCardService {

	private static final int BATCH_SIZE = 1000;

	private final ManaBoxCardRepository manaBoxCardRepository;
	private final ManaBoxCardMapper manaBoxCardMapper;

	public ManaBoxCardService(ManaBoxCardRepository manaBoxCardRepository, ManaBoxCardMapper manaBoxCardMapper) {
		this.manaBoxCardRepository = manaBoxCardRepository;
		this.manaBoxCardMapper = manaBoxCardMapper;
	}

	public Optional<ManaBoxCard> findManaBoxCardById(String id) {
		if (id == null) {
			return Optional.empty();
		}
		return manaBoxCardRepository.findManaBoxCardMbById(id)
				.map(manaBoxCardMapper::manaBoxCardFromManaBoxCardMb);
	}

	public List<ManaBoxCard> findAllManaBoxCardsByIdCollection(UUID idCollection) {
		if(idCollection == null) {
			return List.of();
		}
		return Stream.of(manaBoxCardRepository.findAllManaBoxCardMbByIdCollection(idCollection))
				.map(manaBoxCardMapper::manaBoxCardsFromManaBoxCardMbs)
				.findFirst()
				.get();
	}

	public int insertAllManaBoxCards(List<ManaBoxCard> manaBoxCards) {
		if (CollectionUtils.isEmpty(manaBoxCards)) {
			return 0;
		}
		try {
			return Stream.of(manaBoxCards)
					.map(manaBoxCardMapper::manaBoxCardMbsFromManaBoxCards)
					.flatMap(list -> ListUtils.partition(list, BATCH_SIZE)
							.stream()
							.map(manaBoxCardRepository::insertAllManaBoxCardMb)
					)
					.mapToInt(Integer::intValue)
					.sum();
		} catch (Exception ignored) {
			return 0;
		}
	}

	public boolean deleteAllManaBoxCardsByIdCollection(UUID idCollection) {
		if (idCollection == null) {
			return false;
		}
		return manaBoxCardRepository.deleteAllManaBoxCardMbByIdCollection(idCollection);
	}
}
