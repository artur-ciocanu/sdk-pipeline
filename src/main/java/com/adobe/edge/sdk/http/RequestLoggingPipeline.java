package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.core.Pipeline;
import com.adobe.edge.sdk.core.PipelineContext;
import okhttp3.Request;

public class RequestLoggingPipeline implements Pipeline<Request, Request> {
  @Override
  public Request execute(Request request, PipelineContext context) {
    System.out.println("Just request: " + request);

    return request;
  }
}
