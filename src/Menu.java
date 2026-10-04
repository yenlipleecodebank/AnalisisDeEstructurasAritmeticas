import java.util.Scanner;
import java.util.ArrayList;

public class Menu {

    // Atributos
    private Scanner scanner;

    // Constructor
    public Menu() {
        scanner = new Scanner(System.in);
    }

    // Metodo principal del menú
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- ANÁLISIS DE EXPRESIONES ARITMÉTICAS ---");
            System.out.println("1. Ingresar y analizar una expresión");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            // Gestión de excepciones para capturar entradas erróneas o errores de ejecución
            try {
                String opcion = scanner.nextLine();

                if (opcion.equals("1")) {
                    procesarExpresion();
                } else if (opcion.equals("2")) {
                    salir = true;
                    System.out.println("Saliendo del programa. ¡Éxitos en el proyecto!");
                } else {
                    System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }

    // Metodo para procesar la expresión
    private void procesarExpresion() {
        System.out.print("Ingrese la expresión aritmética (Ejemplo: (A + 10) * B): ");
        String expresion = scanner.nextLine();

        // Extraer los tokens (Variables, Números, Operadores, Paréntesis)
        ArrayList<Tokens> listaTokens = extraerTokens(expresion);

        // Validar si la lista de tokens está vacía para evitar errores
        if (listaTokens.isEmpty()) {
            System.out.println("No se ingresó ninguna expresión válida.");
            return;
        }

        System.out.println("\n--- TOKENS IDENTIFICADOS ---");
        for (int i = 0; i < listaTokens.size(); i++) {
            System.out.println(listaTokens.get(i).toString());
        }

        // Si el metodo retorna false, usamos 'return' para cortar la ejecución aquí mismo
        if (!verificarOperadorInicial(listaTokens)) {
            System.out.println("La expresión es INVÁLIDA (Error de sintaxis inicial).");
            return;
        }

        // Usar la Pila para validar sintaxis
        verificarBalance(listaTokens);
    }

    // Metodo para validar que la expresión no comience con un operador inválido
    private boolean verificarOperadorInicial(ArrayList<Tokens> listaTokens) {
        // Si la lista está vacía, no hay nada que evaluar
        if (listaTokens.isEmpty()) {
            return false;
        }

        Tokens primerToken = listaTokens.get(0);

        // Verificamos si el primer token fue catalogado como operador
        if (primerToken.getTipo().equals("OPERADOR")) {
            String valor = primerToken.getValor();

            // Los operadores *, / y ^ no pueden iniciar una expresión matemática
            // (Nota: el + y el - a veces se permiten como signos de números negativos/positivos,
            // por eso solo bloqueamos estos tres explícitamente)
            if (valor.equals("*") || valor.equals("/") || valor.equals("^")) {
                System.out.println("Error de Sintaxis: La expresión no puede comenzar con el operador '" + valor + "'.");
                return false;
            }
        }

        // Si pasa la prueba, es válido
        return true;
    }

    // Metodo de análisis léxico: convierte la cadena de texto en objetos Tokens
    private ArrayList<Tokens> extraerTokens(String expresion) {
        ArrayList<Tokens> lista = new ArrayList<>();
        String acumulador = "";

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Ignorar espacios en blanco
            if (c == ' ') {
                continue;
            }

            // Evaluar si es una VARIABLE (Letras)
            if (Character.isLetter(c)) {
                acumulador = "";
                while (i < expresion.length() && Character.isLetter(expresion.charAt(i))) {
                    acumulador += expresion.charAt(i);
                    i++;
                }
                lista.add(new Tokens(acumulador, "VARIABLE"));
                i--; // Ajuste del índice
            }
            // Evaluar si es un NÚMERO LITERAL
            else if (Character.isDigit(c)) {
                acumulador = "";
                while (i < expresion.length() && Character.isDigit(expresion.charAt(i))) {
                    acumulador += expresion.charAt(i);
                    i++;
                }
                lista.add(new Tokens(acumulador, "NUMERO_LITERAL"));
                i--;
            }
            // Evaluar paréntesis
            else if (c == '(') {
                lista.add(new Tokens("(", "PARENTESIS_APERTURA"));
            } else if (c == ')') {
                lista.add(new Tokens(")", "PARENTESIS_CIERRE"));
            }
            // Evaluar operadores matemáticos
            else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                lista.add(new Tokens(String.valueOf(c), "OPERADOR"));
            } else {
                lista.add(new Tokens(String.valueOf(c), "DESCONOCIDO"));
            }
        }
        return lista;
    }

    // Uso de la PilaDinamica para verificar el balance de la expresión
    private void verificarBalance(ArrayList<Tokens> listaTokens) {
        PilaDinamica pila = new PilaDinamica();
        boolean balanceado = true;

        System.out.println("\n--- EVALUACIÓN CON PILA (BALANCEO DE PARÉNTESIS) ---");

        for (int i = 0; i < listaTokens.size(); i++) {
            Tokens tokenActual = listaTokens.get(i);

            // Si abre paréntesis, ingresa a la pila
            if (tokenActual.getTipo().equals("PARENTESIS_APERTURA")) {
                pila.push(tokenActual);
                System.out.println("Acción PUSH: Se agregó un '(' a la pila.");
            }
            // Si cierra paréntesis, debe extraer su correspondiente pareja de la pila
            else if (tokenActual.getTipo().equals("PARENTESIS_CIERRE")) {
                if (pila.estaVacia()) {
                    balanceado = false;
                    System.out.println("Error: Se encontró ')' pero la pila está vacía (Falta una apertura).");
                    break;
                } else {
                    pila.pop();
                    System.out.println("Acción POP: Se retiró un '(' de la pila por cierre correspondiente.");
                }
            }
        }

        // Si al terminar el ciclo, la pila tiene elementos, sobraron aperturas
        if (!pila.estaVacia() && balanceado) {
            balanceado = false;
            System.out.println("Error: La expresión terminó pero la pila contiene '(' sin cerrar.");
        }

        System.out.println("\n--- RESULTADO FINAL ---");
        if (balanceado) {
            System.out.println("La expresión es VÁLIDA y está correctamente balanceada.");
        } else {
            System.out.println("La expresión es INVÁLIDA (Error de sintaxis).");
        }
    }
}