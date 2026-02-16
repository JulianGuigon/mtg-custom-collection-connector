package com.julian.guigon.mtg.custom.collection.connector.manabox.repository;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.entity.ManaBoxCardMb;
import com.julian.guigon.mtg.custom.collection.connector.manabox.repository.typehandler.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ManaBoxCardRepository {

	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "scryfallId", column = "scryfall_id"),
			@Result(property = "binderName", column = "binder_name"),
			@Result(property = "binderType", column = "binder_type"),
			@Result(property = "name", column = "name"),
			@Result(property = "setCode", column = "set_code"),
			@Result(property = "setName", column = "set_name"),
			@Result(property = "collectorNumber", column = "collector_number"),
			@Result(property = "foil", column = "foil", typeHandler = FoilEnumTypeHandler.class),
			@Result(property = "rarity", column = "rarity", typeHandler = RarityEnumTypeHandler.class),
			@Result(property = "quantity", column = "quantity"),
			@Result(property = "purchasePrice", column = "purchase_price"),
			@Result(property = "misprint", column = "misprint"),
			@Result(property = "altered", column = "altered"),
			@Result(property = "condition", column = "condition", typeHandler = ConditionEnumTypeHandler.class),
			@Result(property = "language", column = "language", typeHandler = LanguageEnumTypeHandler.class),
			@Result(property = "purchasePriceCurrency", column = "purchase_price_currency", typeHandler = CurrencyEnumTypeHandler.class),
			@Result(property = "idCollection", column = "id_collection")
	})
	@Select("""
			SELECT
				c.id,
				c.scryfall_id,
				c.binder_name,
				c.binder_type,
				c.name,
				c.set_code,
				c.set_name,
				c.collector_number,
				c.foil,
				c.rarity,
				c.quantity,
				c.purchase_price,
				c.misprint,
				c.altered,
				c.condition,
				c.language,
				c.purchase_price_currency,
				c.id_collection
			FROM m3c.mana_box_card c
			WHERE c.id = #{id}""")
	Optional<ManaBoxCardMb> findManaBoxCardMbById(Integer id);

	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "scryfallId", column = "scryfall_id"),
			@Result(property = "binderName", column = "binder_name"),
			@Result(property = "binderType", column = "binder_type"),
			@Result(property = "name", column = "name"),
			@Result(property = "setCode", column = "set_code"),
			@Result(property = "setName", column = "set_name"),
			@Result(property = "collectorNumber", column = "collector_number"),
			@Result(property = "foil", column = "foil", typeHandler = FoilEnumTypeHandler.class),
			@Result(property = "rarity", column = "rarity", typeHandler = RarityEnumTypeHandler.class),
			@Result(property = "quantity", column = "quantity"),
			@Result(property = "purchasePrice", column = "purchase_price"),
			@Result(property = "misprint", column = "misprint"),
			@Result(property = "altered", column = "altered"),
			@Result(property = "condition", column = "condition", typeHandler = ConditionEnumTypeHandler.class),
			@Result(property = "language", column = "language", typeHandler = LanguageEnumTypeHandler.class),
			@Result(property = "purchasePriceCurrency", column = "purchase_price_currency", typeHandler = CurrencyEnumTypeHandler.class),
			@Result(property = "idCollection", column = "id_collection")
	})
	@Select("""
			SELECT
				c.id,
				c.scryfall_id,
				c.binder_name,
				c.binder_type,
				c.name,
				c.set_code,
				c.set_name,
				c.collector_number,
				c.foil,
				c.rarity,
				c.quantity,
				c.purchase_price,
				c.misprint,
				c.altered,
				c.condition,
				c.language,
				c.purchase_price_currency,
				c.id_collection
			FROM m3c.mana_box_card c
			WHERE c.id_collection = #{idCollection}""")
	List<ManaBoxCardMb> findAllManaBoxCardMbByIdCollection(UUID idCollection);

	@Insert({
			"<script>",
			"<if test='cards != null and cards.size() > 0'>",
			"INSERT INTO m3c.mana_box_card (",
			"id, scryfall_id, binder_name, binder_type, name, set_code, set_name, ",
			"collector_number, foil, rarity, quantity, purchase_price, misprint, ",
			"altered, condition, language, purchase_price_currency, id_collection",
			") VALUES ",
			"<foreach collection='cards' item='c' separator=','>",
			"(",
			"#{c.id},",
			"#{c.scryfallId},",
			"#{c.binderName},",
			"#{c.binderType},",
			"#{c.name},",
			"#{c.setCode},",
			"#{c.setName},",
			"#{c.collectorNumber},",
			"#{c.foil},",
			"#{c.rarity},",
			"#{c.quantity},",
			"#{c.purchasePrice},",
			"#{c.misprint},",
			"#{c.altered},",
			"#{c.condition},",
			"#{c.language},",
			"#{c.purchasePriceCurrency},",
			"#{c.idCollection}",
			")",
			"</foreach>",
			"</if>",
			"</script>"
	})
	int insertAllManaBoxCardMb(@Param("cards") List<ManaBoxCardMb> cards);

	@Delete("DELETE FROM m3c.mana_box_card c WHERE c.id_collection = #{idCollection}")
	boolean deleteAllManaBoxCardMbByIdCollection(UUID idCollection);
}
