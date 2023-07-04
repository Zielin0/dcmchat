package xyz.zielinus.dcmchat;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.zielinus.dcmchat.commands.ConfigCommand;
import xyz.zielinus.dcmchat.commands.ConfigTabCompleter;
import xyz.zielinus.dcmchat.commands.StatusCommand;
import xyz.zielinus.dcmchat.utils.Colorize;
import xyz.zielinus.dcmchat.listeners.*;

import java.util.*;
import java.util.logging.Logger;

import static xyz.zielinus.dcmchat.utils.Colorize.Colors.RED;
import static xyz.zielinus.dcmchat.utils.Colorize.Colors.GREEN;
import static xyz.zielinus.dcmchat.utils.Colorize.Colors.RESET;

public final class Dcmchat extends JavaPlugin {
    public Logger logger = this.getLogger();
    public final String PLUGIN_NAME = this.getDescription().getName();

    @Getter
    private String advancementsPath;

    @Getter
    private JDA bot;

    Collection<GatewayIntent> intents = new ArrayList<>(Arrays.asList(
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS
    ));

    @Getter @Setter
    Guild guild;

    @SneakyThrows
    @Override
    public void onEnable() {
        logger.info(Colorize.colorConsole(PLUGIN_NAME + " enabled!", GREEN) + RESET.color);

        this.saveDefaultConfig();
        saveResource("advancements.json", true);

        String token = getConfig().getString("BOT_TOKEN");
        if (token == null) {
            logger.info(Colorize.colorConsole("Please provide a BOT_TOKEN in the config.yml file.", RED) + RESET.color);
            getServer().getPluginManager().disablePlugin(this);
        }

        bot = JDABuilder.createDefault(token)
                .enableIntents(intents)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        bot.addEventListener(new DiscordEventListener(this));

        bot.getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, "Chat..."));

        advancementsPath = getDataFolder() + "/advancements.json";

        listenerRegister();
        commandRegister();
    }

    @Override
    public void onDisable() {
        logger.info(Colorize.colorConsole(PLUGIN_NAME + " disabled!", RED) + RESET.color);

        if (this.bot != null)
            this.bot.shutdown();
    }

    private void listenerRegister() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerChatEvent(this), this);
        pm.registerEvents(new DeathEvent(this), this);
        pm.registerEvents(new PlayerAdvancementEvent(this), this);
        pm.registerEvents(new PlayerConnectionListener(this), this);
    }

    private void commandRegister() {
        getCommand("status").setExecutor(new StatusCommand(this));

        getCommand("config").setExecutor(new ConfigCommand(this));
        getCommand("config").setTabCompleter(new ConfigTabCompleter(this));
    }

    public String getDiscordServerName() {
        return getConfigOptionOrDefault("server-name", "name");
    }

    public String getChatChannel() {
        return getConfigOptionOrDefault("channel-name", "mc-chat");
    }

    public boolean getIsTagEnabled() { return getConfigOptionOrDefault("enable-tag", false); }

    public boolean getIsConsoleEnabled() { return getConfigOptionOrDefault("enable-console", false); }

    private String getConfigOptionOrDefault(String key, String defaultValue) {
        String value = getConfig().getString(key);
        if (value == null)
            return defaultValue;
        else
            return value;
    }

    private boolean getConfigOptionOrDefault(String key, boolean defaultValue) {
        boolean value = getConfig().getBoolean(key);
        if (!value)
            return defaultValue;
        else
            return value;
    }
}
