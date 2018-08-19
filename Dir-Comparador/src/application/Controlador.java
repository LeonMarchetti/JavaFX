package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controlador implements Initializable {

    @FXML private Button btnDir1;
    @FXML private Button btnDir2;
    @FXML private Button btnComparar;
    @FXML private TextField txtDir1;
    @FXML private TextField txtDir2;

    private static final String TITULO = "Comparador de Directorios";
    private final String ARCHIVO = getClass().getClassLoader().getResource("").getPath()
        + "dir-comparador.properties";
    private Properties props;

    private class HiloComparar extends Thread {
        @Override public void run() {
            File dir1 = new File(txtDir1.getText());
            File dir2 = new File(txtDir2.getText());

            if (!dir1.isDirectory()) {
                alertaError(
                    "Error: No es un directorio",
                    "Campo de texto n°1: " + txtDir1.getText());
                return;
            }

            if (!dir2.isDirectory()) {
                alertaError(
                    "Error: No es un directorio",
                    "Campo de texto n°2: " + txtDir2.getText());
                return;
            }

            List<String> nombresDir1 = new ArrayList<>();
            for (File f : dir1.listFiles()) {
                nombresDir1.add(f.getName());
            }

            List<String> nombresDir2 = new ArrayList<>();
            for (File f : dir2.listFiles()) {
                nombresDir2.add(f.getName());
            }

            List<String> faltantesDir1 = new ArrayList<>();
            List<String> faltantesDir2 = new ArrayList<>();

            for (String s : nombresDir2) {
                if (!nombresDir1.contains(s)) {
                    faltantesDir1.add(s);
                }
            }

            for (String s : nombresDir1) {
                if (!nombresDir2.contains(s)) {
                    faltantesDir2.add(s);
                }
            }

            System.out.println();
            System.out.printf("Faltantes Dir1: %d\n", faltantesDir1.size());
            System.out.printf("Faltantes Dir2: %d\n", faltantesDir2.size());

            desactivarBotones(false);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            props = new Properties();
            props.load(new FileReader(ARCHIVO));

            txtDir1.setText(props.getProperty("directorio1", ""));
            txtDir2.setText(props.getProperty("directorio2", ""));

        } catch (FileNotFoundException e) {
            System.out.printf("FileNotFoundException\n%s\n", e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void actComparar(ActionEvent event) {
        desactivarBotones(true);
        (new HiloComparar()).start();
    }

    private void alertaError(String encabezado, String contenido) {
        Platform.runLater(new Thread() {
            @Override public void run() {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle(TITULO);
                alerta.setHeaderText(encabezado);
                alerta.setContentText(contenido);
                alerta.show();
            }
        });
    }

    private void desactivarBotones(boolean desactivar) {
        btnComparar.setDisable(desactivar);
        btnDir1.setDisable(desactivar);
        btnDir2.setDisable(desactivar);
    }

    public void guardarDatos() {
        try {
            props.setProperty("directorio1", txtDir1.getText());
            props.setProperty("directorio2", txtDir2.getText());
            props.store(new FileOutputStream(ARCHIVO), null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            alertaError("Error: Archivo de configuración no encontrado", ARCHIVO);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
