package com.julian.guigon.mtg.custom.collection.connector.common.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class ZonedDateTimeTypeHandler extends BaseTypeHandler<ZonedDateTime> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, ZonedDateTime parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setObject(i, parameter.toOffsetDateTime());
	}

	@Override
	public ZonedDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
		OffsetDateTime odt = rs.getObject(columnName, OffsetDateTime.class);
		return odt != null ? odt.toZonedDateTime() : null;
	}

	@Override
	public ZonedDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		OffsetDateTime odt = rs.getObject(columnIndex, OffsetDateTime.class);
		return odt != null ? odt.toZonedDateTime() : null;
	}

	@Override
	public ZonedDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		OffsetDateTime odt = cs.getObject(columnIndex, OffsetDateTime.class);
		return odt != null ? odt.toZonedDateTime() : null;
	}
}

