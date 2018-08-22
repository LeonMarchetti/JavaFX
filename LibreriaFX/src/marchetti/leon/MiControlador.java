package marchetti.leon;


import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public abstract class MiControlador implements Initializable {

    protected Stage stage;
    protected static String TITULO = "Aplicación";

    protected abstract void desactivarControles(boolean desactivar);

    protected void alertaError(String encabezado, String contenido) {
        Platform.runLater(() -> {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle(TITULO);
            alerta.setHeaderText(encabezado);
            alerta.setContentText(contenido);
            alerta.show();
        });
    }

    protected boolean alertaConfirmacion(String encabezado, String contenido) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle(TITULO);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return (resultado.get() == ButtonType.OK);
    }

    protected File elegirDirectorio() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(TITULO);
        return dc.showDialog(this.stage);
    }

    public void guardarDatos() throws IOException {}

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
