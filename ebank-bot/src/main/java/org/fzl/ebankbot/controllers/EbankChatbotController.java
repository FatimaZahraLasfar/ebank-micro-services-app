package org.fzl.ebankbot.controllers;

import org.fzl.ebankbot.agents.EBankAIAgent;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class EbankChatbotController {

    private final EBankAIAgent eBankAIAgent;
    private ChatMemory chatMemory;
    public EbankChatbotController(EBankAIAgent eBankAIAgent, ChatMemory chatMemory) {
        this.eBankAIAgent =eBankAIAgent;
        this.chatMemory=chatMemory;
    }

    @GetMapping(value = "/chat" , produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(
            @RequestParam(name = "query", defaultValue = "Hello") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return eBankAIAgent.chat(query,conversationId);
    }
}