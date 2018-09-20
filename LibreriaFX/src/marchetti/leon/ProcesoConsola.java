package marchetti.leon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcesoConsola {

    private static Runtime rt = Runtime.getRuntime();

    private String comando;
    private List<String> lineasSalida = new ArrayList<String>();
    private List<String> lineasError = new ArrayList<String>();

    public ProcesoConsola(String comando) {
        this.comando = comando;
    }

    public int ejecutar(File dir)
        throws InterruptedException, IOException
    {
        Process proceso = rt.exec(this.comando, null, dir);

        BufferedReader brEntrada = new BufferedReader(
            new InputStreamReader(
                proceso.getInputStream()));

        String linea;
        while ((linea = brEntrada.readLine()) != null) {
            lineasSalida.add(linea);
        }

        BufferedReader brError = new BufferedReader(
            new InputStreamReader(
                proceso.getErrorStream()));

        while ((linea = brError.readLine()) != null) {
            lineasError.add(linea);
        }

        return proceso.waitFor();
    }

    public String getSalida() {
        return String.join("\n", this.lineasSalida);
    }

    public List<String> getLineasSalida() {
        return this.lineasSalida;
    }

    public String getError() {
        return String.join("\n", this.lineasError);
    }

    public List<String> getLineasError() {
        return this.lineasError;
    }
}
