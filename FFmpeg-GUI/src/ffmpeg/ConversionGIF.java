package ffmpeg;

import java.io.IOException;
import application.EjecutarComando;

public class ConversionGIF {

    private static String formatoComandoConversion =
        "ffmpeg -i %s -ss %s -to %s %s -hide_banner -y";
    private static String formatoComandoEscala =
        "ffmpeg -i %s -vf scale=%s %s -hide_banner -y";

    public static int convertir(
        String dir,
        String video,
        String salida,
        String inicio,
        String fin,
        String escala) throws IOException, InterruptedException {

        if (video.equals("") ||
            salida.equals("") ||
            inicio.equals("") ||
            fin.equals("")
           ) {
            return -1;

        } else {
            String rutaVideo = String.format("\"%s\\%s\"", dir, video);
            String rutaSalida = String.format("\"%s\\%s\"", dir, salida);
            String comandoConvertir = String.format(formatoComandoConversion,
                rutaVideo, inicio, fin, rutaSalida);

            int res = EjecutarComando.ejecutar(comandoConvertir);
            if (res == 0) {
                if (!escala.equals("")) {
                    String comandoEscala = String.format(formatoComandoEscala,
                        rutaSalida, escala, rutaSalida);
                    return EjecutarComando.ejecutar(comandoEscala);
                }
            }
            return res;
        }
    }
}
