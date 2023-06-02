package org.sitmun.proxy.middleware.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.sitmun.proxy.middleware.request.RemoteRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public class OgcWmsRequest implements RemoteRequest {
  private Request.Builder customHttpRequest = new Request.Builder();
  private String baseUrl;

  public OgcWmsRequest setUrl(String url)  {
    this.baseUrl = url;
    customHttpRequest = customHttpRequest.url(url);
    return this;
  }

  @Override
  public ResponseEntity<?> execute() {
    OkHttpClient httpClient = new OkHttpClient();
    Request httpRequest = customHttpRequest.build();
    Response response;
    try {
      response = httpClient.newCall(httpRequest).execute();
      return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(response.header("content-type")))
        .body(response.body().bytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public OgcWmsRequest setHeader(String authorization, String concat) {
    customHttpRequest = customHttpRequest.header(authorization, concat);
    return this;
  }

  public OgcWmsRequest setParameters(Map<String, String> parameters) {
    StringBuilder uri = new StringBuilder(baseUrl.concat("?"));
    StringBuilder params = new StringBuilder();
    parameters.keySet().forEach(k -> params.append("&").append(k).append("=").append(parameters.get(k)));
    uri.append(params.substring(1));
    customHttpRequest = customHttpRequest.url(uri.toString());
    return this;
  }
}
