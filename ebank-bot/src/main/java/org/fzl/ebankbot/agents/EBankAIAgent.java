package org.fzl.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EBankAIAgent {

    private final ChatClient chatClient;

    public EBankAIAgent(ChatClient.Builder builder, ToolCallbackProvider tools) {
        this.chatClient = builder
                .defaultSystem("""
                    Tu es un assistant bancaire connecté à une base de données
                    clients via des outils MCP :
                      - getAllCustomers : récupère la liste complète des clients
                      - findCustomerById : cherche un client par son identifiant
                      - saveCustomer : enregistre un nouveau client

                    RÈGLES :
                    1. Dès que l'utilisateur parle des clients, appelle l'outil.
                    2. Ne réponds jamais depuis tes connaissances générales.
                    3. Ne dis jamais "je n'ai pas accès" — tu as les outils.
                    4. Restitue le résultat en français, de façon lisible.
                    """)
                .defaultToolCallbacks(tools)
                .build();
    }

    public String chat(String query) {
        return chatClient.prompt(query)
                .call()
                .content();
    }
}