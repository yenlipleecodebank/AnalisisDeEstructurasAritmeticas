// Clase que representa un componente individual de la expresión (El Nodo)
public class Tokens {

    // Atributos encapsulados cumpliendo las buenas prácticas de POO
    private String valor;
    private String tipo;

    // Constructor para inicializar el token
    public Tokens(String valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    // Métodos accesores (Getters)
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