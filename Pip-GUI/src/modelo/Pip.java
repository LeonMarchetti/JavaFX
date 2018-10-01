package modelo;

import java.io.IOException;
import java.util.List;
import marchetti.leon.ProcesoConsola;

public class Pip {

    private static final String COMANDO_LISTA = "pip list --outdated";
    private static final String COMANDO_ACTUALIZAR = "pip install --upgrade %s";
    private static final String COMANDO_DESINSTALAR = "pip uninstall %s --yes";

    public static List<String> listaActualizables()
        throws IOException, InterruptedException
    {
        ProcesoConsola proceso = new ProcesoConsola(COMANDO_LISTA);
        proceso.ejecutar(null);
        List<String> resultado = proceso.getLineasSalida();

        // Sacar las primeras dos líneas de la salida de "pip list":
        if (!resultado.isEmpty()) {
            resultado.remove(0);
            resultado.remove(0);
        }

        return resultado;
    }

    public static String actualizar(String paquete)
        throws IOException, InterruptedException
    {
        ProcesoConsola proceso = new ProcesoConsola(
            String.format(COMANDO_ACTUALIZAR, paquete));
        proceso.ejecutar(null);

        /* Devuelve el error devuelto por el proceso para saber si se completó
         * actualización del paquete con éxito.
         */
        return proceso.getError();
    }

    public static String desinstalar(String paquete)
        throws InterruptedException, IOException
    {
        ProcesoConsola proceso = new ProcesoConsola(
            String.format(COMANDO_DESINSTALAR, paquete));
        proceso.ejecutar(null);

        /* Devuelve el error devuelto por el proceso para saber si se completó
         * actualización del paquete con éxito.
         */
        return proceso.getError();

    }

}
