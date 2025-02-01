
package dominio;
//Mariano Perez, Numero°307265
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Juego {
    private  ArrayList<Tablero> listaTableros;
    private  ArrayList<Movimiento> listaMovimientos;
    private  ArrayList<Movimiento> listaTodosMovimientos;
    
    private Instant comienzo;
    private Instant fin;
    
    public Juego() {
        listaTableros = new ArrayList<>();
        listaMovimientos = new ArrayList<>();
        listaTodosMovimientos = new ArrayList<>();
    }
    
    public  ArrayList<Tablero> getListaTableros() {
        return listaTableros;
    }

    public  ArrayList<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }
    
    public ArrayList<Movimiento> getListaTodosMovimientos() {
        return listaTodosMovimientos;
    }

    
    public void setComienzo(Instant comienzo) {
        this.comienzo = comienzo;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Instant getComienzo() {
        return comienzo;
    }

    public Instant getFin() {
        return fin;
    }
    
    //inicio desde el achivo
    public  void InicioDeJuegoDesdeArchivo(Scanner input){
        Tablero tablero1 = new Tablero(input);
        int nivel = input.nextInt();
        int contN = 0;
        while(!(nivel == contN)){
            int fila = input.nextInt();
            int col = input.nextInt();
            ingresoDeMovimientoSoluciones(fila,col);
            contN++;
        }
        comienzoTiempo();
        agregarTableroModificado(tablero1);
    }
    //inicio juego Predefinido
    public void inicioDeJuegoPredefinido(){
        comienzoTiempo();
        Tablero tablero1 = new Tablero();
        agregarTableroModificado(tablero1);
        ingresoDeMovimientoSoluciones(4,4);
        ingresoDeMovimientoSoluciones(5,6);
        ingresoDeMovimientoSoluciones(5,4);
   }
    
    //inicia juego al azar
    public  void inicioDeJuegoAzar(int fila, int columna, int nivel){
        int contN = 0;
        Tablero tablero1 = new Tablero(fila, columna);
        while(!(nivel == contN)){    
            Random random = new Random();
            int numeroF = random.nextInt(fila); 
            int numeroC = random.nextInt(columna);
            Movimiento existeEnLista = new Movimiento(numeroF+1, numeroC+1);

            //evaluo si exite ya este movimiento para que no lo vuelva hacer
            while(verificaMovSolucion(existeEnLista)){
                numeroF = random.nextInt(fila);
                numeroC = random.nextInt(columna);
                existeEnLista = new Movimiento(numeroF+1, numeroC+1);
            }
            tablero1 = dependeSimbolo(tablero1,numeroF, numeroC);
            ingresoDeMovimientoSoluciones(numeroF+1, numeroC+1);   
            contN++;
        }
        comienzoTiempo();
        agregarTableroModificado(tablero1);
        
    }
    //verifica movimientos para el nivel
    private boolean verificaMovSolucion(Movimiento m){
        return getListaTodosMovimientos().contains(m);
    }
        //ingresa movimiento de soluciones
    private void ingresoDeMovimientoSoluciones(int fila, int columna){    
        Movimiento movi = new Movimiento(fila, columna);
        this.getListaTodosMovimientos().add(movi);   
    }
    //comienza tiempoy agrega
    private  void comienzoTiempo(){
       setComienzo(Instant.now());
    }
        //agrega el tablero modificado a la lista
    private void agregarTableroModificado(Tablero tablero1){
        getListaTableros().add(tablero1);
    }

    public ArrayList<Movimiento> devolucionSolucion(){
       
        ArrayList<Movimiento> movimientosMostrados = new ArrayList<>();

        for (int i = getListaTodosMovimientos().size()-1; i >=0 ; i--) {
            Movimiento movi = getListaTodosMovimientos().get(i);
            int cont = 0;
            for (int j = getListaTodosMovimientos().size()-1; j >=0 ; j--) {
                if (getListaTodosMovimientos().get(j).equals(movi)) {
                    cont++;
                }
            }
            if (cont % 2 != 0 && !movimientosMostrados.contains(movi)) {   
                movimientosMostrados.add(movi);    
            } 
        }
        return movimientosMostrados;
    }
    // ingresa movimientos de usuario
    private void ingresoDeMovimiento(int fila, int columna){
        Movimiento movi = new Movimiento(fila+1, columna+1);
        getListaMovimientos().add(movi);
        ingresoDeMovimientoSoluciones(fila+1,columna+1);
        
    }    
    // crea copia del tablero actual
    private Tablero crearCopiaTablero(Tablero tablero2){
        Tablero tablero1 = new Tablero(tablero2);
        return tablero1;
    }
    //valida si el juego esta terminado
    public  boolean juegoTerminado(String letra){
        Tablero tableroTerm = getListaTableros().get(getListaTableros().size()-1);
        boolean terminado = true;
        if(letra.equalsIgnoreCase("X")){
            terminaTiempo(terminado);
        }else{
            int contR = 0;
            int contA = 0;
            for(int i=0; i< tableroTerm.getCombinacionTablero().length; i++){
                for(int j=0; j< tableroTerm.getCombinacionTablero()[i].length; j++){
                    if ("R".equals(tableroTerm.getCombinacionTablero()[i][j].substring(1))) {
                        contR++;
                    } else if ("A".equals(tableroTerm.getCombinacionTablero()[i][j].substring(1))) {
                        contA++;
                    }
                }
            }
            if(contR>0 && contA>0){
                terminado = false;
            }
        }
        terminaTiempo(terminado);
        return terminado;
    }
    //Finaliza el tiempo
    private void terminaTiempo(boolean termino){
        if(termino){
            setFin(Instant.now());
        }    
    }
    //Limpia listas
    public void limpiadorListas(){
            getListaMovimientos().clear();
            getListaTodosMovimientos().clear();
            getListaTableros().clear();
    }
    //devuelve el simbolo a cambiar
    private char simboloACambiar(Tablero tablero1, int posFil, int posCol){
        return tablero1.getCombinacionTablero()[posFil][posCol].charAt(0);
    }
    //Comienzo de cambio de color
    public void comienzoCambioColor(int fila, int colum){
        Tablero tableroMod = dependeSimbolo(getListaTableros().get(getListaTableros().size()-1),fila,colum);
        agregarTableroModificado(tableroMod);
        ingresoDeMovimiento(fila, colum);
    }

    //mira que simbolo es para asignarle el cambio que tiene que hacer
    private  Tablero dependeSimbolo(Tablero tablero1,int posFil, int posCol){   
        Tablero tablero2 = crearCopiaTablero(tablero1);
        char simbolo =  simboloACambiar(tablero2,posFil,posCol);
        tablero2.cambioColor(simbolo, posFil, posCol);
        return tablero2;
    }
    
    public void retrocedeMovimiento(){
        if(getListaTableros().size()>1){
            getListaTableros().remove(getListaTableros().size()-1);
            getListaMovimientos().remove(getListaMovimientos().size()-1);
            getListaTodosMovimientos().remove(getListaTodosMovimientos().size()-1);
        }else{
            System.out.println("No se puede retroceder mas estas en el primer tablero");
        }
    }
    
    @Override
    public String toString(){
        
        Duration duration = Duration.between(getComienzo(), getFin());
        long segundos = duration.getSeconds();
        long minutos = segundos / 60;
        segundos %= 60;
        long horas = minutos / 60;
        minutos %= 60;
        long dias = horas / 24;
        horas %= 24;

        return String.format("%d días, %02d:%02d:%02d", dias, horas, minutos, segundos);    
    }
}  
