package org.sitmun.proxy.middleware.service;

import org.sitmun.proxy.middleware.request.RemoteRequest;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcDatabaseRequest implements RemoteRequest {
  private String sql;
  private Connection connection;

  public JdbcDatabaseRequest setSql(String sql)  {
    this.sql = sql;
    return this;
  }

  public JdbcDatabaseRequest setConnection(Connection connection) {
    this.connection = connection;
    return this;
  }

  @Override
  public ResponseEntity<?> execute() {
    List<Map<String, Object>> result = new ArrayList<>();
    try(Connection currentConnection = connection) {
      executeStatement(currentConnection, result);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok().body(result);
  }

  private void executeStatement(Connection connection, List<Map<String, Object>> result) {
    try(Statement stmt = connection.createStatement()) {
      retrieveResultSetMetadata(stmt, result);
    }  catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void retrieveResultSetMetadata(Statement stmt, List<Map<String, Object>> result) {
    try(ResultSet resultSet = stmt.executeQuery(sql)) {
      ResultSetMetaData rsmd = resultSet.getMetaData();
      while (resultSet.next()) {
        Map<String, Object> row = new HashMap<>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          Object value = resultSet.getObject(i);
          row.put(rsmd.getColumnLabel(i), value);
        }
        result.add(row);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
