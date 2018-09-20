package modelo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SinTituloFecha {

    private static DateFormat df =
        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public static String getHoyString() {
        Date now = new Date();
        return df.format(now);
    }

}
