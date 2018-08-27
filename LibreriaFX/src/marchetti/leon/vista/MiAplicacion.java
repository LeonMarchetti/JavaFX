package marchetti.leon.vista;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Implementación propia de una aplicación con JavaFX.
 * Para utilizarla hay que sobreescribir el método <code>start(Stage)</code>,
 * llamar a <code>setLocation(String)</code> con la ruta del archivo
 * .fxml a utilizar y luego llamar a <code>super.start(Stage)</code>
 * para iniciar la aplicación.
 * Se puede llamar a <code>addIcon(Stage, String)</code> para usar
 * un ícono en la aplicación.
 * @author LeoAM
 *
 */
public abstract class MiAplicacion extends Application {

    /** Ruta con la ubicación de la vista */
    protected String location = "";

    @Override
    public void start(Stage primaryStage) {
        try {
            if (location.equals("")) {
                throw new UnsupportedOperationException(
                    "Falta ingresar el nombre del archivo .fxml.");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            Parent root = loader.load();

            // Controlador:
            MiControlador controlador = loader.getController();
            controlador.setStage(primaryStage);
            primaryStage.setTitle(controlador.getTitulo());

            // Guardar datos de la aplicación al cerrar la ventana:
            primaryStage.setOnHidden(e -> {
                try {
                    controlador.guardarDatos();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configura la vista de la aplicación.
     * @param location Ruta de la vista de la aplicación
     */
    protected void setLocation(String location) {
        this.location = location;
    }

    /**
     * Configura el ícono de la aplicación.
     * @param stage Escenario de la aplicación
     * @param name Ruta del ícono
     */
    protected void addIcon(Stage stage, String name) {
        stage.getIcons().add(
            new Image(this.getClass().getResourceAsStream(name)));
    }
}
