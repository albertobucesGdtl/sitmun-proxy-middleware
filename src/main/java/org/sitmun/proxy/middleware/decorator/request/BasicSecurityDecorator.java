package org.sitmun.proxy.middleware.decorator.request;

import org.sitmun.proxy.middleware.dto.OgcWmsPayloadDto;
import org.sitmun.proxy.middleware.dto.PayloadDto;
import org.sitmun.proxy.middleware.request.RemoteRequest;
import org.sitmun.proxy.middleware.service.OgcWmsRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;

@Component
public class BasicSecurityDecorator implements RequestDecorator<OgcWmsRequest, OgcWmsPayloadDto> {

  @Override
  public boolean accept(PayloadDto payload) {
    boolean result = false;
    if (payload instanceof OgcWmsPayloadDto) {
      OgcWmsPayloadDto ogcPayload = (OgcWmsPayloadDto) payload;
      result = ogcPayload.getSecurity() != null
        && StringUtils.hasText(ogcPayload.getSecurity().getUsername())
        && StringUtils.hasText(ogcPayload.getSecurity().getPassword());
    }
    return result;
  }

  @Override
  public void apply(OgcWmsRequest globalRequest, OgcWmsPayloadDto payload) {
    String authString = payload.getSecurity().getUsername().concat(":").concat(payload.getSecurity().getPassword());
    String authEncode = encodeAuthorization(authString);
    globalRequest.setHeader("Authorization", "Basic ".concat(authEncode));
  }

  private String encodeAuthorization(String authorization) {
    return Base64.getEncoder().encodeToString(authorization.getBytes());
  }
}
