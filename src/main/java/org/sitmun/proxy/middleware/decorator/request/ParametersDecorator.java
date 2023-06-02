package org.sitmun.proxy.middleware.decorator.request;

import org.sitmun.proxy.middleware.dto.OgcWmsPayloadDto;
import org.sitmun.proxy.middleware.dto.PayloadDto;
import org.sitmun.proxy.middleware.service.OgcWmsRequest;
import org.springframework.stereotype.Component;

@Component
public class ParametersDecorator implements RequestDecorator<OgcWmsRequest, OgcWmsPayloadDto> {

  @Override
  public boolean accept(PayloadDto payload) {
    boolean result = false;
    if (payload instanceof OgcWmsPayloadDto) {
      OgcWmsPayloadDto ogcPayload = (OgcWmsPayloadDto) payload;
      result = ogcPayload.getParameters() != null && !ogcPayload.getParameters().isEmpty();
    }
    return result;
  }

  @Override
  public void apply(OgcWmsRequest globalRequest, OgcWmsPayloadDto payload) {
    globalRequest.setParameters(payload.getParameters());
  }

}
