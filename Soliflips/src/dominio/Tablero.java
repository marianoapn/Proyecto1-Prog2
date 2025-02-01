
package dominio;
//Mariano Perez, Numero°307265
import java.util.Random;
import java.util.Scanner;

public class Tablero{
    private String[][] combinacionTablero;
    private static String[] Simbolos = {"/","-","|","\\"} ;
    private static String[] Letras ={"R", "A"};
     
    
    public Tablero(){
        cargarCombinacionPredeterminada();
        
    }
    
    public Tablero(int filas, int columnas){
        crearCombinacionTableroAzar(filas, columnas);
    }
    
    public Tablero(Scanner input){
        cargarCombinacionDesdeArchivoTxt(input);
    }
    
    public Tablero(Tablero tablero) {
        this.combinacionTablero = new String[tablero.combinacionTablero.length][tablero.combinacionTablero[0].length];
        for (int i = 0; i < tablero.combinacionTablero.length; i++) {
            for (int j = 0; j < tablero.combinacionTablero[0].length; j++) {
                this.combinacionTablero[i][j] = tablero.combinacionTablero[i][j];
            }
        }
    }
    
    public String[][] getCombinacionTablero() {
        return combinacionTablero;
    }
    // Crea el Tablero al azar
    private void crearCombinacionTableroAzar(int fila, int columna) {
        String[][] combinacionSimboloColor = new String[fila][columna];
        Random random = new Random();
        int indiceLetraAzar = random.nextInt(Letras.length);
        for (int i = 0; i < combinacionSimboloColor.length; i++) {
            for (int j = 0; j < combinacionSimboloColor[0].length; j++) {    
                int indiceSimboloAzar = random.nextInt(Simbolos.length);
                combinacionSimboloColor[i][j] = Simbolos[indiceSimboloAzar] + Letras[indiceLetraAzar];
            }
        }
        this.combinacionTablero = combinacionSimboloColor;
    }
    //carga la combinacion del tablero del archivo
    private void cargarCombinacionDesdeArchivoTxt(Scanner input){
        
            int filas = input.nextInt();
            int columnas = input.nextInt();
            input.nextLine(); 

            String[][] combinacionSimboloColor = new String[filas][columnas];
            for (int i = 0; i < combinacionSimboloColor.length; i++) {
                String[] fila = input.nextLine().split(" ");
                for (int j = 0; j < combinacionSimboloColor[0].length; j++) {
                    combinacionSimboloColor[i][j] = fila[j];
                }
            }
            this.combinacionTablero = combinacionSimboloColor;
    }
    // Carga la combinacion ya predeterminada
    private void cargarCombinacionPredeterminada(){
        String[][] combinacionSimboloColor = {{"|A" ,"|A", "-R", "/A", "|R","-R"},
                                                {"-R", "/A", "/A", "|A", "-R", "-R"},
                                                {"-R", "-R", "|A", "-R", "/R", "-R"},
                                                {"\\R","-R","|R", "\\R", "|A", "|R"},
                                                {"\\R", "/R", "/R", "|A", "/A", "\\A"}
                                            };
         this.combinacionTablero = combinacionSimboloColor;
    }
    //cambia de color tablero depende simbolo que ingrese
    public String[][] cambioColor(char simbolo ,int posFil, int posCol){
        String[][] mat = this.getCombinacionTablero();       
        if(simbolo == '-'){
            //cambia la fila -
            int i = posFil;
            int j = 0;
            while(j<mat[0].length){
                if('R' == mat[i][j].charAt(1)){
                    mat[i][j] = mat[i][j].charAt(0) + "A";
                } else {
                    mat[i][j] = mat[i][j].charAt(0) + "R";
                }
                j++;
            }
        }else if(simbolo == '|') {
        //cambia la columna
            int i = 0;
            int j = posCol;
            while(i<mat.length){
                if('R' == mat[i][j].charAt(1)){
                    mat[i][j] = mat[i][j].charAt(0) + "A";
                } else {
                    mat[i][j] = mat[i][j].charAt(0) + "R";
                }
                i++;
            }   
        }else if(simbolo == '\\'){
            //Cambia la diagonal \
            //diagonal hacia abajo
            int i = posFil-1;
            int j = posCol-1;
            while(i >= 0 && j >=0){
                if('R' == mat[i][j].charAt(1)){
                    mat[i][j] = mat[i][j].charAt(0) + "A";
                } else {
                    mat[i][j] = mat[i][j].charAt(0) + "R";
                }
                i--;
                j--;
            }
            i = posFil;
            j = posCol;
            //diagonalhacia arriba
            while(i< mat.length && j < mat[0].length){
                if('R' == mat[i][j].charAt(1)){
                    mat[i][j] = mat[i][j].charAt(0) + "A";
                } else {
                    mat[i][j] = mat[i][j].charAt(0) + "R";
                }
                i++;
                j++;
            }
        }else if(simbolo == '/'){
            // cambia la diagonal /
            //diagonal hacia arriba
            int i = posFil-1;
            int j = posCol+1;
            while(i>=0 && i<mat.length && j>=0 && j<mat[0].length ){
                if('R' == mat[i][j].charAt(1)){
                    mat[i][j] = mat[i][j].charAt(0) + "A";
                } else {
                    mat[i][j] = mat[i][j].charAt(0) + "R";
                }
                i--;
                j++;
            } 
            //diagonal hacia abajo
            i = posFil;
            j = posCol;
            while(i >=0 &&i<mat.length && j >= 0){
               if('R' == mat[i][j].charAt(1)){
                   mat[i][j] = mat[i][j].charAt(0) + "A";
               } else {
                   mat[i][j] = mat[i][j].charAt(0) + "R";
               }
               i++;
               j--;
           }

        }
        return mat;
    }
    
    @Override
    public String toString() {
        String azul = "\u001B[34m";
        String rojo = "\u001B[31m";
        String resetColor = "\u001B[0m";
        int numCols = getCombinacionTablero()[0].length;

        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int col = 1; col <= numCols; col++) {
            sb.append(String.format("%2d", col));
            sb.append("  ");
        }
        sb.append("  \n");
        sb.append("   +");
        for (int col = 0; col < numCols; col++) {
            sb.append("---+");
        }
        sb.append("\n");

        for (int fil = 0; fil < getCombinacionTablero().length; fil++) {
            sb.append(String.format("%2d", fil + 1));
            sb.append(" |");
            for (int col = 0; col < numCols; col++) {
                sb.append(" ");
                if(getCombinacionTablero()[fil][col].charAt(1) == 'R'){
                    sb.append(rojo+ getCombinacionTablero()[fil][col].charAt(0)+resetColor);
                }else{
                    sb.append(azul+ getCombinacionTablero()[fil][col].charAt(0)+resetColor);
                }
                
                sb.append(" |");
            }
            sb.append("\n");
            sb.append("   +");
            for (int col = 0; col < numCols; col++) {
                sb.append("---+");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
