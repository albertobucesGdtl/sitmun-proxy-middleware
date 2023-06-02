package org.sitmun.proxy.middleware.decorator.request;

import org.sitmun.proxy.middleware.dto.DatasourcePayloadDto;
import org.sitmun.proxy.middleware.dto.PayloadDto;
import org.sitmun.proxy.middleware.service.JdbcDatabaseRequest;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionDecorator implements RequestDecorator<JdbcDatabaseRequest, DatasourcePayloadDto> {

  @Override
  public boolean accept(PayloadDto payload) {
    return payload instanceof DatasourcePayloadDto;
  }

  @Override
  public void apply(JdbcDatabaseRequest globalRequest, DatasourcePayloadDto payload) {
    globalRequest.setConnection(getConnection(payload));
  }

  private Connection getConnection(DatasourcePayloadDto datasourcePayload) {
    Connection connection = null;
    try {
      Class.forName(datasourcePayload.getDriver());
      connection = DriverManager.getConnection(datasourcePayload.getUri(), datasourcePayload.getUser(), datasourcePayload.getPassword());
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
