public class Nodo {
    int valor;
    int altura;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int valor) {
        this.valor = valor;
        this.altura = 1; // Todo nodo nuevo se inserta como hoja (altura 1)
    }
}
