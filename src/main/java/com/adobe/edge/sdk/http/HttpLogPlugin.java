package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.PipelineContext;
import com.adobe.edge.sdk.Plugin;
import okhttp3.Request;
import okhttp3.Response;

public class HttpLogPlugin implements Plugin<Request, Response> {

  private final Plugin<Request, Response> plugin;

  private HttpLogPlugin(Plugin<Request, Response> plugin) {
    this.plugin = plugin;
  }

  public static HttpLogPlugin of(Plugin<Request, Response> plugin) {
    return new HttpLogPlugin(plugin);
  }

  @Override
  public Response execute(Request request, PipelineContext context) {
    System.out.println("Request: " + request);

    Response response = plugin.execute(request, context);

    System.out.println("Response: " + response);

    return response;
  }
}
