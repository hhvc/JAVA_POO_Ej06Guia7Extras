package Servicios;

import Entidad.Ahorcado;
import java.util.Scanner;
import java.io.IOException;

public class AhorcadoService {
    Scanner leer = new Scanner(System.in);
    ProcessBuilder borrar = new ProcessBuilder("cmd", "/c", "cls"); //objeto servicio ProcessBuilder que usaré para borrar pantalla
    
    public Ahorcado crearJuego() throws IOException, InterruptedException{      
        Ahorcado nuevoJuego = new Ahorcado();
        System.out.println("Ingrese la palabra secreta a encontrar");
        String palabra = leer.next();
        nuevoJuego.setPalabraSecreta(palabra.toCharArray());
//        System.out.println("Ingrese la cantidad de intentos máximos para el jugador");
//        nuevoJuego.setIntentosRestantes(leer.nextInt());
        System.out.println("Ud. ha ingresado una palabra de " + palabra.length()+ " letras\n");
        char aux[]=new char[(palabra.length())*2];
        for (int i = 0; i < (palabra.length())*2; i++) {
            if (i%2>0) {
                aux[i]=' ';
            } else aux[i]='_';
        }
        nuevoJuego.setPalabraEncontrada(aux);
        graficaInicial();
        leer.next();
        return nuevoJuego;
    }
    
    public void longitud(Ahorcado j){
        System.out.println(j.getPalabraSecreta().length);
    }
    
    public void buscarLetra(Ahorcado j)throws IOException, InterruptedException{
        char letra = chequearRepetidas(j);
       
        boolean encontrada = this.encontradas(j, letra);
        if (encontrada) {
            System.out.println("Felicitaciones!!!");
        } else  System.out.println("Fallaste. Te quedan " + this.intentos(j) + " intentos");
        borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
        graficoVivo(j);
    }
    
    public boolean encontradas(Ahorcado j, char l){
            char aux[]= j.getPalabraEncontrada();
            boolean respuesta = false;
            for (int i = 0; i < j.getPalabraSecreta().length; i++) {
                if (Character.toUpperCase(j.getPalabraSecreta()[i]) == Character.toUpperCase(l)) {
                    aux[i*2] = l;
//                    System.out.println("Letra encontrada en posición "+(i+1));
                    j.setqLetrasOK(j.getqLetrasOK()+1);
                    respuesta = true;
                }
            }
            j.setPalabraEncontrada(aux);
            if (!respuesta) {
            j.setIntentosRestantes(j.getIntentosRestantes()-1);
        }
            return respuesta;
    }
 
    public int intentos(Ahorcado j){
        return j.getIntentosRestantes();
    }
    
    public char chequearRepetidas(Ahorcado j)throws IOException, InterruptedException{
        
        // investigar que sería más fácil usando: private Set<String> acertadas = new HashSet<>();

        boolean repetida;
        char letra;
        do {
            char[] aux = new char[j.getIngresadas().length+1];
            repetida = false;
            borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
            graficoVivo(j);
//            System.out.println("\nIngrese una letra");
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
    
    public void juego(Ahorcado j) throws IOException, InterruptedException{ // agrego dos excepciones para acceder a la consola con "throws" y poder borrarla
        

        borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
        
        do {
            this.buscarLetra(j);
        } while (this.intentos(j)>0 && j.getqLetrasOK() != j.getPalabraSecreta().length);
            borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
        graficoVivo(j);
        
        if (j.getqLetrasOK() == j.getPalabraSecreta().length) {
            graficaGanador(j);
    //        System.out.println("¡ ¡ ¡ G A N A S T E ! ! !\n\nFelicitaciones. Vivirás un día más");
        } else  {
            borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
//            System.out.println("Perdiste!!!! Hoy comerán los gusanos!!!!");
            graficoAhorcado();
        }
    }    
    
    public void graficoVivo(Ahorcado j) throws IOException, InterruptedException{
        borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
        
    System.out.println(" ___________.._______\n" +
"| .__________))______|\n" +
"| | / /      ||\n" +
"| |/ /       ||\n" +
"| | /        ||     \n" +
"| |/          @                             AHORCADO\n" +
"| |         // \\\\  \n" +
"| |        ((   ))                   Ingresa una letra:\n" +
"| |         \\\\ //  \n" +
"| |           ¯                   \n" +
"| |                    \n" +
"| |                                      "+String.valueOf(j.getPalabraEncontrada())+"\n" +
"| |                    \n" +
"| |               \n" + 
"| |                                Intentos restantes = "+j.getIntentosRestantes()+"\n" +
"| |               \n" +
"| |               \n" +
"| |               \n" +
"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"|\n" +
"|\"|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"|\"|\n" +
"| |                     | |\n" +
": :                     : :\n" +
". .                     . .");
    }
    
    public void graficoAhorcado(){
        System.out.println(" ___________.._______\n" +
"| .__________))______|\n" +
"| | / /      ||\n" +
"| |/ /       ||\n" +
"| | /        ||.-''.\n" +
"| |/         |/  _  \\                      AHORCADO\n" +
"| |          ||  `/,|\n" +
"| |          (\\\\`_.'                ----  PERDISTE  ----\n" +
"| |         .-`--'.\n" +
"| |        /Y . . Y\\             \n" +
"| |       // |   | \\\\\n" +
"| |      //  | . |  \\\\      Te esperan los gusanos para compartir la cena...\n" +
"| |     ')   |   |   (`\n" +
"| |          ||'||\n" +
"| |          || ||\n" +
"| |          || ||\n" +
"| |          || ||\n" +
"| |         / | | \\\n" +
"\"\"\"\"\"\"\"\"\"\"|_`-' `-' |\"\"\"\"\"\"\"\"\"\"\"|\n" +
"|\"|\"\"\"\"\"\"\"\\ \\       '\"\"\"\"\"\"\"\"\"|\"|\n" +
"| |        \\ \\                        | |\n" +
": :         \\ \\                       : :  \n" +
". .          `'                       . .");
    }
 


public void graficaInicial() throws IOException, InterruptedException{
    borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
    
    System.out.println(" ___________.._______\n" +
"\"| .__________))______|\n" +
"\"| | / /      ||\n" +
"\"| |/ /       ||\n" +
"\"| | /        ||      .'''-\n" +
"\"| |/          @     / _  _\\                  AHORCADO\n" +
"\"| |         // \\\\   | 0  0|\n" +
"\"| |        ((   ))  |   ¬ |\n" +
"\"| |         \\\\ //    \\ ~~ /     Un juego que no requiere instrucciones\n" +
"\"| |           ¯     .-`--'.\n" +
"\"| |                /Y . . Y\\        Y vos, ¿podrás sobrevivir?\n" +
"\"| |               // |   | \\\\\n" +
"\"| |              //  | . |  \\\\       Escribe algo para empezar\n" +
"\"| |             ')   |   |   (`\n" +
"\"| |                  ||'||\n" +
"\"| |                  || ||\n" +
"\"| |                  || ||\n" +
"\"| |                  || ||\n" +
"\"| |                 / | | \\\n" +
"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"|\n" +
"\"|\"|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"|\"|\n" +
"\"| |                 | |\n" +
"\": :                 : :\n" +
"\". .                 . .");
     
}

public void graficaGanador(Ahorcado j) throws IOException, InterruptedException{
    borrar.inheritIO().start().waitFor(); // línea para borrar pantalla
    
    System.out.println("____________________██████\n" +
"_________▓▓▓▓____█████████\n" +
"__     ▓▓▓▓▓=▓____▓=▓▓▓▓▓\n" +
"__ ▓▓▓_▓▓▓▓░O____O░░▓▓▓▓\n" +
"_▓▓▓▓_▓▓▓▓▓░░__░░░░▓▓▓▓\n" +
"_ ▓▓▓▓_▓▓▓▓░░@__@░░░▓▓▓                       AHORCADO\n" +
"__ ▓▓▓___▓▓░░_____░░░▓▓\n" +
"▓▓▓▓▓____▓░░_____░░▓\n" +
"_ ▓▓____ ▒▓▒▓▒___ ████\n" +
"_______ ▒▓▒▓▒▓▒_ ██████                 "+String.valueOf(j.getPalabraEncontrada())+"\n" +
"_______▒▓▒▓▒▓▒ ████████\n" +
"_____ ▒▓▒▓▒▓▒_██████ ███        ╔═════════════════════════╗\n" +
"_ ___▒▓▒▓▒▓▒__██████ _███       ║ GANASTE!, Vivirás más...║\n" +
"_▓▓@▓▓▓▓▓▓▓__██████_ ███        ╚═════════════════════════╝\n" +
"▓▓_██████▓▓__██████_ ███\n" +
"▓_███████▓▓__██████_ ███\n" +
"_████████▓▓__██████ _███\n" +
"_████████▓▓__▓▓▓▓▓▓_▒▒\n" +
"_████████▓▓__▓▓▓▓▓▓\n" +
"_████████▓▓__▓▓▓▓▓▓\n" +
"__████████▓___▓▓▓▓▓▓\n" +
"_______▒▒▒▒▒____▓▓▓▓▓▓\n" +
"_______▒▒▒▒▒ _____▓▓▓▓▓\n" +
"_______▒▒▒▒▒_____ ▓▓▓▓▓\n" +
"_______▒▒▒▒▒ _____▓▓▓▓▓\n" +
"________▒▒▒▒______▓▓▓▓▓\n" +
"________█████____█████\n" +
"_'▀█║────────────▄▄───────────​─▄──▄_\n" +
"──█║───────▄─▄─█▄▄█║──────▄▄──​█║─█║\n" +
"──█║───▄▄──█║█║█║─▄║▄──▄║█║─█║​█║▄█║\n" +
"──█║──█║─█║█║█║─▀▀──█║─█║█║─█║​─▀─▀\n" +
"──█║▄║█║─█║─▀───────█║▄█║─▀▀\n" +
"──▀▀▀──▀▀────────────▀─█║\n" +
"───────▄▄─▄▄▀▀▄▀▀▄──▀▄▄▀\n" +
"──────███████───▄▀\n" +
"──────▀█████▀▀▄▀\n" +
"────────▀█▀");
    
    
    
    
    
//    System.out.println(" ___________.._______\n" +
//"| .__________))______|\n" +
//"| | / /      ||\n" +
//"| |/ /       ||\n" +
//"| | /        ||.-''.\n" +
//"| |/         |/  _  \\                      AHORCADO\n" +
//"| |          ||  `/,|\n" +
//"| |          (\\\\`_.'        Un juego que no requiere instrucciones\n" +
//"| |         .-`--'.\n" +
//"| |        /Y . . Y\\             Y vos, ¿podrás sobrevivir?\n" +
//"| |       // |   | \\\\\n" +
//"| |      //  | . |  \\\\        PRESIONA UNA TECLA PARA EMPEZAR\n" +
//"| |     ')   |   |   (`\n" +
//"| |          ||'||\n" +
//"| |          || ||\n" +
//"| |          || ||\n" +
//"| |          || ||\n" +
//"| |         / | | \\\n" +
//"\"\"\"\"\"\"\"\"\"\"|_`-' `-' |\"\"\"|\n" +
//"|\"|\"\"\"\"\"\"\"\\ \\       '\"|\"|\n" +
//"| |        \\ \\        | |\n" +
//": :         \\ \\       : :  \n" +
//". .          `'       . .");
}


}