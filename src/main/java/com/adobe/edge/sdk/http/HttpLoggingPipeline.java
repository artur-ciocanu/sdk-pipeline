package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.core.Pipeline;
import com.adobe.edge.sdk.core.PipelineContext;
import okhttp3.Request;
import okhttp3.Response;

public class HttpLoggingPipeline implements Pipeline<Request, Response> {

  private final Pipeline<Request, Response> plugin;

  private HttpLoggingPipeline(Pipeline<Request, Response> plugin) {
    this.plugin = plugin;
  }

  public static HttpLoggingPipeline of(Pipeline<Request, Response> plugin) {
    return new HttpLoggingPipeline(plugin);
  }

  @Override
  public Response execute(Request request, PipelineContext context) {
    System.out.println("Request: " + request);

    Response response = plugin.execute(request, context);

    System.out.println("Response: " + response);

    return response;
  }
}
