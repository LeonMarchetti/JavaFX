package application;

import application.ControladorPip.HiloActualizarPip;
import application.ControladorPip.HiloDesinstalarPip;
import javafx.scene.control.Button;

public class FilaPip {

    private String paquete;
    private String version;
    private String ultima;
    private Button actualizar;
    private Button desinstalar;

    public FilaPip(String fila, ControladorPip controlador) {

        String[] datos = fila.split("\\s+", 0);
        if (datos.length != 4) {
            System.out.printf("Partes: %d\n", datos.length);
            throw new RuntimeException("La fila está mal formateada, longitud: " + datos.length);
        }

        this.paquete = datos[0];
        this.version = datos[1];
        this.ultima = datos[2];
        this.actualizar = new Button("Actualizar");
        this.desinstalar = new Button("Desinstalar");

        this.actualizar.setOnMouseClicked(
            e -> {
                HiloActualizarPip hilo = controlador.new HiloActualizarPip(this.paquete);
                hilo.start();
            });
        this.desinstalar.setOnMouseClicked(
            e -> {
                HiloDesinstalarPip hilo = controlador.new HiloDesinstalarPip(this.paquete);
                hilo.start();
            });
    }

    public String getPackage() { return this.paquete; }
    public String getVersion() { return this.version; }
    public String getLatest() { return this.ultima; }
    public Button getActualizar() { return this.actualizar; }
    public Button getDesinstalar() { return this.desinstalar; }
}
