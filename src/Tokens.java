public class Tokens {

    // Atributos
    private String valor;
    private String tipo;

    // Constructor
    public Tokens(String valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    // Métodos
    public String getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    // Metodo sobreescrito para facilitar la impresión en consola (Pruebas de consola)
    @Override
    public String toString() {
        return "[Valor: " + valor + " | Tipo: " + tipo + "]";
    }
}