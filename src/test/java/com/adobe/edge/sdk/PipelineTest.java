package com.adobe.edge.sdk;

import com.adobe.edge.sdk.http.DeliveryResponsePipeline;
import com.adobe.edge.sdk.http.HttpLoggingPipeline;
import com.adobe.edge.sdk.http.HttpSendPipeline;
import com.adobe.edge.sdk.http.OkHttpRequestPipeline;
import com.adobe.edge.sdk.http.RequestLoggingPipeline;
import com.adobe.edge.sdk.http.ResponseLoggingPipeline;
import com.adobe.edge.sdk.core.Pipeline;
import com.adobe.target.delivery.v1.model.ChannelType;
import com.adobe.target.delivery.v1.model.Context;
import com.adobe.target.delivery.v1.model.DeliveryResponse;
import com.adobe.target.delivery.v1.model.ExecuteRequest;
import com.adobe.target.delivery.v1.model.MboxRequest;
import com.adobe.target.delivery.v1.model.Trace;
import com.adobe.target.delivery.v1.model.VisitorId;
import com.adobe.target.edge.client.model.TargetDeliveryRequest;
import com.adobe.target.edge.client.service.VisitorProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PipelineTest {

  @Test
  public void testPipeline() {
    ObjectMapper mapper = new ObjectMapper();
    TargetDeliveryRequest request = createRequest();
    RequestLoggingPipeline requestLoggingPipeline = new RequestLoggingPipeline();
    OkHttpRequestPipeline okHttpRequestPipeline = new OkHttpRequestPipeline(mapper);
    HttpSendPipeline httpSendPipeline = new HttpSendPipeline();
    HttpLoggingPipeline httpLoggingPipeline = HttpLoggingPipeline.of(httpSendPipeline);
    ResponseLoggingPipeline responseLoggingPipeline = new ResponseLoggingPipeline();
    DeliveryResponsePipeline responsePipeline = new DeliveryResponsePipeline(mapper);
    Pipeline<TargetDeliveryRequest, DeliveryResponse> pipeline =
        okHttpRequestPipeline
            .pipe(requestLoggingPipeline)
            .pipe(httpLoggingPipeline)
            .pipe(responseLoggingPipeline)
            .pipe(responsePipeline);

    DeliveryResponse response = pipeline.execute(request, null);

    Assertions.assertEquals(response.getStatus(), 200);
    Assertions.assertNotNull(response.getExecute());
    Assertions.assertNotNull(response.getExecute().getMboxes());
    Assertions.assertEquals(response.getExecute().getMboxes().size(), 1);
  }

  private static TargetDeliveryRequest createRequest() {
    VisitorProvider.init("EA673DFC5A2F19060A495C9C@AdobeOrg");
    MboxRequest mboxRequest = new MboxRequest();
    mboxRequest.setIndex(0);
    mboxRequest.setName("ondevice-featureflag");
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.addMboxesItem(mboxRequest);
    Trace trace = new Trace();
    trace.authorizationToken("1234");

    VisitorId id =
        new VisitorId().tntId("jora-poltergheist.34_0").marketingCloudVisitorId("11-22-33-44");

    return TargetDeliveryRequest.builder()
        .id(id)
        .context(new Context().channel(ChannelType.WEB))
        .execute(executeRequest)
        .build();
  }
}
