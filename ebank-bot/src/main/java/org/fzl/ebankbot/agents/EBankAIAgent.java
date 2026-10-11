package org.fzl.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EBankAIAgent {
    private final ChatClient chatClient;

    public EBankAIAgent(ChatClient.Builder builder,
                        ChatMemory chatMemory,
                        ToolCallbackProvider tools) {
        this.chatClient = builder
                .defaultSystem("""
                Tu es un assistant bancaire connecté à une base de données clients
                via des outils MCP :
                  - getAllCustomers : récupère la liste complète des clients
                  - findCustomerById : cherche un client par son identifiant
                  - saveCustomer : enregistre un nouveau client

                RÈGLES :
                1. Quand l'utilisateur demande des informations sur les clients
                   (liste, recherche, ajout), tu DOIS appeler l'outil correspondant.
                2. Ne réponds JAMAIS depuis tes connaissances générales.
                3. Ne dis JAMAIS "je n'ai pas accès aux données" — tu as justement
                   des outils pour y accéder.
                4. Si l'utilisateur demande la liste des clients, appelle
                   getAllCustomers immédiatement.
                """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)
                .build();
    }

    public String chat(String query) {

        return chatClient.prompt(query)
                .call()
                .content();
    }
}
