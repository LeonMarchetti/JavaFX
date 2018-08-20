package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;


public class ControladorSalida implements Initializable {

    @FXML private ListView<File> lstDir1;
    @FXML private ListView<File> lstDir2;

    /**
     * Clase que extiende a ListCell<File> para mostrar el nombre
     * sin ruta de un archivo.
     * @author LeoAM
     *
     */
    private class FileCell extends ListCell<File> {
        /**
         * Se sobreescribe el método para que la celda muestre el
         * nombre sin la ruta del archivo.
         * @param item El archivo
         * @param empty Si la celda está vacía
         */
        @Override public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lstDir1.setCellFactory(e -> new FileCell());
        lstDir2.setCellFactory(e -> new FileCell());
    }

    /**
     * Pasa las listas de archivos faltantes a esta vista, cuyos
     * contenidos serán mostrados en las listview de esta ventana.
     * @param faltantesDir1 Lista de faltantes del primer directorio
     * @param faltantesDir2 Lista de faltantes del segundo directorio
     */
    public void setListas(
        List<File> faltantesDir1,
        List<File> faltantesDir2) {

        lstDir1.setItems(FXCollections.observableArrayList(faltantesDir1));
        lstDir2.setItems(FXCollections.observableArrayList(faltantesDir2));
    }
}
