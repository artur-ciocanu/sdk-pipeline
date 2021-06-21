package com.adobe.edge.sdk;

public interface Pipeline<I, O> extends Plugin<I, O> {

  static <I, O> Pipeline<I, O> from(Plugin<I, O> plugin) {
    return DefaultPipeline.from(plugin);
  }

  default <K> Pipeline<I, K> add(Plugin<? super O, ? extends K> plugin) {
    return Pipeline.from((input, context) -> plugin.execute(execute(input, context), context));
  }
}
