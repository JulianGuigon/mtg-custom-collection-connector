package com.julian.guigon.mtg.custom.collection.connector.manabox.repository.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class BinderNamesTypeHandler extends BaseTypeHandler<List<String>> {
	@Value("${m3c.binder.names.separator}")
	private String separator;

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, String.join(separator, parameter));
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return Arrays.stream(rs.getString(columnName)
						.split(Pattern.quote(separator)))
				.toList();
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return Arrays.stream(rs.getString(columnIndex)
						.split(Pattern.quote(separator)))
				.toList();
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return Arrays.stream(cs.getString(columnIndex)
						.split(Pattern.quote(separator)))
				.toList();
	}
}
