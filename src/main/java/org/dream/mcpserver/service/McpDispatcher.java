package org.dream.mcpserver.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class McpDispatcher {
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

    private List<Map<String,Object>> listTools(){
        return List.of(
                    Map.of(
                        "name","math.add",
                        "description","Add two numbers",
                        "inputSchema",Map.of(
                                "type","object",
                                "properties", Map.of(
                                        "a",Map.of("type","number"),
                                        "b",Map.of("type","number")
                                ),
                                "required",List.of("a","b")
                        )
                    )
        );
    }

    private Object callTool(Map<String,Object> params){
        String name = (String) params.get("name");
        Map<String,Object> args = (Map<String, Object>) params.get("arguments");

        if("math.add".equals(name)) {
            return (int) args.get("a") + (int) args.get("b");
        }
        throw new IllegalArgumentException("Unknown tool: "+name);
    }
}
