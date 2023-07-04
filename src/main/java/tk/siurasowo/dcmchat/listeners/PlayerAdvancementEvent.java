package tk.siurasowo.dcmchat.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.NotNull;
import tk.siurasowo.dcmchat.Dcmchat;
import tk.siurasowo.dcmchat.utils.AdvancementUtil;
import tk.siurasowo.dcmchat.utils.Colorize;
import tk.siurasowo.dcmchat.utils.EmbedUtil;
import tk.siurasowo.dcmchat.utils.PlayerUtil;

public class PlayerAdvancementEvent implements Listener {

    private final Dcmchat plugin;
    public PlayerAdvancementEvent(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    @EventHandler
    public void onAdvancement(@NotNull PlayerAdvancementDoneEvent event) {
        TextChannel channel = plugin.getBot().getTextChannelsByName(plugin.getChatChannel(), true).get(0);

        String username = event.getPlayer().getName();

        Advancement advancement = event.getAdvancement();

        String advancementKey = advancement.getKey().getKey();

        if (!advancementKey.contains("root")) {
            if (!advancementKey.toLowerCase().startsWith("recipes")) {
                String fixedAdvancementKey = advancementKey.replace('/', '.');

                advancementKey = "advancements." + fixedAdvancementKey + ".title";

                if (advancement.getDisplay().frame() != null) {
                    String msg = "";
                    Colorize.EmbedColors color = null;
                    switch (advancement.getDisplay().frame()) {
                        case TASK:
                            msg = String.format("%s has made the advancement **%s**", username, AdvancementUtil.getValue(plugin.getAdvancementsPath(), advancementKey));
                            color = Colorize.EmbedColors.ADVANCEMENT;
                            break;
                        case CHALLENGE:
                            msg = String.format("%s has completed the challenge **%s**", username, AdvancementUtil.getValue(plugin.getAdvancementsPath(), advancementKey));
                            color = Colorize.EmbedColors.CHALLENGE;
                            break;
                        case GOAL:
                            msg = String.format("%s has reached the goal **%s**", username, AdvancementUtil.getValue(plugin.getAdvancementsPath(), advancementKey));
                            color = Colorize.EmbedColors.ADVANCEMENT;
                            break;
                    }

                    channel.sendMessageEmbeds(EmbedUtil.sendEmbedWithAuthor(username, PlayerUtil.getAvatarByUsername(username), msg, color).build()).queue();
                }
            }
        }

    }

}
