package xyz.zielinus.dcmchat.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import okhttp3.OkHttpClient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import xyz.zielinus.dcmchat.Dcmchat;
import xyz.zielinus.dcmchat.utils.PlayerUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;

public class PlayerChatEvent implements Listener {

    private final Dcmchat plugin;
    public PlayerChatEvent(Dcmchat dcmchat) {
        this.plugin = dcmchat;
    }

    @EventHandler
    public void onChat(@NotNull AsyncPlayerChatEvent event) {
        String webhookName = "dcmchat";
        TextChannel channel = plugin.getBot().getTextChannelsByName(plugin.getChatChannel(), true).get(0);
        Player player = event.getPlayer();

        String message = event.getMessage();

        List<Webhook> webhooks = plugin.getBot().getGuildById(channel.getGuild().getId()).retrieveWebhooks().complete();
        Webhook webhook = null;

        for (Webhook w : webhooks) {
            if (w.getName().equalsIgnoreCase(webhookName)) {
                webhook = w;
            }
        }

        if (webhook != null) {
            sendWebhookMessage(webhook, player.getName(), PlayerUtil.getAvatarByUsername(player.getName()), message);
        } else {
            channel.createWebhook(webhookName).queue(newWebhook -> {
                setWebhookAvatar(newWebhook, PlayerUtil.getAvatarByUsername("Zielino"));
                sendWebhookMessage(newWebhook, player.getName(), PlayerUtil.getAvatarByUsername(player.getName()), message);
            });
        }
    }

    private void sendWebhookMessage(@NotNull Webhook webhook, String username, String avatarUrl, String message) {
        WebhookClientBuilder builder = new WebhookClientBuilder(webhook.getUrl());
        builder.setHttpClient(new OkHttpClient.Builder().build());
        WebhookClient client = builder.build();

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        messageBuilder.setUsername(username);
        messageBuilder.setAvatarUrl(avatarUrl);
        messageBuilder.setContent(message);

        client.send(messageBuilder.build());
        client.close();
    }

    @SneakyThrows
    private void setWebhookAvatar(Webhook webhook, String avatarUrl ) {
        URL url = new URL(avatarUrl);
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        webhook.getManager().setAvatar(Icon.from(imageBytes)).queue();
    }
}
