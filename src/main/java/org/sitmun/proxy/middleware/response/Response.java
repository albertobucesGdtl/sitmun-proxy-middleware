package org.sitmun.proxy.middleware.response;

import lombok.Data;
import org.sitmun.proxy.middleware.decorator.DecoratedResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;

@Data
public class Response<T> implements DecoratedResponse<T> {

  private final int statusCode;
  private final String contentType;
  private final T body;

  public Response(int statusCode, @Nullable String contentType, @Nullable T body) {
    this.statusCode = statusCode;
    this.contentType = contentType;
    this.body = body;
  }

  @Override
  public ResponseEntity<T> asResponseEntity() {
    ResponseEntity.BodyBuilder response = ResponseEntity.status(statusCode);
    if (contentType != null) {
      response = response.contentType(MediaType.parseMediaType(contentType));
    }
    if (body != null) {
      return response.body(body);
    }
    return response.build();
  }
}

