package com.julian.guigon.mtg.custom.collection.connector.manabox.repository.typehandler;

import com.julian.guigon.mtg.custom.collection.connector.manabox.model.enums.CurrencyEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyEnumTypeHandler extends BaseTypeHandler<CurrencyEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, CurrencyEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public CurrencyEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return CurrencyEnum.fromString(rs.getString(columnName));
	}

	@Override
	public CurrencyEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return CurrencyEnum.fromString(rs.getString(columnIndex));
	}

	@Override
	public CurrencyEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return CurrencyEnum.fromString(cs.getString(columnIndex));
	}
}
