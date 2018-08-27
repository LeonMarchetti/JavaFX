package marchetti.leon.vista;


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
    protected String TITULO = "Aplicación";

    protected abstract void desactivarControles(boolean desactivar);

    /**
     * Lanza una alerta de error.
     * Se ejecuta en el hilo de aplicación de JavaFx.
     * @param encabezado Encabezado de la alerta
     * @param contenido Contenido de la alerta
     */
    protected void alertaError(String encabezado, String contenido) {
        Platform.runLater(() -> {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle(TITULO);
            alerta.setHeaderText(encabezado);
            alerta.setContentText(contenido);
            agregarIconos(alerta);

            alerta.show();
        });
    }

    /**
     * Lanza una ventana de confirmación, con botones <i>Aceptar</i>
     * y <i>Cancelar</i>.
     * @param encabezado Encabezado de la alerta
     * @param contenido Contenido de la alerta
     * @return
     */
    protected boolean alertaConfirmacion(String encabezado, String contenido) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle(TITULO);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        agregarIconos(alerta);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return (resultado.get() == ButtonType.OK);
    }

    /**
     * Agrega los íconos de la ventana principal de la aplicación a
     * la alerta correspondiente.
     * @param alerta Alerta al que agregar los íconos
     */
    private void agregarIconos(Alert alerta) {
        ((Stage)
            alerta
            .getDialogPane()
            .getScene()
            .getWindow())
            .getIcons()
            .addAll(stage.getIcons());
    }

    /**
     * Lanza un diálogo para elegir un directorio.
     * @return Directorio elegido
     */
    protected File elegirDirectorio() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(TITULO);
        return dc.showDialog(this.stage);
    }

    /**
     * Método que guarda datos de la aplicación que se ejecuta
     * cuando se cierra la ventana.
     * @throws IOException
     */
    public abstract void guardarDatos() throws IOException;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Obtiene el título de la aplicación.
     * @return El título de la aplicación
     */
    public String getTitulo() {
        return TITULO;
    }
}
