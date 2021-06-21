package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.PipelineContext;
import com.adobe.edge.sdk.Plugin;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LogResponsePlugin implements Plugin<Response, Response> {
  @Override
  public Response execute(Response response, PipelineContext context) {
    System.out.println("Just response: " + response);
    System.out.println("Just response body: " + getBody(response));

    return response;
  }

  private String getBody(Response response) {
    try {
      ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
      return responseBody.string();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
