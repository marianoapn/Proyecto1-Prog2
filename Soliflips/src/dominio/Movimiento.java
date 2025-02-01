
package dominio;
//Mariano Perez, Numero°307265
public class Movimiento {
    private int fila;
    private int columna;

    public Movimiento(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "(" + fila + "," + columna + ')';
    }
    
    @Override
    public boolean equals(Object o){
        Movimiento m = (Movimiento)o;
        return this.columna == m.columna && this.fila == m.fila;
    }
    
}
