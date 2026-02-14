package com.julian.guigon.mtg.custom.collection.connector.manabox.service;

import com.julian.guigon.mtg.custom.collection.connector.manabox.mapper.ManaBoxCardMapper;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.ManaBoxCardRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManaBoxCardService {

	private final ManaBoxCardRepository manaBoxCardRepository;
	private final ManaBoxCardMapper manaBoxCardMapper;

	public ManaBoxCardService(ManaBoxCardRepository manaBoxCardRepository, ManaBoxCardMapper manaBoxCardMapper) {
		this.manaBoxCardRepository = manaBoxCardRepository;
		this.manaBoxCardMapper = manaBoxCardMapper;
	}

	public Optional<ManaBoxCard> findManaBoxCardById(Integer id) {
		return manaBoxCardRepository.findManaBoxCardMbById(id)
				.map(manaBoxCardMapper::manaBoxCardFromManaBoxCardMb);
	}

	public List<ManaBoxCard> findAllManaBoxCards() {
		return manaBoxCardRepository.findAllManaBoxCardMb().stream()
				.map(manaBoxCardMapper::manaBoxCardFromManaBoxCardMb)
				.toList();
	}

	public int insertAllManaBoxCards(List<ManaBoxCardMb> manaBoxCardMbs) {
		if(CollectionUtils.isEmpty(manaBoxCardMbs)) {
			return 0;
		}
		return manaBoxCardRepository.insertAllManaBoxCardMb(manaBoxCardMbs);
	}

	public boolean deleteAllManaBoxCards() {
		return manaBoxCardRepository.deleteAllManaBoxCardMb();
	}
}
