package org.dream.mcpserver.tools;

import org.dream.mcpserver.annotations.McpTool;
import org.dream.mcpserver.annotations.McpToolComponent;
import org.dream.mcpserver.request.AddRequest;
import org.springframework.stereotype.Component;

@Component
@McpToolComponent
public class MathTools {

    @McpTool(name = "math.add", description = "Add two numbers")
    public int add(AddRequest request) {
        return request.a() + request.b();
    }
}
