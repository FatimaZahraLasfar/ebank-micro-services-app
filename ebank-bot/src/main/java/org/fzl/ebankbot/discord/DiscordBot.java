package org.fzl.ebankbot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.fzl.ebankbot.agents.EBankAIAgent;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class DiscordBot extends ListenerAdapter {

    private final JDA jda;
    private final EBankAIAgent agent;

    public DiscordBot(JDA jda, EBankAIAgent agent) {
        this.jda = jda;
        this.agent = agent;
    }

    @PostConstruct
    public void register() {
        jda.addEventListener(this);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();
        if (query == null || query.isBlank()) return;

        String response = agent.chat(query);
        event.getChannel().sendMessage(response).queue();
    }
}