package tk.siurasowo.dcmchat.commands;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Channel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.siurasowo.dcmchat.Dcmchat;
import tk.siurasowo.dcmchat.utils.Colorize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigCommand implements CommandExecutor {

    private final Dcmchat plugin;

    public ConfigCommand(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    private final List<String> commands = new ArrayList<>(Arrays.asList(
            "enable-tag",
            "enable-console",
            "channel"
    ));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
        }

        Player player = (Player) sender;

        if (player.isOp()) {
            if (args.length == 0 || args.length > 2 || !(commands.contains(args[0]))) {
                help(player);
                return true;
            }

            // enable-tag subcommand
            if (args[0].equalsIgnoreCase(commands.get(0))) {
                if (args[1].equals("true")) {
                    plugin.getConfig().set("enable-tag", true);
                    save();
                    confirm(player, "enable-tag", args[1]);
                }
                if (args[1].equals("false")) {
                    plugin.getConfig().set("enable-tag", false);
                    save();
                    confirm(player, "enable-tag", args[1]);
                }
            }

            // enable-console subcommand
            if (args[0].equalsIgnoreCase(commands.get(1))) {
                if (args[1].equals("true")) {
                    plugin.getConfig().set("enable-console", true);
                    save();
                    confirm(player, "enable-console", args[1]);
                }
                if (args[1].equals("false")) {
                    plugin.getConfig().set("enable-console", false);
                    save();
                    confirm(player, "enable-console", args[1]);
                }
            }

            // channel subcommand
            if (args[0].equalsIgnoreCase(commands.get(2))) {
                boolean exists = false;
                String channelName = args[1].replace("#", "");
                for (Channel channel : plugin.getGuild().getChannels()) {
                    if (channel.getName().equalsIgnoreCase(channelName)) {
                        exists = true;
                    }
                }

                if (exists) {
                    plugin.getConfig().set("channel-name", channelName);
                    save();
                    confirm(player, "channel-name", args[1]);
                } else {
                    player.sendMessage(Colorize.color("&4Invalid channel!"));
                }
            }
        } else {
            player.sendMessage(Colorize.color("&4You do not have permissions to use this command."));
        }
        return true;
    }

    private void help(Player player) {
        player.sendMessage(Colorize.color("&bConfig command usage\n"));
        player.sendMessage(Colorize.color("&a/config &5[enable-tag | enable-console | channel] &3<value>\n"));
        player.sendMessage(Colorize.color("&7For the enable-tag & enable-console value can be &2true &7or &4false\n"));
        player.sendMessage(Colorize.color("&7For the channel value can only be a valid &9discord channel\n"));
    }

    private void confirm(Player player, String option, String newValue) {
        player.sendMessage(Colorize.color("&7The &a" + option + " &7has been set to &b" + newValue + "\n"));
    }

    @SneakyThrows
    private void save() {
        plugin.getConfig().save(plugin.getDataFolder() + "/config.yml");
    }

}
