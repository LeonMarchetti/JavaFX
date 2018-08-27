package application;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import marchetti.leon.vista.MiControlador;

public class Controlador extends MiControlador {

    @FXML private Button myButton;
    @FXML private TableColumn<Objeto, Integer> tblCol1;
    @FXML private TableColumn<Objeto, String> tblCol2;
    @FXML private TableColumn<Objeto, SubClase> tblCol3;
    @FXML private TableColumn<Objeto, LocalDate> tblCol4;
    @FXML private TableColumn<Objeto, CheckBox> tblCol5;
    @FXML private TableView<Objeto> tblTabla;
    @FXML private TextField myTextField;

    private ObservableList<Objeto> listaFilas = FXCollections.observableArrayList();

    public class SubClase {
        String descripcion;
        public SubClase(String descripcion) {
            this.descripcion = descripcion;
        }
        public String getDescripcion() {
            return this.descripcion;
        }
        @Override
        public String toString() {
            return this.descripcion;
        }
    }

    public class Objeto {
        private int id;
        private String descripcion;
        private float monto;
        private SubClase subClase;
        private LocalDate fecha;
        private CheckBox activo;

        public Objeto(int id, String descripcion, float monto, LocalDate fecha, boolean activo) {
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
        public void setActivo(CheckBox activo) {
            this.activo = activo;
        }
    }

    public Controlador() {
        TITULO = "Sin Título";
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        tblTabla.setItems(listaFilas);

        // Columnas ===========================================================
        tblCol1.setCellValueFactory(
                new PropertyValueFactory<Objeto, Integer>("id"));

        tblCol2.setCellValueFactory(
                new PropertyValueFactory<Objeto, String>("descripcion"));

        tblCol3.setCellValueFactory(
                new PropertyValueFactory<Objeto, SubClase>("subClase"));

        tblCol4.setCellValueFactory(
                new PropertyValueFactory<Objeto, LocalDate>("fecha"));

        tblCol5.setCellValueFactory(
            new PropertyValueFactory<Objeto, CheckBox>("activo"));

        Random rnd = new Random();
        int filas = rnd.nextInt(10) + 1;
        int largoDesc = rnd.nextInt(5) + 5;

        for (int i = 0; i < filas; i++) {
            listaFilas.add(
                    new Objeto(
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
                    (newV.getActivo().isSelected() ? "Activo" : "Inactivo")
            );

            // oldV es null
        });
    }

    private String stringRandom(int largo) {
        Random rnd = new Random();
        String CHARS = "ABCDEF0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < largo; i++) {
            sb.append(CHARS.charAt(rnd.nextInt(16)));
        }

        return sb.toString();
    }

    private LocalDate fechaRandom() {
        Random rnd = new Random();
        int minDay = (int) LocalDate.of(1970, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2018, 5, 18).toEpochDay();
        return LocalDate.ofEpochDay(rnd.nextInt(maxDay - minDay) + minDay);
    }

    // When user click on myButton
    // this method will be called.
    public void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");

        Date now = new Date();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


         // Model Data
         String dateTimeString = df.format(now);

         // Show in VIEW
         myTextField.setText(dateTimeString);

    }

    @Override
    protected void desactivarControles(boolean desactivar) {
        throw new UnsupportedOperationException("No hay controles para desactivar");
    }

    @Override
    public void guardarDatos() throws IOException {

    }
}
