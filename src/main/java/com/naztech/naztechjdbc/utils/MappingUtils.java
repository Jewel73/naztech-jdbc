package com.naztech.naztechjdbc.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.jdbc.CallableStatement;

public class MappingUtils {

	public static <T> Map<String, Object> mapObjectToStoreProcedureArgs(T object, Connection connection, String spName) throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Map<String, Object> args = new HashMap<>();
		
		Class<?> clazz  = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		ResultSet resultSet = connection.getMetaData().getProcedureColumns(null, null, spName, null);
		
		while(resultSet.next()) {
			String argName = resultSet.getString("COLUMN_NAME");
			Field field = clazz.getDeclaredField(argName);
			field.setAccessible(true);
			Object value = field.get(object);
			
			args.put(argName, value);
		}
		
		return args;
		
	}
}
