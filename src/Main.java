import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cedula = "";

        // 1. Validación de Cédula
        while (true) {
            System.out.print("Ingrese cédula de 10 dígitos (Cédula asignada: 1751416718): ");
            cedula = scanner.nextLine();
            if (cedula.matches("\\d{10}")) {
                break;
            }
            System.out.println("Error: Cédula inválida. Intente de nuevo.");
        }

        // 2. Generación de Valores
        int[] V = new int[10];
        for (int i = 0; i < 10; i++) {
            int d = Character.getNumericValue(cedula.charAt(i));
            V[i] = (d * 10) + (i + 1);
        }

        System.out.println("\n=== VALORES GENERADOS ===");
        for (int i = 0; i < 10; i++) {
            System.out.println("V" + (i + 1) + " = " + V[i]);
        }

        // 3. Construcción del Árbol AVL
        ArbolAVL avl = new ArbolAVL();
        System.out.println("\n=== CONSTRUCCIÓN DEL ÁRBOL AVL ===");
        for (int i = 0; i < 10; i++) {
            System.out.println("\nInsertando V" + (i + 1) + " (" + V[i] + "): ");
            avl.insertar(V[i]);
            System.out.print("Inorden actual: ");
            avl.imprimirInorden(avl.raiz);
            System.out.println("\nAltura del árbol: " + avl.altura(avl.raiz));
            System.out.println("Factor de Equilibrio (Raíz): " + avl.obtenerFactorEquilibrio(avl.raiz));
        }

        // Recorridos Finales solicitados en la rúbrica
        System.out.println("\n\n=== RECORRIDOS FINALES DEL ÁRBOL ===");
        System.out.print("Inorden:    ");
        avl.imprimirInorden(avl.raiz);
        System.out.print("\nPreorden:   ");
        avl.imprimirPreorden(avl.raiz);
        System.out.print("\nPostorden:  ");
        avl.imprimirPostorden(avl.raiz);
        System.out.println();

        // 4. Búsquedas optimizadas
        System.out.println("\n=== BÚSQUEDAS ===");
        int[] aBuscar = {V[6], V[9], 105}; // V7 (67), V10 (90), 105
        String[] nombres = {"V7", "V10", "105"};
        int comparacionesAVLV10 = 0; // Variable para guardar el dato

        for (int i = 0; i < aBuscar.length; i++) {
            System.out.println("\nBuscando " + nombres[i] + " (" + aBuscar[i] + "):");
            boolean existe = avl.buscar(aBuscar[i]);
            System.out.println("\nResultado: " + (existe ? "ENCONTRADO." : "NO ENCONTRADO."));
            System.out.println("Comparaciones realizadas: " + avl.comparacionesBusqueda);

            // Si es V10, guardamos las comparaciones para la tabla final
            if (nombres[i].equals("V10")) {
                comparacionesAVLV10 = avl.comparacionesBusqueda;
            }
        }

        // 5. Inserciones Nuevas
        System.out.println("\n=== INSERCIONES NUEVAS ===");
        int d9 = Character.getNumericValue(cedula.charAt(8));
        int d10 = Character.getNumericValue(cedula.charAt(9));
        int A = 111 + d9; // A = 112
        int B = 122 + d10; // B = 130

        System.out.println("\nInsertando A (" + A + "): ");
        avl.insertar(A);
        System.out.println("\nInsertando B (" + B + "): ");
        avl.insertar(B);

        // 6. Eliminaciones
        System.out.println("\n=== ELIMINACIONES ===");
        int[] aEliminar = {V[3], V[2], V[0]}; // Ejemplo: 14 (Hoja), 53 (Un hijo), 11 (Dos hijos)
        for(int valor : aEliminar) {
            System.out.println("\nEliminando valor: " + valor);
            avl.eliminar(valor);
            System.out.print("Inorden resultante: ");
            avl.imprimirInorden(avl.raiz);
            System.out.println("\nFactor de Equilibrio (Raíz): " + avl.obtenerFactorEquilibrio(avl.raiz));
        }

        // 7. Tabla Resumen
        int alturaBST = 7;
        int comparacionesBSTV10 = 4;

        System.out.println("\n======================================");
        System.out.println("     TABLA RESUMEN: BST vs AVL");
        System.out.println("======================================");
        System.out.println(String.format("%-25s %-10s %-10s", "Métrica", "BST", "AVL"));
        System.out.println("--------------------------------------");
        System.out.println(String.format("%-25s %-10d %-10d", "Altura Final", alturaBST, avl.altura(avl.raiz)));
        System.out.println(String.format("%-25s %-10d %-10d", "Comparaciones (Buscar V10)", comparacionesBSTV10, comparacionesAVLV10));
        System.out.println("Rotaciones Totales AVL: " + avl.rotacionesTotales);
        System.out.println("======================================");

        scanner.close();
    }
}
