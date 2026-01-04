package org.dream.mcpserver.model;

import lombok.Data;

@Data
public class JsonRpcRequest {
    private String method;
    private String jsonrpc;
    private Object params;
    private String id;
}
