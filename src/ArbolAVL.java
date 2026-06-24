public class ArbolAVL {
    Nodo raiz;
    public int rotacionesTotales = 0;
    public int comparacionesBusqueda = 0;

    int altura(Nodo N) {
        if (N == null) return 0;
        return N.altura;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    int obtenerFactorEquilibrio(Nodo N) {
        if (N == null) return 0;
        return altura(N.izquierdo) - altura(N.derecho);
    }

    Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        Nodo T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;

        rotacionesTotales++;
        return x;
    }

    Nodo rotacionIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;

        rotacionesTotales++;
        return y;
    }

    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);

        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        } else {
            return nodo;
        }

        nodo.altura = 1 + max(altura(nodo.izquierdo), altura(nodo.derecho));
        int balance = obtenerFactorEquilibrio(nodo);

        if (balance > 1 && valor < nodo.izquierdo.valor) {
            System.out.println(" [! Rotación LL aplicada al nodo " + nodo.valor + "]");
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && valor > nodo.derecho.valor) {
            System.out.println(" [! Rotación RR aplicada al nodo " + nodo.valor + "]");
            return rotacionIzquierda(nodo);
        }
        if (balance > 1 && valor > nodo.izquierdo.valor) {
            System.out.println(" [! Rotación LR aplicada al nodo " + nodo.valor + "]");
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && valor < nodo.derecho.valor) {
            System.out.println(" [! Rotación RL aplicada al nodo " + nodo.valor + "]");
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    public void eliminar(int valor) {
        raiz = eliminarRecursivo(raiz, valor);
    }

    private Nodo eliminarRecursivo(Nodo raiz, int valor) {
        if (raiz == null) return raiz;

        if (valor < raiz.valor) {
            raiz.izquierdo = eliminarRecursivo(raiz.izquierdo, valor);
        } else if (valor > raiz.valor) {
            raiz.derecho = eliminarRecursivo(raiz.derecho, valor);
        } else {
            if ((raiz.izquierdo == null) || (raiz.derecho == null)) {
                Nodo temp = null;
                if (temp == raiz.izquierdo) temp = raiz.derecho;
                else temp = raiz.izquierdo;

                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else {
                    raiz = temp;
                }
            } else {
                Nodo temp = nodoMinimo(raiz.derecho);
                raiz.valor = temp.valor;
                raiz.derecho = eliminarRecursivo(raiz.derecho, temp.valor);
            }
        }

        if (raiz == null) return raiz;

        raiz.altura = max(altura(raiz.izquierdo), altura(raiz.derecho)) + 1;
        int balance = obtenerFactorEquilibrio(raiz);

        if (balance > 1 && obtenerFactorEquilibrio(raiz.izquierdo) >= 0) {
            System.out.println(" [! Rotación LL por eliminación en el nodo " + raiz.valor + "]");
            return rotacionDerecha(raiz);
        }
        if (balance > 1 && obtenerFactorEquilibrio(raiz.izquierdo) < 0) {
            System.out.println(" [! Rotación LR por eliminación en el nodo " + raiz.valor + "]");
            raiz.izquierdo = rotacionIzquierda(raiz.izquierdo);
            return rotacionDerecha(raiz);
        }
        if (balance < -1 && obtenerFactorEquilibrio(raiz.derecho) <= 0) {
            System.out.println(" [! Rotación RR por eliminación en el nodo " + raiz.valor + "]");
            return rotacionIzquierda(raiz);
        }
        if (balance < -1 && obtenerFactorEquilibrio(raiz.derecho) > 0) {
            System.out.println(" [! Rotación RL por eliminación en el nodo " + raiz.valor + "]");
            raiz.derecho = rotacionDerecha(raiz.derecho);
            return rotacionIzquierda(raiz);
        }

        return raiz;
    }

    private Nodo nodoMinimo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    public boolean buscar(int valor) {
        comparacionesBusqueda = 0;
        System.out.print("Ruta visitada: ");
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo actual, int valor) {
        if (actual == null) {
            System.out.print("Nulo ");
            return false;
        }

        comparacionesBusqueda++;
        System.out.print(actual.valor + " -> ");

        if (actual.valor == valor) return true;

        if (valor < actual.valor) {
            return buscarRecursivo(actual.izquierdo, valor);
        }
        return buscarRecursivo(actual.derecho, valor);
    }

    public void imprimirInorden(Nodo nodo) {
        if (nodo != null) {
            imprimirInorden(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            imprimirInorden(nodo.derecho);
        }
    }

    public void imprimirPreorden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            imprimirPreorden(nodo.izquierdo);
            imprimirPreorden(nodo.derecho);
        }
    }

    public void imprimirPostorden(Nodo nodo) {
        if (nodo != null) {
            imprimirPostorden(nodo.izquierdo);
            imprimirPostorden(nodo.derecho);
            System.out.print(nodo.valor + " ");
        }
    }
}
