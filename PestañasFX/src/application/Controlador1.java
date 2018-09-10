package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import marchetti.leon.vista.MiControlador;


public class Controlador1 extends MiControlador {

    @FXML private Button btnTab1;
    @FXML private TextField txtTab1;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @Override
    protected void desactivarControles(boolean desactivar) {

    }

    @Override
    public void guardarDatos() throws IOException {

    }

    String getTexto() {
        return txtTab1.getText() + " - Pestaña 1";
    }

    Button getBtnTab() {
        return btnTab1;
    }
}
