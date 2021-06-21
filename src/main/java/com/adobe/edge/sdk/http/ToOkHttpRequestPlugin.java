package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.PipelineContext;
import com.adobe.edge.sdk.Plugin;
import com.adobe.target.delivery.v1.model.DeliveryRequest;
import com.adobe.target.edge.client.model.TargetDeliveryRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ToOkHttpRequestPlugin implements Plugin<TargetDeliveryRequest, Request> {

  private static final String URL =
      "https://mboxedge34.tt.omtrdc.net/rest/v1/delivery?client=adobesummit2018&sessionId=aa-bb-cc-dd";

  private final ObjectMapper objectMapper;

  public ToOkHttpRequestPlugin(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Request execute(TargetDeliveryRequest targetDeliveryRequest, PipelineContext context) {
    DeliveryRequest deliveryRequest = targetDeliveryRequest.getDeliveryRequest();
    MediaType mediaType = MediaType.parse("application/json");
    byte[] bytes = getBytes(deliveryRequest);
    RequestBody body = RequestBody.create(mediaType, bytes);

    return new Request.Builder().url(URL).post(body).build();
  }

  private byte[] getBytes(DeliveryRequest deliveryRequest) {
    try {
      return objectMapper.writeValueAsBytes(deliveryRequest);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
