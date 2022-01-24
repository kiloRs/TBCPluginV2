package fun.tbcraft.play;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TBCTimeHandler{

    /**
     * CommandBook getTime function, credit go to them for this!
     *
     * @param time The time to parse
     * @return The name of this time
     */
    public static String getTimeString(long time) {
        int hours = (int) ((time / 1000 + 8) % 24);
        int minutes = (int) (60 * (time % 1000) / 1000);
        return String.format("%02d:%02d (%d:%02d %s)", hours, minutes, (hours % 12) == 0 ? 12 : hours % 12, minutes, hours < 12 ? "am" : "pm");
    }


    public static LocalDateTime getTime(long mills) {
        return Instant.ofEpochMilli(mills).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
