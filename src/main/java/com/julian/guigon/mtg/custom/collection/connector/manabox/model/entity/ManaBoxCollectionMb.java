package com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ManaBoxCollectionMb {
	private UUID id;
	private String name;
	private ZonedDateTime creationDate;
	private List<String> binderNames;
}
