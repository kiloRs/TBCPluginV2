package fun.tbcraft.play.utils;

import io.lumine.mythic.lib.comp.hexcolor.HexColorParser;
import io.lumine.mythic.utils.chat.ColorString;

public class ColorWords{
    private static HexColorParser hex;

    public ColorWords (){
        hex = new HexColorParser();
    }

    public static String hex(String origin){
       return hex.parseColorCodes(origin);
    }
    public static String get(String original){
        if ( !original.isEmpty() ) {
            return hex(original);
        }
        return ColorString.get(original); }
}
