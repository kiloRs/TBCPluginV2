package fun.tbcraft.play.utils;

import io.lumine.mythic.lib.comp.hexcolor.HexColorParser;
import io.lumine.mythic.utils.chat.ColorString;

public class ColoredWords{
    private static HexColorParser hex;

    public ColoredWords(){
        hex = new HexColorParser();
    }
    public static String get(String original){
        if ( !original.isEmpty() ) {
            return    hex.parseColorCodes(original);
        }
        return ColorString.get(original); }
}
