package fun.tbcraft.play.utils.log;

import fun.tbcraft.play.TBCPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TBCFileLogger{
    private File location;
    public TBCFileLogger (String locPlusExt){
        this.location = new File(TBCPlugin.getPlugin().getDataFolder() , locPlusExt);

        if ( !location.isFile() && !location.exists() ){
            try {
                location.createNewFile();
                TBCPlugin.log("Loading Log File...");
            } catch (IOException e) {
                e.printStackTrace();
                TBCPlugin.errorLog("Log File Error!");
                return;
            }

            TBCPlugin.log("Log File Loaded");
        }
    }
    public void logToFile(String message, String fileName, String extension) {
        if ( this.location == null || !this.location.exists() ){
            this.location = new File(TBCPlugin.getPlugin().getDataFolder() , fileName + "." + extension);
        }
        try {
            if (!this.location.getParentFile().exists() || !this.location.getParentFile().isDirectory() ) {
                this.location.getParentFile().mkdir();
            }

            File saveTo = this.location;
            if (!saveTo.exists()) {
                saveTo.createNewFile();
            }

            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
