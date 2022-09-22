package tk.siurasowo.dcmchat.listeners;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.siurasowo.dcmchat.Dcmchat;
import tk.siurasowo.dcmchat.utils.ChatUtils;
import tk.siurasowo.dcmchat.utils.Colorize;
import tk.siurasowo.dcmchat.utils.Hex;

import java.awt.*;
import java.util.List;

public class DiscordEventListener extends ListenerAdapter {

    private final Dcmchat plugin;
    public DiscordEventListener(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    private static final String RESET = Colorize.color("&r");

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<Guild> guilds = plugin.getBot().getGuildsByName(plugin.getDiscordServerName(), true);

        if (guilds.isEmpty()) {
            plugin.logger.info(Colorize.colorConsole("The bot hasn't been added to your server. " +
                    "The bot will only work on the server you've setup in the config.yml file.", Colorize.Colors.RED));
        } else {
            plugin.setGuild(guilds.get(0));
        }
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        if (!event.getGuild().getName().equalsIgnoreCase(plugin.getDiscordServerName())) {
            event.getGuild().leave().complete();
        } else {
            plugin.setGuild(event.getGuild());
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        if (event.getGuild().getName().equalsIgnoreCase(plugin.getDiscordServerName())) plugin.setGuild(null);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        if (channel.getName().equalsIgnoreCase(plugin.getChatChannel()) && !event.getAuthor().isBot()) {
            String message = event.getMessage().getContentRaw();
            boolean validMessage = message.length() <= 128;

            if (!validMessage) {
                channel.addReactionById(event.getMessageId(), Emoji.fromUnicode("U+2757")).queue();
            } else {
                int colorRed;
                int colorGreen;
                int colorBlue;
                if (event.getGuild().getMember(event.getAuthor()).getRoles().size() == 0) {
                    colorRed = 153;
                    colorGreen = 170;
                    colorBlue = 181;
                } else {
                    Color roleColor = event.getGuild().getMember(event.getAuthor()).getRoles().get(0).getColor();
                    if (roleColor == null) {
                        colorRed = 153;
                        colorGreen = 170;
                        colorBlue = 181;
                    } else {
                        colorRed = roleColor.getRed();
                        colorGreen = roleColor.getGreen();
                        colorBlue = roleColor.getBlue();
                    }
                }

                List<String> emojis = EmojiParser.extractEmojis(message);

                for (String e : emojis) {
                    String alias = EmojiParser.parseToAliases(e);
                    message = message.replace(e, alias);
                }

                String id;

                for (String s : message.split(" ")) {
                    if (s.matches("^<@!?(\\d+)>$")) {
                        id = s.replace("<", "").replace("@", "").replace(">", "");
                        String nickname = plugin.getGuild().getMemberById(id).getNickname();
                        String name = plugin.getGuild().getMemberById(id).getUser().getName();
                        message = message.replace("<@" + id + ">",
                                Hex.format(Colorize.MENTION_HEX) + "@"
                                + (nickname == null ? name : nickname)
                                + RESET);
                    }

                    if (s.equals("@here")) {
                        message = message.replace("@here", Hex.format(Colorize.MENTION_HEX) + "@here" + RESET);
                    }
                    if (s.equals("@everyone")) {
                        message = message.replace("@everyone", Hex.format(Colorize.MENTION_HEX) + "@everyone" + RESET);
                    }

                    if (s.matches(Hex.LINK_PATTERN)) {
                        message = message.replace(s, Hex.format(Colorize.LINK_HEX) + s + RESET);
                    }
                }

                String isWithTag = (plugin.getIsTagEnabled() ? event.getAuthor().getAsTag() :
                        (plugin.getGuild().getMemberById(event.getAuthor().getId()).getNickname() == null ? event.getAuthor().getName() :
                                plugin.getGuild().getMemberById(event.getAuthor().getId()).getNickname()));

                String colorHex = String.format("#%02x%02x%02x", colorRed, colorGreen, colorBlue);
                String msg = ChatUtils.msgDiscordPrefix(Hex.format(colorHex) + isWithTag, message);

                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    p.sendMessage(msg);

                    if (plugin.getIsConsoleEnabled()) {
                        plugin.logger.info(Colorize.colorConsole("[Discord] ", Colorize.Colors.BLUE)
                                + Colorize.colorConsole(isWithTag, Colorize.Colors.CYAN)
                                + Colorize.colorConsole(" " + message, Colorize.Colors.RESET)
                        );
                    }
                }
            }
        }

    }
}
