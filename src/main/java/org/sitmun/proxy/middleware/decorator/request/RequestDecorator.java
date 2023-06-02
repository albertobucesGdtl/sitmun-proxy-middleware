package org.sitmun.proxy.middleware.decorator.request;

import org.sitmun.proxy.middleware.dto.PayloadDto;
import org.sitmun.proxy.middleware.request.RemoteRequest;

public interface RequestDecorator<T extends RemoteRequest, S extends PayloadDto> {

  boolean accept(PayloadDto payload);

  void apply(T globalRequest, S payload);
}
