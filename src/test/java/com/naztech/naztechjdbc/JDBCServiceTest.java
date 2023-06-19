package com.naztech.naztechjdbc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.naztech.naztechjdbc.service.NaztechJdbcService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JDBCServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private CallableStatement callableStatement;

    @Mock
    private ResultSet resultSet;

    private NaztechJdbcService jdbcService;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);
        jdbcService = new NaztechJdbcService(dataSource);

        // Mock the DataSource and Connection behavior
        when(dataSource.getConnection()).thenReturn(connection);

        // Mock the Connection and CallableStatement behavior
        when(connection.prepareCall(anyString())).thenReturn(callableStatement);

        // Mock the CallableStatement behavior
        when(callableStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false); // Simulate a single row in the result set
        when(resultSet.getObject(anyInt())).thenReturn("Mocked Value");
    }

    @Test
    public void testExecuteStoreProcedure() throws Exception {
        // Prepare test data
        String spName = "my_stored_procedure";
        Map<String, Object> spArgs = new HashMap<>();

        // Mock the mapping class
        class TestClass {
            private String param1;
            private String param2;

            public TestClass() {
            }

            public String getParam1() {
                return param1;
            }

            public void setParam1(String param1) {
                this.param1 = param1;
            }

            public String getParam2() {
                return param2;
            }

            public void setParam2(String param2) {
                this.param2 = param2;
            }
        }

        // Execute the stored procedure
        List<TestClass> result = jdbcService.executeStoreProcedure(spName, TestClass.class, spArgs);

        // Verify the interactions with the mocked objects
        verify(dataSource).getConnection();
        verify(connection).prepareCall(anyString());
        verify(callableStatement).setObject(anyString(), any());
        verify(callableStatement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet, times(2)).getObject(anyInt());

        // Verify the result
        assertEquals(1, result.size());
        TestClass testObject = result.get(0);
        assertEquals("Mocked Value", testObject.getParam1());
        assertEquals("Mocked Value", testObject.getParam2());
    }
}
