package xyz.zielinus.dcmchat.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import xyz.zielinus.dcmchat.Dcmchat;
import xyz.zielinus.dcmchat.utils.EmbedUtil;
import xyz.zielinus.dcmchat.utils.PlayerUtil;
import xyz.zielinus.dcmchat.utils.Colorize;

public class DeathEvent implements Listener {

    private final Dcmchat plugin;
    public DeathEvent(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    @EventHandler
    public void onDeath(@NotNull PlayerDeathEvent event) {
        TextChannel channel = plugin.getBot().getTextChannelsByName(plugin.getChatChannel(), true).get(0);

        String username = event.getPlayer().getName();
        String message = event.getDeathMessage();

        channel.sendMessageEmbeds(EmbedUtil.sendEmbedWithAuthor(username, PlayerUtil.getAvatarByUsername(username), message, Colorize.EmbedColors.DEATH).build()).queue();
    }

}
