package tk.siurasowo.dcmchat.commands;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.siurasowo.dcmchat.Dcmchat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigTabCompleter implements TabCompleter {

    private final Dcmchat plugin;

    public ConfigTabCompleter(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    private final List<String> commands = new ArrayList<>(Arrays.asList(
            "enable-tag",
            "enable-console",
            "channel"
    ));

    private final List<String> booleans = new ArrayList<>(Arrays.asList(
            "true",
            "false"
    ));

    private final List<String> channels = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 2) return null;

        if (args.length == 1) return commands;

        // enable-tag subcommand
        if (args[0].equalsIgnoreCase(commands.get(0))) {
            return booleans;
        }

        // enable-console subcommand
        if (args[0].equalsIgnoreCase(commands.get(1))) {
            return booleans;
        }

        // channel subcommand
        if (args[0].equalsIgnoreCase(commands.get(2))) {
            for (Channel channel : plugin.getGuild().getChannels()) {
                if (channel.getType() == ChannelType.TEXT) {
                    channels.add("#" + channel.getName());
                }
            }

            return channels;
        }

        return null;
    }
}
