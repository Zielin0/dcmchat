package xyz.zielinus.dcmchat.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import xyz.zielinus.dcmchat.Dcmchat;
import xyz.zielinus.dcmchat.utils.EmbedUtil;
import xyz.zielinus.dcmchat.utils.PlayerUtil;
import xyz.zielinus.dcmchat.utils.Colorize;

public class PlayerChatEvent implements Listener {

    private final Dcmchat plugin;
    public PlayerChatEvent(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    @EventHandler
    public void onChat(@NotNull AsyncPlayerChatEvent event) {
        TextChannel channel = plugin.getBot().getTextChannelsByName(plugin.getChatChannel(), true).get(0);

        Player player = event.getPlayer();
        String username = player.getName();

        String message = event.getMessage();

        channel.sendMessageEmbeds(EmbedUtil.sendEmbedWithAuthor(username, PlayerUtil.getAvatarByUsername(username), message, Colorize.EmbedColors.DEFAULT).build()).queue();
    }

}
