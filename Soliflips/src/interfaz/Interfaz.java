
package interfaz;
//Mariano Perez, Numero°307265
import dominio.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Interfaz {

    private final Juego juego;
    private Scanner input = new Scanner(System.in);
    private int cont;
    
    public Interfaz(){
        juego = new Juego();
        cont = 0;
    }
    
    
    public void inicioMenu(){
        if(deseaJugar()){
            do{
                elegirOpcion();
                juego.limpiadorListas();
            }while(deseaJugar());
        }
    }
    public boolean deseaJugar(){
        boolean jugar = false;
        System.out.println("");
        if(cont == 0){
            System.out.println("");
            System.out.println("Si desea jugar ingrese Si");
            cont++;
        }else{
            System.out.println("");
            System.out.println("Si desea volver a jugar ingrese Si.");
        }
        String respuesta = input.nextLine();
        if(respuesta.equalsIgnoreCase("si")){
            jugar = true;
        }
        return jugar;
        
    }

    
    public  void elegirOpcion(){
        System.out.println("a) Tomar datos del archivo “datos.txt”");
        System.out.println("b) Usar el tablero predefinido");
        System.out.println("c) Usar un tablero al azar");
        String opcion = input.nextLine();
        switch(opcion.toLowerCase()){
            case "a" -> {
                ingresoDeArchivoTxt();
                
            }
            case "b" -> {
                juegoPredeterminado();
                
            }
            case "c" -> {
                ingresoFilaColumnaNivelTableroAzar();
                
            }
            default -> {
                System.out.println("Error de opcion ingrese nuevamente a, b o c");
                elegirOpcion();
            }
        }
    }
    public  void ingresoDeArchivoTxt(){
        try { 
            Scanner input = new Scanner(new File(".\\Test\\datos.txt"));
            juego.InicioDeJuegoDesdeArchivo(input);
            //comienza juego ingresando movimientos o letra
              ingresoCambioOAcciones();
        } catch (FileNotFoundException e) {
            System.out.println("Error el archivo no existe, seleccione otra opcion para poder empezar a jugar");
            elegirOpcion();
        }
    }
    //inicia el juegoPredeterminado
    private void juegoPredeterminado() {
        juego.inicioDeJuegoPredefinido();
        ingresoCambioOAcciones();
    }

    public void ingresoFilaColumnaNivelTableroAzar() {
        
        System.out.println("Indique el tamaño del tablero (fila columna nivel): ");
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.println("Ingrese Filas de 3 a 9");
                int fila = Integer.parseInt(input.nextLine());

                System.out.println("Ingrese Columnas de 3 a 9");
                int columna = Integer.parseInt(input.nextLine());

                System.out.println("Ingrese Nivel de 1 a 8");
                int nivel = Integer.parseInt(input.nextLine());

                if (fila >= 3 && fila <= 9 && columna >= 3 && columna <= 9 && nivel >= 1 && nivel <= 8) {
                    juego.inicioDeJuegoAzar(fila, columna, nivel);
                    entradaValida = true;
                    ingresoCambioOAcciones();
                    
                } else {
                    System.out.println("Error: filas y columnas deben estar entre 3 y 9, y el nivel entre 1 y 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingreso no es un número válido. Inténtelo nuevamente.");
            }
        }
    }
    //ingreso de movimientos o x h s;
    public  void ingresoCambioOAcciones(){
        String datos ="";
        mostrarTableros();
        while(!juego.juegoTerminado(datos)){
            boolean validar =false;  
            while(!validar){
                System.out.println("Ingrese una fila y luego en la proxima linea una columna o (-1 -1) o S o H o X");
                    datos = input.nextLine();
                    if(datos.equalsIgnoreCase("S")){
                        mostrarMovimientosDeSolucion();
                        validar = true;
                    }else if(datos.equalsIgnoreCase("H")){
                        mostrarMovimientosHechos();
                        validar = true;
                    }else if(datos.equalsIgnoreCase("X")){
                        juego.juegoTerminado(datos);
                        validar = true;
                    }else{
                        try {
                            int fila = Integer.parseInt(datos)-1;
                            System.out.println("Ingrese una columna");
                            
                            int colum =Integer.parseInt(input.nextLine())-1;
                            if(fila == -2 && colum == -2){
                                 juego.retrocedeMovimiento();
                                 mostrarTableroSiRetrocedo();
                                 validar =true;
                            }else{
                                int largoFil =juego.getListaTableros().get(juego.getListaTableros().size()-1).getCombinacionTablero().length;
                                int largoCol =juego.getListaTableros().get(juego.getListaTableros().size()-1).getCombinacionTablero()[0].length;
                                while(!(fila<largoFil && fila>= 0 && colum<largoCol && colum>= 0)){
                                    System.out.println("Error, la fila o la columna exede los limites del tablero, intente nuevamente");
                                    System.out.println("Ingrese una Fila");
                                    fila = Integer.parseInt(input.nextLine())-1;
                                    System.out.println("Ingrese una columna");
                                    colum = Integer.parseInt(input.nextLine())-1;  
                                }
                                juego.comienzoCambioColor(fila, colum);
                                validar =true;
                                mostrarTableros();
                            }
                           
                        } catch (NumberFormatException e) {
                            System.out.println("Error en ingreso de comandos, Intentelo nuevamente");
                            System.out.println("");
                           
                        }
                    }
            }
        }
        mostrarTiempo();
    }
    public void mostrarMovimientosHechos(){
        int cont = 0;
        if(juego.getListaMovimientos().size()>0){
            System.out.print("Movimientos Hechos: ");
            for(int i =juego.getListaMovimientos().size()-1;i>=0; i--){
                if(cont == 0){
                    System.out.print(juego.getListaMovimientos().get(i));
                    cont++;
                }else{
                    System.out.print(","+juego.getListaMovimientos().get(i));
                    cont++;
                }
            }
            System.out.println(".");
            System.out.println("");
        }else{
            System.out.println("No has realizado ningun movimiento.");
            System.out.println("");
        }
    }
    //muestra la solucion
    public void mostrarMovimientosDeSolucion(){    
        int cont = 0;
            System.out.print("Este juego se resuelve aplicando los movimientos: ");
            for(int i = 0 ;  i < juego.devolucionSolucion().size(); i++){
                if(cont == 0){
                    System.out.print(juego.devolucionSolucion().get(i));
                    cont++;
                }else{
                    System.out.print(","+juego.devolucionSolucion().get(i));
                    cont++;
                }
            }
            System.out.println(".");
            System.out.println("");
    
    }
    //muestra tiempo
    public void mostrarTiempo(){
        System.out.println("El tiempo de partida fue: " + juego.toString());
    }
    public void mostrarTableroSiRetrocedo(){
        String tableroActual =juego.getListaTableros().get(juego.getListaTableros().size()-1).toString();
        System.out.println(tableroActual);
    }  
    public void mostrarTableros(){
        String tableroActual =juego.getListaTableros().get(juego.getListaTableros().size()-1).toString();
        if(juego.getListaTableros().size() > 1){
            String tableroAnterior =juego.getListaTableros().get(juego.getListaTableros().size()-2).toString();
        
            String[] lineasAnterior = tableroAnterior.split("\n");
            String[] lineasActual = tableroActual.split("\n");

            StringBuilder tablerosCombinados = new StringBuilder();

            for (int i = 0; i < lineasAnterior.length; i++) {
                tablerosCombinados.append(lineasAnterior[i]);
                if(i>0){
                    tablerosCombinados.append("  ==> ");
                }else{
                    tablerosCombinados.append("    ");
                }
                tablerosCombinados.append(lineasActual[i]);
                tablerosCombinados.append("\n");
            }
            System.out.println(tablerosCombinados);     
        }else{
            System.out.println(tableroActual);
        }
    }
}
