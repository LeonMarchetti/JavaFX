package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;


public class ControladorSalida extends MiControlador {

    @FXML private Button btnCopiar1;
    @FXML private Button btnCopiar2;
    @FXML private ListView<File> lstDir1;
    @FXML private ListView<File> lstDir2;

    private File dir1 = null;
    private File dir2 = null;

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

    /**
     * Copia el archivo al otro directorio
     * @param dirDestino El destino del archivo a copiar
     * @param f El archivo a copiar
     */
    private void copiarArchivo(File dirDestino, File f) {
        System.out.printf("Archivo: \"%s\"\t", f.getName());

        File archivoDestino = new File(
            dirDestino.getAbsolutePath()
            + File.separator
            + f.getName());

        System.out.printf("Destino: \"%s\"\n", archivoDestino.getAbsolutePath());

        try {
            Files.copy(
                f.toPath(),
                archivoDestino.toPath());

        } catch (IOException e) {
            e.printStackTrace();
            alertaError(
                "Error copiando el archivo",
                "Archivo: \"" + archivoDestino.toPath() + "\"");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lstDir1.setCellFactory(e -> new FileCell());
        lstDir2.setCellFactory(e -> new FileCell());

        lstDir1.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldV, newV) -> {
                copiarArchivo(dir1, newV);
                lstDir1.getItems().remove(newV);
        });

        lstDir2.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldV, newV) ->{
                copiarArchivo(dir2, newV);
                lstDir2.getItems().remove(newV);
        });
    }

    @Override
    protected void desactivarBotones(boolean desactivar) {
        btnCopiar1.setDisable(desactivar);
        btnCopiar2.setDisable(desactivar);
    }

    @FXML private void actCopiar1(ActionEvent envent) {
        for (File f : lstDir1.getItems()) {
            copiarArchivo(dir1, f);
        }
        lstDir1.getItems().clear();
    }

    @FXML private void actCopiar2(ActionEvent envent) {
        for (File f : lstDir2.getItems()) {
            copiarArchivo(dir2, f);
        }
        lstDir2.getItems().clear();
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

    public void setDir1(File dir) {
        this.dir1 = dir;
        btnCopiar1.setText("Copiar a \"" + dir.getAbsolutePath() + "\"");
    }

    public void setDir2(File dir) {
        this.dir2 = dir;
        btnCopiar2.setText("Copiar a \"" + dir.getAbsolutePath() + "\"");
    }

}
