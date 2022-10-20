package Servicios;

import Entidad.Ahorcado;
import java.util.Scanner;

public class AhorcadoService {
    Scanner leer = new Scanner(System.in);
    
    public Ahorcado crearJuego(){      
        Ahorcado nuevoJuego = new Ahorcado();
        System.out.println("Ingrese la palabra secreta a encontrar");
        String palabra = leer.next();
        nuevoJuego.setPalabraSecreta(palabra.toCharArray());
        System.out.println("Ingrese la cantidad de intentos máximos para el jugador");
        nuevoJuego.setIntentosRestantes(leer.nextInt());
        System.out.println("Ud. ha ingresado una palabra de " + palabra.length()+ " letras\n");
        return nuevoJuego;
    }
    
    public void longitud(Ahorcado j){
        System.out.println(j.getPalabraSecreta().length);
    }
    
    public void buscarLetra(Ahorcado j){
        char letra = chequearRepetidas(j);
       
        boolean encontrada = this.encontradas(j, letra);
        if (encontrada) {
            System.out.println("Felicitaciones!!!");
        } else  System.out.println("Fallaste. Te quedan " + this.intentos(j) + " intentos");
    }
    
    public boolean encontradas(Ahorcado j, char l){
            boolean respuesta = false;
            for (int i = 0; i < j.getPalabraSecreta().length; i++) {
                if (Character.toUpperCase(j.getPalabraSecreta()[i]) == Character.toUpperCase(l)) {
                    System.out.println("Letra encontrada en posición "+(i+1));
                    j.setqLetrasOK(j.getqLetrasOK()+1);
                    respuesta = true;
                }
            }
            if (!respuesta) {
            j.setIntentosRestantes(j.getIntentosRestantes()-1);
        }
            return respuesta;
    }
 
    public int intentos(Ahorcado j){
        return j.getIntentosRestantes();
    }
    
    public char chequearRepetidas(Ahorcado j){
        boolean repetida;
        char letra;
        do {
            char[] aux = new char[j.getIngresadas().length+1];
            repetida = false;
            System.out.println("\nIngrese una letra");
            letra = leer.next().charAt(0);
            for (int i = 0; i < j.getIngresadas().length; i++) {
                if (Character.toUpperCase(j.getIngresadas()[i])==Character.toUpperCase(letra)) {
                    repetida = true;
                }
            }
            if (!repetida) {
                System.arraycopy(j.getIngresadas(), 0, aux, 0, j.getIngresadas().length);
                aux[aux.length-1]=letra;
                j.setIngresadas(aux);
            } else System.out.println("Ya probaste con esa letra, picarón.... ¡Elegí otra!");
            System.out.print("\nLetras ingresadas: ");
            for (int i = 0; i < j.getIngresadas().length; i++) {
                System.out.print(j.getIngresadas()[i]+",");
            }
            System.out.println("");

        } while (repetida);
        return letra;
    }
    
    public void juego(Ahorcado j){
        do {
            this.buscarLetra(j);
        } while (this.intentos(j)>0 && j.getqLetrasOK() != j.getPalabraSecreta().length);
        
        
        if (j.getqLetrasOK() == j.getPalabraSecreta().length) {
            System.out.println("Felicitaciones. Vivirás un día más");
        } else  System.out.println("Perdiste!!!! Hoy comerán los gusanos!!!!");
    }    
}
