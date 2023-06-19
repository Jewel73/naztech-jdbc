package com.naztech.naztechjdbc.service;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.web.server.MimeMappings.Mapping;

import com.naztech.naztechjdbc.utils.MappingUtils;


/**
 * @author jewel
 * @version 1.1
 */

public class NaztechJdbcService {


	private final DataSource dataSource;

	/**
	 * @param dataSource
	 */
	public NaztechJdbcService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param <T>
	 * @param spName
	 * @param className
	 * @param spArgs
	 * @return List<Object>
	 * @throws Exception 
	 */
	public <T> List<T> executeStoreProcedure(String spName, T obj) throws Exception{

		List<T> resultList = new ArrayList<>();
		Class<T> className = (Class<T>) obj.getClass();
		
		try(Connection connection = dataSource.getConnection();
			CallableStatement callableStatement = prepareStatement(connection, spName, obj);
			ResultSet resultSet = callableStatement.executeQuery();){

			while(resultSet.next()) {
				T object = mapResultSetToObject(resultSet, className);
				resultList.add(object);
			}
		}
		
		return resultList;
	}

	/**
	 * @param <T>
	 * @param resultSet
	 * @param className
	 * @return T Object
	 * @throws Exception
	 */
	private <T> T mapResultSetToObject(ResultSet resultSet, Class<T> className) throws  Exception {

		T resultObject = className.getDeclaredConstructor().newInstance();
		var metadata = resultSet.getMetaData();
		var columnsCount = metadata.getColumnCount();

		for(int i=1; i<= columnsCount; i++) {

			String key = metadata.getColumnName(i);
			Object value = resultSet.getObject(i);
			setProperty(resultObject, key , value);

		}

		return resultObject;

	}


	/**
	 * @param object
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	private void setProperty(Object object, String key, Object value) throws Exception {

		Field field = object.getClass().getDeclaredField(key);
		field.setAccessible(true);
		field.set(object, value);
	}

	/**
	 * @param connection
	 * @param spName
	 * @param spArgs
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	private <T> CallableStatement prepareStatement(Connection connection, String spName, T obj) throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Map<String, Object> spArgs = MappingUtils.mapObjectToStoreProcedureArgs(obj, connection, spName);
		
		
		
		String parameterPlaceholder = createParameterPlaceHolder(spArgs.size());
		String query = String.format("{call %s ( %s ) }", spName, parameterPlaceholder);
		CallableStatement callableStatement = connection.prepareCall(query);

		for(Map.Entry<String, Object> entry : spArgs.entrySet()) {

			String parameter = entry.getKey();
			Object value  = entry.getValue();
			callableStatement.setObject(parameter, value);

		}

		return callableStatement;
	}

	/**
	 * @param size
	 * @return
	 */

	private String createParameterPlaceHolder(int size) {

		StringBuilder placeholders = new StringBuilder();
		for(int i=0; i<size; i++) {
			if(i>0) 
				placeholders.append(", ?");
			else
				placeholders.append("?");
		}

		return placeholders.toString();
	}

}
