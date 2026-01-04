package org.dream.mcpserver.controller;

import org.dream.mcpserver.model.JsonRpcRequest;
import org.dream.mcpserver.model.JsonRpcResponse;
import org.dream.mcpserver.service.McpDispatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mcp")
public class McpController {
    private final McpDispatcher dispatcher;
    public McpController(McpDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @PostMapping
    public JsonRpcResponse handle(@RequestBody JsonRpcRequest request) {
        Object result = dispatcher.dispatch(
                request.getMethod(),
                request.getParams()
        );
        return new JsonRpcResponse("2.0",result,request.getId());
    }
}
