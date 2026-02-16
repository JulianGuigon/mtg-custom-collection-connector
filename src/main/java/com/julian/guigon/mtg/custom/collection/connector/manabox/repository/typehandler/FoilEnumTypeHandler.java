package com.julian.guigon.mtg.custom.collection.connector.manabox.repository.typehandler;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.FoilEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

public class FoilEnumTypeHandler extends BaseTypeHandler<FoilEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, FoilEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public FoilEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return FoilEnum.fromString(rs.getString(columnName));
	}

	@Override
	public FoilEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return FoilEnum.fromString(rs.getString(columnIndex));
	}

	@Override
	public FoilEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return FoilEnum.fromString(cs.getString(columnIndex));
	}
}
