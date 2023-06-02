package org.sitmun.proxy.middleware.decorator.request;

import org.sitmun.proxy.middleware.dto.DatasourcePayloadDto;
import org.sitmun.proxy.middleware.dto.PayloadDto;
import org.sitmun.proxy.middleware.service.JdbcDatabaseRequest;
import org.springframework.stereotype.Component;

@Component
public class QueryDecorator implements RequestDecorator<JdbcDatabaseRequest, DatasourcePayloadDto> {

  @Override
  public boolean accept(PayloadDto payload) {
    return payload instanceof DatasourcePayloadDto;
  }

  @Override
  public void apply(JdbcDatabaseRequest globalRequest, DatasourcePayloadDto payload) {
    globalRequest.setSql(payload.getSql());
  }

}
