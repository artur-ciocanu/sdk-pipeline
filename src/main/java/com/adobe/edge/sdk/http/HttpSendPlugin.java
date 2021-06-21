package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.PipelineContext;
import com.adobe.edge.sdk.Plugin;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpSendPlugin implements Plugin<Request, Response> {

  private final OkHttpClient httpClient;

  public HttpSendPlugin() {
    this.httpClient = new OkHttpClient();
  }

  @Override
  public Response execute(Request input, PipelineContext context) {
    Call call = httpClient.newCall(input);

    try {
      return call.execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
