package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.core.PipelineContext;
import com.adobe.edge.sdk.core.Step;
import okhttp3.Request;

public class RequestLoggingStep implements Step<Request, Request> {
  @Override
  public Request execute(Request request, PipelineContext context) {
    System.out.println("Just request: " + request);

    return request;
  }
}
