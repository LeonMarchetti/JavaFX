package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import marchetti.leon.vista.MiControlador;


public class MainControlador extends MiControlador {

    @FXML private TextField txtMain1;
    @FXML private TextField txtMain2;

    @FXML private Controlador1 ctrl1Controller;
    @FXML private Controlador2 ctrl2Controller;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ctrl1Controller.getBtnTab().setOnMouseClicked(e -> {
            txtMain1.setText(ctrl1Controller.getTexto());
        });

        ctrl2Controller.getBtnTab().setOnMouseClicked(e -> {
            txtMain2.setText(ctrl2Controller.getTexto());
        });
    }

    @Override
    protected void desactivarControles(boolean desactivar) {
        txtMain1.setEditable(desactivar);
        txtMain2.setEditable(desactivar);
    }

    @Override
    public void guardarDatos() throws IOException {

    }
}
