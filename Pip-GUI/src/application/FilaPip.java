package application;


public class FilaPip {

    private String paquete;
    private String version;
    private String ultima;

    public FilaPip(String fila) {

        String[] datos = fila.split("\\s+", 0);
        if (datos.length != 4) {
            System.out.printf("Partes: %d\n", datos.length);
            throw new RuntimeException("La fila está mal formateada, longitud: " + datos.length);
        }

        this.paquete = datos[0];
        this.version = datos[1];
        this.ultima = datos[2];
    }

    public String getPackage() { return this.paquete; }
    public String getVersion() { return this.version; }
    public String getLatest() { return this.ultima; }

}
