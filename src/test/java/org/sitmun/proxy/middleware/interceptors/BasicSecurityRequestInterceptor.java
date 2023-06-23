package org.sitmun.proxy.middleware.interceptors;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class BasicSecurityRequestInterceptor implements TestInterceptor {

  @NotNull
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    String authorization = request.header("Authorization");
    Response response = chain.proceed(request);
    if (authorization != null && authorization.startsWith("Basic")) {
      String token = new String(Base64.getDecoder().decode(authorization.substring(6)));
      ResponseBody body = ResponseBody.create(token, MediaType.get("application/json"));
      response = response.newBuilder().body(body).code(200).build();
    }
    return response;
  }

}
