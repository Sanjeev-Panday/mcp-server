package org.dream.mcpserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonRpcResponse {
    private String jsonrpc = "2.0";
    private Object result;
    private String id;
}
