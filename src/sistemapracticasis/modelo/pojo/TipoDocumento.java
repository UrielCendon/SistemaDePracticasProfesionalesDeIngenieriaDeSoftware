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

    private final String VALOR_EN_DB;

    private TipoDocumento (String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    public static TipoDocumento fromValor(String texto) {
        for (TipoDocumento tipo : values()) {
            if (tipo.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
