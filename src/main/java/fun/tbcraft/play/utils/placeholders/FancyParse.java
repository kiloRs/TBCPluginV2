package fun.tbcraft.play.utils.placeholders;

import fun.tbcraft.play.TBCPlugin;
import me.devtec.theapi.configapi.Config;
import org.apache.commons.lang3.Validate;

import java.util.Locale;
import java.util.Optional;

public class FancyParse{
    private static final Config currentConfig = TBCPlugin.getSettings();
    private String parsed = "";
    private String original = "";

    public static String parse(String i){
        return new FancyParse(i).getOrThrow();
    }
    public FancyParse(String s){
        this.original = s;
        final var verified = Validate.notEmpty(s , "Empty String Found");
            if ( verified.isEmpty() ){
                return;
            }

            if ( verified.equalsIgnoreCase("true") ){
                this.parsed = currentConfig.getString("Fancy.True");
            } else if ( verified.equalsIgnoreCase("false") ) {
                this.parsed = currentConfig.getString("Fancy.False");
            }
            else {
                this.parsed = currentConfig.getString("Fancy.Default")!=null?currentConfig.getString("Fancy.Default"):"";
            }

            if ( this.original.equalsIgnoreCase(this.parsed) ){
                return;
            }

            TBCPlugin.debug("[Changed Original: " + this.original + " to " + this.parsed.toUpperCase(Locale.ROOT));

    }

    private String getParsed ( ) {
        return parsed;
    }
    public String getOrThrow(){
        return Optional.ofNullable(getParsed()).orElseThrow(( ) -> new RuntimeException("Very Invalid Parsed String for " + original));
    }

    public String getOriginal ( ) {
        return original;
    }
}
