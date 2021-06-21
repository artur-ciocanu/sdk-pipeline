package com.adobe.edge.sdk;

@FunctionalInterface
public interface Plugin<T, R> {

  R execute(T t, PipelineContext context);
}
