package org.fzl.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.fzl.ebankbot.agents.EBankAIAgent;
import org.springframework.ai.chat.prompt.Prompt;

@DiscordController
public class DiscordBot {
    private EBankAIAgent eBankAIAgent;

    public DiscordBot(EBankAIAgent eBankAIAgent){
       this.eBankAIAgent=eBankAIAgent;}

      @DiscordMapping
      private void perform(MessageReceivedEvent event){
        System.out.println(event);
        if(event.getAuthor().isBot()) return;
        String query = event.getMessage().getContentRaw();
        String response = eBankAIAgent.chat(query);
        event.getChannel().sendMessage(response).queue();
    }
}