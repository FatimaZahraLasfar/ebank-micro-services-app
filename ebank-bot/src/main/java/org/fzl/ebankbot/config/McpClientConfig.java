package org.fzl.ebankbot.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class McpClientConfig {

    @Bean
    @Primary   // ← priorité sur le bean auto-configuré
    public ToolCallbackProvider toolCallbackProvider(List<McpSyncClient> mcpClients) {
        System.out.println(">>> MCP clients connected: " + mcpClients.size());
        var provider = new SyncMcpToolCallbackProvider(mcpClients);
        System.out.println(">>> Tools found: " + provider.getToolCallbacks().length);
        return provider;
    }
}