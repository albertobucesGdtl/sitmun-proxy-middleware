package org.sitmun.proxy.middleware.service;

import org.sitmun.proxy.middleware.decorator.request.RequestDecorator;
import org.sitmun.proxy.middleware.decorator.response.ResponseDecorator;
import org.sitmun.proxy.middleware.dto.DatasourcePayloadDto;
import org.sitmun.proxy.middleware.dto.OgcWmsPayloadDto;
import org.sitmun.proxy.middleware.dto.PayloadDto;
import org.sitmun.proxy.middleware.request.RemoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalRequestService  {

  @Autowired
  private List<RequestDecorator> requestDecorators;

  @Autowired
  private List<ResponseDecorator> responseDecorators;

  public ResponseEntity<?> executeRequest(PayloadDto payload) {
    RemoteRequest remoteRequest ;
    if (payload instanceof OgcWmsPayloadDto) {
      String url = ((OgcWmsPayloadDto) payload).getUri();
      remoteRequest = new OgcWmsRequest().setUrl(url);
    } else if (payload instanceof DatasourcePayloadDto) {
      remoteRequest = new JdbcDatabaseRequest();
    } else {
      throw new RuntimeException("Payload type not supported: "+payload.getClass().getName());
    }
    applyRequestDecorators(remoteRequest, payload);
    ResponseEntity<?> response = remoteRequest.execute();
    applyResponseDecorators(response);
    return response;
  }

  private void applyRequestDecorators(RemoteRequest globalRequest, PayloadDto payload) {
    requestDecorators.forEach(d -> {
      if (d.accept(payload)) {
        d.apply(globalRequest, payload);
      }
    });
  }

  private void applyResponseDecorators(ResponseEntity<?> response) {
    responseDecorators.forEach(d -> {
      if (d.accept(response)) {
        d.apply(response);
      }
    });
  }
}
