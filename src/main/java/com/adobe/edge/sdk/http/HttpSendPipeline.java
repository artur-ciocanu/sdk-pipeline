package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.core.Pipeline;
import com.adobe.edge.sdk.core.PipelineContext;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpSendPipeline implements Pipeline<Request, Response> {

  private final OkHttpClient httpClient;

  public HttpSendPipeline() {
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
