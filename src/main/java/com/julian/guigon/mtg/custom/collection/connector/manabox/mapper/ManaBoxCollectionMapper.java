package com.julian.guigon.mtg.custom.collection.connector.manabox.mapper;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCollectionMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.model.pojo.ManaBoxCollection;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ManaBoxCollectionMapper {

	ManaBoxCollection manaBoxCollectionFromManaBoxCollectionMb(ManaBoxCollectionMb manaBoxCollectionMb);
	ManaBoxCollectionMb manaBoxCollectionMbFromManaBoxCollection(ManaBoxCollection manaBoxCollection);
}
