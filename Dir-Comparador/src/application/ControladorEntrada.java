package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.ComparadorDirectorios;


public class ControladorEntrada extends MiControlador {

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

            if (!ComparadorDirectorios.esDirValido(dir1)) {
                alertaError(
                    "Error: No es un directorio",
                    "Campo de texto n°1: " + txtDir1.getText());
                desactivarControles(false);
                return;
            }

            if (!ComparadorDirectorios.esDirValido(dir2)) {
                alertaError(
                    "Error: No es un directorio",
                    "Campo de texto n°2: " + txtDir2.getText());
                desactivarControles(false);
                return;
            }

            ComparadorDirectorios cd = new ComparadorDirectorios(dir1, dir2);

            desactivarControles(false);

            // Mostrar la vista de salida de archivos faltantes:
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/VistaSalida.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);

                        ControladorSalida controlador = loader.getController();
                        controlador.setDir1(cd.getDir1());
                        controlador.setDir2(cd.getDir2());
                        controlador.setListas(cd.getFaltantesDir1(), cd.getFaltantesDir2());

                        Stage stage = new Stage();
                        controlador.setStage(stage);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle(TITULO);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        alertaError("Error", "No se pudo cargar la siguiente pantalla.");
                    }
                }
            });
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
        desactivarControles(true);
        (new HiloComparar()).start();
    }

    @FXML private void actSeleccionarDir(ActionEvent event) {
        File dir = elegirDirectorio();
        if (dir != null) {
            if (event.getSource() == btnDir1) {
                txtDir1.setText(dir.getAbsolutePath());

            } else if (event.getSource() == btnDir2) {
                txtDir2.setText(dir.getAbsolutePath());
            }
        }
    }

    @Override
    protected void desactivarControles(boolean desactivar) {
        btnComparar.setDisable(desactivar);
        btnDir1.setDisable(desactivar);
        btnDir2.setDisable(desactivar);
    }

    @Override
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
