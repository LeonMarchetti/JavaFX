package application;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public abstract class MiControlador implements Initializable {

    protected static String TITULO = "Aplicación";

    protected abstract void desactivarBotones(boolean desactivar);

    protected void alertaError(String encabezado, String contenido) {
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
}
