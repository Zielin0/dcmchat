package tk.siurasowo.dcmchat.utils;


public class ChatUtils {
    public static final String PREFIX = Colorize.color("&d&lDcmchat &r&7> &r&f");

    private static final String DISCORD_PREFIX = Colorize.color("&8["+ Hex.format(Colorize.BLURPLE_HEX) + "Discord&r&8] &7<");
    private static final String DISCORD_SUFFIX = Colorize.color("&7> &r&f");

    public String msgWithPrefix(String msg) {
        return PREFIX + msg;
    }

    public static String msgDiscordPrefix(String s, String msg) {
        return DISCORD_PREFIX + s + DISCORD_SUFFIX + msg;
    }

}
