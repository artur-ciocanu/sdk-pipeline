package com.adobe.edge.sdk.core;

public interface Pipeline<I, O> extends Step<I, O> {

  default <R> Pipeline<I, R> pipe(Step<O, R> step) {
    return (value, context) -> step.execute(execute(value, context), context);
  }

  static <I, O> Pipeline<I, O> of(Step<I, O> step) {
    return new DefaultPipeline<>(step);
  }

  class DefaultPipeline<I, O> implements Pipeline<I, O> {

    private final Step<I, O> step;

    private DefaultPipeline(Step<I, O> step) {
      this.step = step;
    }

    @Override
    public O execute(I value, PipelineContext context) {
      return step.execute(value, context);
    }
  }

}
