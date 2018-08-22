package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Pip {

    public static List<String> listaActualizables() throws IOException, InterruptedException {

        String comando = "pip list --outdated";
        System.out.printf("Ejecutando '%s'\n", comando);
        Process pr = Runtime.getRuntime().exec(comando);
        pr.waitFor();

        BufferedReader br = new BufferedReader(
            new InputStreamReader(
                pr.getInputStream()));

        List<String> resultado = new ArrayList<String>();
        String salida;
        int i = 0;
        while ((salida = br.readLine()) != null) {
            if (i++ > 1) {
                resultado.add(salida);
            }
            System.out.println(salida);
        }
        return resultado;
    }

    public static String actualizar(String paquete) throws IOException, InterruptedException {

        String comando = String.format("pip install --upgrade %s", paquete);
        System.out.printf("Ejecutando: '%s'\n", comando);
        Process pr = Runtime.getRuntime().exec(
            comando);

        if (pr.waitFor() != 0) {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    pr.getErrorStream()));

            String salida = "";
            String linea;
            while ((linea = br.readLine()) != null) {
                salida += linea;
                System.out.println(linea);
            }

            return salida;

        } else {
            return "";
        }
    }

}
