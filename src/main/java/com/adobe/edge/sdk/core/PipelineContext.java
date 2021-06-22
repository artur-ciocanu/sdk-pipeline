package com.adobe.edge.sdk.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PipelineContext {

  public static final PipelineContext NONE = new PipelineContext(null, null, null);

  private final PipelineContext parent;
  private final Object key;
  private final Object value;

  public PipelineContext(Object key, Object value) {
    this.parent = null;
    this.key = Objects.requireNonNull(key, "'key' cannot be null.");
    this.value = value;
  }

  private PipelineContext(PipelineContext parent, Object key, Object value) {
    this.parent = parent;
    this.key = key;
    this.value = value;
  }

  public PipelineContext addData(Object key, Object value) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    return new PipelineContext(this, key, value);
  }

  public static PipelineContext of(Map<Object, Object> keyValues) {
    if (keyValues == null || keyValues.isEmpty()) {
      throw new IllegalArgumentException("Key value map cannot be null or empty");
    }

    PipelineContext context = null;

    for (Map.Entry<Object, Object> entry : keyValues.entrySet()) {
      if (context == null) {
        context = new PipelineContext(entry.getKey(), entry.getValue());
      } else {
        context = context.addData(entry.getKey(), entry.getValue());
      }
    }
    return context;
  }

  public Optional<Object> getData(Object key) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }

    for (PipelineContext c = this; c != null; c = c.parent) {
      if (key.equals(c.key)) {
        return Optional.of(c.value);
      }
    }

    return Optional.empty();
  }

  public Map<Object, Object> getValues() {
    return getValuesHelper(new HashMap<>());
  }

  private Map<Object, Object> getValuesHelper(Map<Object, Object> values) {
    if (key != null) {
      values.putIfAbsent(key, value);
    }

    return (parent == null) ? values : parent.getValuesHelper(values);
  }

}
