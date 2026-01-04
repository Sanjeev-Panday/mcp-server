package org.dream.mcpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dream.mcpserver.internal.model.ToolDefinition;
import org.dream.mcpserver.internal.registry.McpToolRegistry;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class McpDispatcher {

    private final McpToolRegistry registry;
    private final ObjectMapper mapper;
    public McpDispatcher(McpToolRegistry registry, ObjectMapper mapper) {
        this.registry = registry;
        this.mapper = mapper;
    }

    public Object dispatch(String method, Object params) {
        return switch (method) {
            case "initialize" -> initialize();
            case "tools/list" -> listTools();
            case "tools/call" -> callTool((Map<String,Object>) params);
            default -> throw new IllegalArgumentException("Unknown method: "+method);
        };
    }

    private Map<String, Object> initialize() {
        return Map.of("capabilities",Map.of(
                "tools",true,
                "resources",false
        ));
    }

    private Object listTools(){
        return registry.list().stream()
                .map(t -> Map.of(
                    "name",t.getName(),
                    "description",t.getDescription()
                )).toList();
    }

    private Object callTool(Map<String,Object> params){
        String name = (String) params.get("name");
        Object arguments = params.get("arguments");
        ToolDefinition toolDefinition = registry.get(name);
        if(toolDefinition == null) {
            throw new IllegalArgumentException("Unknown tool: "+name);
        }
        Method method = toolDefinition.getMethod();
        Class<?> parameterType = method.getParameterTypes()[0];
        Object argObject = mapper.convertValue(arguments,parameterType);

        try{
            return method.invoke(toolDefinition.getBean(),argObject);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
