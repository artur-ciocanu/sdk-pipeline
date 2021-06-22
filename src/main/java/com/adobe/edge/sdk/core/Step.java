package com.adobe.edge.sdk.core;

public interface Step<I, O> {

  O execute(I value, PipelineContext context);

}
