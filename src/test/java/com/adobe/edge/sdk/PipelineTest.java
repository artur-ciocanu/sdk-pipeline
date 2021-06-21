package com.adobe.edge.sdk;

import com.adobe.edge.sdk.http.HttpLogPlugin;
import com.adobe.edge.sdk.http.HttpSendPlugin;
import com.adobe.edge.sdk.http.LogRequestPlugin;
import com.adobe.edge.sdk.http.LogResponsePlugin;
import com.adobe.edge.sdk.http.ToDeliveryResponsePlugin;
import com.adobe.edge.sdk.http.ToOkHttpRequestPlugin;
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
    TargetDeliveryRequest request = createRequest();
    HttpSendPlugin sendPlugin = new HttpSendPlugin();
    HttpLogPlugin logPlugin = HttpLogPlugin.of(sendPlugin);
    ObjectMapper mapper = new ObjectMapper();

    DeliveryResponse response =
        Pipeline.from(new ToOkHttpRequestPlugin(mapper))
            .add(new LogRequestPlugin())
            .add(logPlugin)
            .add(new LogResponsePlugin())
            .add(new ToDeliveryResponsePlugin(mapper))
            .execute(request, null);

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
