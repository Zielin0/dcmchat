package xyz.zielinus.dcmchat.utils;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedUtil {

    public static EmbedBuilder sendEmbedWithAuthor(String title, String imageUrl, String description, Colorize.EmbedColors color) {
        return new EmbedBuilder()
                .setAuthor(title, null, imageUrl)
                .setDescription(description)
                .setColor(color.color);
    }

    public static EmbedBuilder sendEmbedWithAuthor(String title, String imageUrl, Colorize.EmbedColors color) {
        return new EmbedBuilder()
                .setAuthor(title, null, imageUrl)
                .setColor(color.color);
    }

}
