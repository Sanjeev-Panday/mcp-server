package org.dream.mcpserver.internal.registry;

import lombok.RequiredArgsConstructor;
import org.dream.mcpserver.annotations.McpTool;
import org.dream.mcpserver.annotations.McpToolComponent;
import org.dream.mcpserver.internal.model.ToolDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.lang.reflect.Method;


@Component
public class McpToolRegistry implements ApplicationContextAware {
    private ApplicationContext context;
    private final Map<String, ToolDefinition> tools = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext context) {
        Map<String, Object> beans = context.getBeansWithAnnotation(McpToolComponent.class);
        for(Object bean : beans.values()) {
            Method[] methods = bean.getClass().getMethods();
            for(Method method : methods) {
                if(method.isAnnotationPresent(McpTool.class)) {
                    McpTool annotation = method.getAnnotation(McpTool.class);
                    String name = annotation.name();
                    String description = annotation.description();
                    ToolDefinition toolDefinition = new ToolDefinition(name, description, bean, method);
                    tools.put(name, toolDefinition);
                }
            }
        }
    }
    public Collection<ToolDefinition> list() {
        return tools.values();
    }
    public ToolDefinition get(String name) {
        return tools.get(name);
    }
}
