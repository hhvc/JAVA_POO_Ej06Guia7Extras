package Entidad;

public class Ahorcado {
    private char[] palabraSecreta;
    private char[] ingresadas = {' '};
    private int qLetrasOK=0;
    private int intentosRestantes;

    public Ahorcado() {
    }

    public Ahorcado(char[] palabraSecreta, int intentosRestantes) {
        this.palabraSecreta = palabraSecreta;
        this.intentosRestantes = intentosRestantes;
    }

    public char[] getPalabraSecreta() {
        return palabraSecreta;
    }

    public void setPalabraSecreta(char[] palabraSecreta) {
        this.palabraSecreta = palabraSecreta;
    }

    public int getqLetrasOK() {
        return qLetrasOK;
    }

    public void setqLetrasOK(int qLetrasOK) {
        this.qLetrasOK = qLetrasOK;
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public void setIntentosRestantes(int intentosRestantes) {
        this.intentosRestantes = intentosRestantes;
    }

    public char[] getIngresadas() {
        return ingresadas;
    }

    public void setIngresadas(char[] ingresadas) {
        this.ingresadas = ingresadas;
    }

    
    
}
