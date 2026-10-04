import java.util.ArrayList;

// Implementación de la pila dinámica para la gestión de Tokens
public class PilaDinamica {

    // Atributo
    private ArrayList<Tokens> pila;

    // Constructor
    public PilaDinamica() {
        pila = new ArrayList<>();
    }

    // Operación para verificar si está vacía
    public boolean estaVacia() {
        return pila.isEmpty();
    }

    // Operación Push: Agrega un token a la cima
    public void push(Tokens nodo) {
        pila.add(nodo);
    }

    // Operación Pop: Extrae y retorna el token de la cima
    public Tokens pop() {
        if (estaVacia()) {
            System.out.println("Error: La pila está vacia");
            return null;
        }
        // Retira el último elemento
        return pila.remove(pila.size() - 1);
    }

    // Operación Peek:
    public Tokens peek() {
        if (estaVacia()) {
            System.out.println("Error: La pila está vacia");
            return null;
        }
        // Obtiene el último elemento
        return pila.get(pila.size() - 1);
    }
}