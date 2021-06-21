package com.adobe.edge.sdk;

public class DefaultPipeline<I, O> implements Pipeline<I, O> {

  private final Plugin<I, O> starterPlugin;

  private DefaultPipeline(Plugin<I, O> plugin) {
    this.starterPlugin = plugin;
  }

  public static <I, O> Pipeline<I, O> from(Plugin<I, O> plugin) {
    return new DefaultPipeline<>(plugin);
  }

  public O execute(I input, PipelineContext context) {
    return starterPlugin.execute(input, context);
  }
}
