public class Hilo extends Thread{
    GestorBbdd gBbdd = new GestorBbdd();
    private int auxInicial;
    private int auxFinal;

    public Hilo(int auxInicial, int auxFinal) {
        this.auxInicial = auxInicial;
        this.auxFinal = auxFinal;
    }

    @Override
    public void run() {
        super.run();
        lecturaConHilos();
    }


    public synchronized void lecturaConHilos(){
        gBbdd.lecturaConcurrente(auxInicial, auxFinal);
    }
}
