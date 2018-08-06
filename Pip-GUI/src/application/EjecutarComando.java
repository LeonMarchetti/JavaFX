package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutarComando {

    private static Runtime rt = Runtime.getRuntime();

    private static void imprimirSalida(Process pr) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(
                pr.getErrorStream()));

        String salida;
        while ((salida = br.readLine()) != null) {
            System.out.println(salida);
        }
    }

    public static int ejecutar(String comando) throws IOException, InterruptedException {
        Process pr = rt.exec(comando);
        int res = pr.waitFor();
        if (res != 0) {
            imprimirSalida(pr);
        }
        return res;
    }
}
