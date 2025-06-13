package sistemapracticasis.modelo.pojo;

public class Usuario {
    private int idUsuario;
    private String username;
    private String tipo;

    public Usuario() {}

    public Usuario(int idUsuario, String username, String tipo) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.tipo = tipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return username + " (" + tipo + ")";
    }
}
