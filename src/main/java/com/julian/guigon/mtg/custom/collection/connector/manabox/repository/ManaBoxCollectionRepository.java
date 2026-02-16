package com.julian.guigon.mtg.custom.collection.connector.manabox.repository;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCollectionMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.typehandler.BinderNamesTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ManaBoxCollectionRepository {

	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "name", column = "name"),
			@Result(property = "creationDate", column = "creation_date"),
			@Result(property = "binderNames", column = "binder_names", typeHandler = BinderNamesTypeHandler.class)
	})
	@Select("""
			SELECT
				c.id,
				c.name,
				c.creation_date,
				c.binder_names
			FROM m3c.mana_box_collection c
			WHERE c.id = #{id}""")
	Optional<ManaBoxCollectionMb> findManaBoxCollectionById(UUID id);

	@Insert(value = """
			INSERT INTO m3c.mana_box_collection (
				id,
				name,
				creation_date,
				binder_names
			) VALUES (
				#{id},
				#{name},
				#{creationDate},
				#{binderNames}
			)
			""")
	int insertOneManaBoxCollection(ManaBoxCollectionMb c);

	@Delete("DELETE FROM m3c.mana_box_collection WHERE id=#{id}")
	boolean deleteManaBoxCollectionById(UUID id);
}
