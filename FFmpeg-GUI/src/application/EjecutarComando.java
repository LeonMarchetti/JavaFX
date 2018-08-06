package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutarComando {

    private static Runtime rt = Runtime.getRuntime();

    public static int ejecutar(String comando)
        throws IOException, InterruptedException {

        System.out.printf("Comando: [%s]\n", comando);
        Process pr = rt.exec(comando);

        BufferedReader errorStream = new BufferedReader(
            new InputStreamReader(
                pr.getErrorStream()));

        String linea = null;
        while ((linea = errorStream.readLine()) != null) {
            System.out.println(linea);
        }

        int res = pr.waitFor();
        return res;
    }
}
