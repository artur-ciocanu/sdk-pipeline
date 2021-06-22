package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.core.PipelineContext;
import com.adobe.edge.sdk.core.Step;
import okhttp3.Request;
import okhttp3.Response;

public class HttpLoggingStep implements Step<Request, Response> {

  private final Step<Request, Response> plugin;

  private HttpLoggingStep(Step<Request, Response> plugin) {
    this.plugin = plugin;
  }

  public static HttpLoggingStep of(Step<Request, Response> plugin) {
    return new HttpLoggingStep(plugin);
  }

  @Override
  public Response execute(Request request, PipelineContext context) {
    System.out.println("Request: " + request);

    Response response = plugin.execute(request, context);

    System.out.println("Response: " + response);

    return response;
  }

}
