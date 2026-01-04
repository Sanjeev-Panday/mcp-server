package org.dream.mcpserver.internal.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Getter
public class ToolDefinition {
    private final String name;
    private final String description;
    private final Object bean;
    private final Method method;
}
