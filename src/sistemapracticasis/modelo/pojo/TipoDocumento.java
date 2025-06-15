package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum TipoDocumento {
    CARTAACEPTACION("carta de aceptacion"),
    CONSTANCIASEGURO("constancia seguro"),
    CRONOGRAMAACTIVIDADES("cronograma actividades"),
    HORARIOUV("horario uv"),
    OFICIOASIGNACION("oficio asignacion");

    private final String valorEnDB;

    private TipoDocumento (String valorEnDB) {
        this.valorEnDB = valorEnDB;
    }

    public String getValorEnDB() {
        return valorEnDB;
    }

    public static TipoDocumento fromValor(String texto) {
        for (TipoDocumento tipo : values()) {
            if (tipo.valorEnDB.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
