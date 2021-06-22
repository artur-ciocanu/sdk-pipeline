package com.adobe.edge.sdk.core;

public interface Pipeline<I, O> {

  O execute(I value, PipelineContext context);

  default <R> Pipeline<I, R> pipe(Pipeline<O, R> source) {
    return (value, context) -> source.execute(execute(value, context), context);
  }
}
