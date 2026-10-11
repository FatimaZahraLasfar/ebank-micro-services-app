package org.fzl.ebankbot.controllers;

import org.fzl.ebankbot.agents.EBankAIAgent;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotController {

    private final EBankAIAgent eBankAIAgent;

    public EbankChatbotController(EBankAIAgent eBankAIAgent, ChatMemory chatMemory) {
        this.eBankAIAgent =eBankAIAgent;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "query", defaultValue = "Hello") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return eBankAIAgent.chat(query,conversationId);
    }
}