package org.sitmun.proxy.middleware.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfigProxyDto {

  private String type;

  private long exp;

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes({@JsonSubTypes.Type(value = OgcWmsPayloadDto.class), @JsonSubTypes.Type(value = DatasourcePayloadDto.class)})
  private PayloadDto payload;

}
