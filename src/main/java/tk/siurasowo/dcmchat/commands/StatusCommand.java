package tk.siurasowo.dcmchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.siurasowo.dcmchat.Dcmchat;
import tk.siurasowo.dcmchat.utils.Colorize;
import java.util.concurrent.atomic.AtomicInteger;

public class StatusCommand implements CommandExecutor {

    private final Dcmchat plugin;
    public StatusCommand(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {

            int memberCount;
            AtomicInteger usersCount = new AtomicInteger();
            AtomicInteger botCount = new AtomicInteger();

            Player player = (Player) sender;

            memberCount = plugin.getGuild().getMembers().size();
            plugin.getGuild().getMembers().forEach(member -> {
                if (!member.getUser().isBot()) usersCount.getAndIncrement();
                else botCount.getAndIncrement();
            });

            int userPercent = (usersCount.get() * 100) / memberCount;
            int botPercent = (botCount.get() * 100) / memberCount;

            String msg = Colorize.color("\n&8-===&r &3&l" + plugin.getGuild().getName() + " &r&5Stats &8 ===-&r\n");
            msg += Colorize.color("&6&lOwner: &r&e" + plugin.getGuild().getOwner().getUser().getAsTag() + "&r\n\n");
            msg += Colorize.color("&bMembers: &f&l" + memberCount + "&r\n");
            msg += Colorize.color("&bUsers: &f&l" + usersCount + " &7(" + userPercent + "%)" + "&r\n");
            msg += Colorize.color("&bBots: &f&l" + botCount + " &7(" + botPercent + "%)" + "&r\n");

            player.sendMessage(msg);
        }
        return true;
    }
}
