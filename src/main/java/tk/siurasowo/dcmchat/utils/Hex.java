package tk.siurasowo.dcmchat.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hex {

    private final static Pattern PATTERN = Pattern.compile("(#[A-Fa-f0-9]{6})");

    // Thanks to: https://urlregex.com/
    public final static String LINK_PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static String format(String hexCode) {
        Matcher matcher = PATTERN.matcher(hexCode);
        while (matcher.find()) {
            hexCode = hexCode.replace(matcher.group(), "" + ChatColor.of(matcher.group()));
        }
        return hexCode;
    }

}
