package org.sitmun.proxy.middleware.request;

import org.springframework.http.ResponseEntity;

public interface RemoteRequest {
  ResponseEntity<?> execute();
}
