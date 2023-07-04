package tk.siurasowo.dcmchat.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import tk.siurasowo.dcmchat.Dcmchat;
import tk.siurasowo.dcmchat.utils.EmbedUtil;
import tk.siurasowo.dcmchat.utils.PlayerUtil;

import static tk.siurasowo.dcmchat.utils.Colorize.EmbedColors.DEATH;

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

        channel.sendMessageEmbeds(EmbedUtil.sendEmbedWithAuthor(username, PlayerUtil.getAvatarByUsername(username), message, DEATH).build()).queue();
    }

}
