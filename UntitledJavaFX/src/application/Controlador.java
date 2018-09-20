package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import marchetti.leon.ProcesoConsola;
import marchetti.leon.vista.MiControlador;
import modelo.SinTituloFecha;

public class Controlador extends MiControlador {

    @FXML private Button btnComando;
    @FXML private Button btnFecha;
    @FXML private TextField txtComando;
    @FXML private TextField txtFecha;


    public Controlador() {
        TITULO = "Sin Título";
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML public void actComando(ActionEvent event) {

        ProcesoConsola proceso = new ProcesoConsola("echo Hola Mundo");

        try {
            proceso.ejecutar(null);
            txtComando.setText(proceso.getSalida());

        } catch (InterruptedException e) {
            txtComando.setText("Proceso interrumpido...");
            e.printStackTrace();

        } catch (IOException e) {
            txtComando.setText("Error de I/O...");
            e.printStackTrace();
        }

    }

    @FXML public void actFecha(ActionEvent event) {
        txtFecha.setText(
            SinTituloFecha.getHoyString());
    }

    @Override
    protected void desactivarControles(boolean desactivar) {
        btnFecha.setDisable(desactivar);
        txtFecha.setEditable(!desactivar);
    }

    @Override
    public void guardarDatos() throws IOException {
        System.out.println("application.Controlador.guardarDatos");
    }
}
