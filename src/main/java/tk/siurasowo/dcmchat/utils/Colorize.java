package tk.siurasowo.dcmchat.utils;

import org.bukkit.ChatColor;

public class Colorize {

    public static final String BLURPLE_HEX = "#5865F2";
    public static final String MENTION_HEX = "#787fc9";
    public static final String LINK_HEX = "#6495ed";

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String colorConsole(String s, Colors c) {
        return c.color + s;
    }

    public enum Colors {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\\u001B[33m"),
        BLUE("\u001B[34m"),
        CYAN("\u001B[36m"),
        PURPLE("\u001B[35m");

        public final String color;
        private Colors(String color) {
            this.color = color;
        }
    }

    public enum EmbedColors {
        JOIN(0x00ff00),
        LEAVE(0xff0000),
        ADVANCEMENT(0xffff00),
        CHALLENGE(0x800080),
        DEATH(0x000000),
        DEFAULT(0x2f3136);

        public final int color;
        private EmbedColors(int color) { this.color = color; }
    }

}
