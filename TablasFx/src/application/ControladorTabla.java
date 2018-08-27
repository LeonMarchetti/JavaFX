package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import marchetti.leon.vista.MiControlador;

/**
 * ControladorTabla.
 * @author LeoAM
 *
 */
public class ControladorTabla extends MiControlador {

    /**
     * Clase de ejemplo, que se va a mostrar en la tabla.
     * @author LeoAM
     *
     */
    public class Fila {
        private int id;
        private String descripcion;
        private float monto;
        private SubClase subClase;
        private LocalDate fecha;
        private CheckBox activo;

        public Fila(int id, String descripcion, float monto, LocalDate fecha, boolean activo) {
            this.id = id;
            this.descripcion = descripcion;
            this.monto = monto;
            this.subClase = new SubClase("SubClase: " + descripcion);
            this.fecha = fecha;
            this.activo = new CheckBox();
            this.activo.setSelected(activo);
        }
        public int getId() {
            return this.id;
        }
        public String getDescripcion() {
            return this.descripcion;
        }
        public float getMonto() {
            return this.monto;
        }
        public SubClase getSubClase() {
            return this.subClase;
        }
        public LocalDate getFecha() {
            return this.fecha;
        }
        public CheckBox getActivo() {
           return this.activo;
        }
        public boolean isActivo() {
            return this.activo.isSelected();
        }
    }

    /**
     * Clase de ejemplo, cuyos objetos van a ser atributos de la clase "Objeto".
     * @author LeoAM
     *
     */
    private class SubClase {
        String descripcion;
        public SubClase(String descripcion) {
            this.descripcion = descripcion;
        }
        @SuppressWarnings("unused")
        public String getDescripcion() {
            return this.descripcion;
        }
        @Override
        public String toString() {
            return this.descripcion;
        }
    }

    /** Columna id */
    @FXML private TableColumn<Fila, Integer> tblCol1;
    /** Columna descripción */
    @FXML private TableColumn<Fila, String> tblCol2;
    /** Columna clase2 */
    @FXML private TableColumn<Fila, SubClase> tblCol3;
    /** Columna fecha */
    @FXML private TableColumn<Fila, LocalDate> tblCol4;
    /** Columna activo */
    @FXML private TableColumn<Fila, CheckBox> tblCol5;
    /** Control tabla */
    @FXML private TableView<Fila> tblTabla;

    /** Lista de filas, sincronizada con la tabla. */
    private ObservableList<Fila> listaFilas = FXCollections.observableArrayList();

    public ControladorTabla() {
        TITULO = "Tablas JavaFX";
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tblTabla.setItems(listaFilas);

        // Columnas ===========================================================
        tblCol1.setCellValueFactory(
            new PropertyValueFactory<Fila, Integer>("id"));

        tblCol2.setCellValueFactory(
            new PropertyValueFactory<Fila, String>("descripcion"));

        tblCol3.setCellValueFactory(
            new PropertyValueFactory<Fila, SubClase>("subClase"));

        tblCol4.setCellValueFactory(
            new PropertyValueFactory<Fila, LocalDate>("fecha"));

        tblCol5.setCellValueFactory(
            new PropertyValueFactory<Fila, CheckBox>("activo"));

        // Crear filas ========================================================
        Random rnd = new Random();
        int filas = rnd.nextInt(10) + 1;
        int largoDesc = rnd.nextInt(5) + 5;

        for (int i = 0; i < filas; i++) {
            // Agregar objeto a la lista de filas:
            listaFilas.add(
                new Fila(
                    rnd.nextInt(100),
                    stringRandom(largoDesc),
                    rnd.nextFloat(),
                    fechaRandom(),
                    (rnd.nextInt(100) % 2 != 0)
            ));
        }

        // Evento cuando se selecciona una fila:
        tblTabla.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            System.out.printf("%d\t%s\t%s\t%s\t%s\n",
                newV.getId(),
                newV.getDescripcion(),
                newV.getSubClase(),
                newV.getFecha(),
                newV.isActivo() ? "Activo" : "Inactivo"
                );
            // oldV es la fila seleccionada anteriormente
        });
    }

    /**
     * Genera un String aleatorio, formado por caracteres A-F y 0-9.
     * @param largo Largo del string a generar.
     * @return String generado aleatoriamente.
     */
    private String stringRandom(int largo) {
        Random rnd = new Random();
        String CHARS = "ABCDEF0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < largo; i++) {
            sb.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
        }

        return sb.toString();
    }

    /**
     * Genera una fecha aleatoria entre 01/01/1970 y 18/05/2018.
     * @return Fecha generada aleatoriamente.
     */
    private LocalDate fechaRandom() {
        Random rnd = new Random();
        int minDay = (int) LocalDate.of(1970, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2018, 5, 18).toEpochDay();
        return LocalDate.ofEpochDay(rnd.nextInt(maxDay - minDay) + minDay);
    }

    @Override
    protected void desactivarControles(boolean desactivar) {
        throw new UnsupportedOperationException("No hay controles para desactivar");
    }

    @Override
    public void guardarDatos() throws IOException {

    }
}
