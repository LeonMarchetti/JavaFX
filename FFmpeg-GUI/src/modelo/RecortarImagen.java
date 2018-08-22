package modelo;

import java.io.IOException;
import marchetti.leon.EjecutarComando;

public class RecortarImagen {

    private static String formatoComandoRecorte =
        "ffmpeg -i %s -vf \"crop=%d:%d:%d:%d\" %s -y";

    public static int recortar(
        String dir,
        String imagen,
        String salida,
        int ancho,
        int alto,
        int x,
        int y) throws IOException, InterruptedException {

        if (imagen.equals("") ||
            salida.equals("")
           ) {
            return -1;

        } else {
            String rutaImagen = String.format("\"%s\\%s\"", dir, imagen);
            String rutaSalida = String.format("\"%s\\%s\"", dir, salida);
            String comandoRecortar = String.format(formatoComandoRecorte,
                rutaImagen, ancho, alto, x, y, rutaSalida);
            return EjecutarComando.ejecutar(comandoRecortar);
        }
    }
}
