package com.adobe.edge.sdk.http;

import com.adobe.edge.sdk.core.PipelineContext;
import java.io.IOException;
import com.adobe.edge.sdk.core.Step;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseLoggingStep implements Step<Response, Response> {
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
