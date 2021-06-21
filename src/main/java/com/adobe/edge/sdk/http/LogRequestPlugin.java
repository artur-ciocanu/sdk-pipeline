package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.PipelineContext;
import com.adobe.edge.sdk.Plugin;
import okhttp3.Request;

public class LogRequestPlugin implements Plugin<Request, Request> {
  @Override
  public Request execute(Request request, PipelineContext context) {
    System.out.println("Just request: " + request);

    return request;
  }
}
