package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import marchetti.leon.vista.MiControlador;


public class Controlador2 extends MiControlador {

    @FXML private Button btnTab2;
    @FXML private TextField txtTab2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @Override
    protected void desactivarControles(boolean desactivar) {

    }

    @Override
    public void guardarDatos() throws IOException {

    }

    Button getBtnTab() {
        return btnTab2;
    }

    String getTexto() {
        return txtTab2.getText() + " - Pestaña 2";
    }
}
