package com.julian.guigon.mtg.custom.collection.connector.manabox.repository.typehandler;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.RarityEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RarityEnumTypeHandler extends BaseTypeHandler<RarityEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, RarityEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public RarityEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return RarityEnum.fromString(rs.getString(columnName));
	}

	@Override
	public RarityEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return RarityEnum.fromString(rs.getString(columnIndex));
	}

	@Override
	public RarityEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return RarityEnum.fromString(cs.getString(columnIndex));
	}
}
