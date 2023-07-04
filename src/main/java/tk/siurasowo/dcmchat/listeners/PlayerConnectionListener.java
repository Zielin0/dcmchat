package tk.siurasowo.dcmchat.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import tk.siurasowo.dcmchat.Dcmchat;
import tk.siurasowo.dcmchat.utils.EmbedUtil;
import tk.siurasowo.dcmchat.utils.PlayerUtil;

import static tk.siurasowo.dcmchat.utils.Colorize.EmbedColors.JOIN;
import static tk.siurasowo.dcmchat.utils.Colorize.EmbedColors.LEAVE;

public class PlayerConnectionListener implements Listener {

    private final static String JOIN_MESSAGE = "%s joined the game";
    private final static String LEAVE_MESSAGE = "%s left the game";

    private final Dcmchat plugin;
    public PlayerConnectionListener(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        TextChannel channel = plugin.getBot().getTextChannelsByName(plugin.getChatChannel(), true).get(0);

        String username = event.getPlayer().getName();

        channel.sendMessageEmbeds(EmbedUtil.sendEmbedWithAuthor(String.format(JOIN_MESSAGE, username),
                PlayerUtil.getAvatarByUsername(username), JOIN).build()).queue();
    }

    @EventHandler
    public void onLeave(@NotNull PlayerQuitEvent event) {
        TextChannel channel = plugin.getBot().getTextChannelsByName(plugin.getChatChannel(), true).get(0);

        String username = event.getPlayer().getName();

        channel.sendMessageEmbeds(EmbedUtil.sendEmbedWithAuthor(String.format(LEAVE_MESSAGE, username),
                PlayerUtil.getAvatarByUsername(username), LEAVE).build()).queue();
    }

}
