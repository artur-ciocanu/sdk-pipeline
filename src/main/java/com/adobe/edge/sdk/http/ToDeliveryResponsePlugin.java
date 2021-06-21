package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.PipelineContext;
import com.adobe.edge.sdk.Plugin;
import com.adobe.target.delivery.v1.model.DeliveryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.Response;

public class ToDeliveryResponsePlugin implements Plugin<Response, DeliveryResponse> {

  private final ObjectMapper objectMapper;

  public ToDeliveryResponsePlugin(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public DeliveryResponse execute(Response response, PipelineContext context) {
    return getDeliveryResponse(response);
  }

  private DeliveryResponse getDeliveryResponse(Response response) {
    try {
      return objectMapper.readValue(response.body().string(), DeliveryResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
