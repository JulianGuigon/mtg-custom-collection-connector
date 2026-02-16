package com.julian.guigon.mtg.custom.collection.connector.manabox.mapper;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCard;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ManaBoxCardMapper {

	ManaBoxCard manaBoxCardFromManaBoxCardMb(ManaBoxCardMb manaBoxCardMb);
	ManaBoxCardMb manaBoxCardMbFromManaBoxCard(ManaBoxCard manaBoxCard);
	List<ManaBoxCard> manaBoxCardsFromManaBoxCardMbs(List<ManaBoxCardMb> manaBoxCardMbs);
	List<ManaBoxCardMb> manaBoxCardMbsFromManaBoxCards(List<ManaBoxCard> manaBoxCards);
}
