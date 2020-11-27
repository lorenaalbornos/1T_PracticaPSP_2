import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Ejecutador {

    static final int N_HILOS = 5;

    public static void main(String[] args){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        do{
            menu();
            opcion = teclado.nextInt();
            switch (opcion){
                case 1:
                    lecturaSecuencial();
                    break;
                case 2:
                    lecturaConcurrente();
                    break;
                case 0:
                    System.out.println("HA SALIDO");
                    break;
            }
        }while (opcion != 0);
    }

    public static void menu(){
        System.out.println("1. Leer registros de forma secuencial");
        System.out.println("2. Leer registros de forma concurrente con 5 hilos");
        System.out.println("0. Salir");
        System.out.print("Introduzca opción: ");
    }

    public static void lecturaSecuencial(){
        GestorBbdd gBbdd = new GestorBbdd();
        long iLecturaSecuencial, fLecuraSecuencial;
        iLecturaSecuencial = System.currentTimeMillis();
        gBbdd.lectura();
        fLecuraSecuencial = System.currentTimeMillis();
        System.out.println("La suma de los ingresos es de: " + gBbdd.getSumaIngresos());
        System.out.println("El tiempo en leer los registros de forma secuencial es de: " + (fLecuraSecuencial - iLecturaSecuencial) + " milisegundos.");
    }

    public static void lecturaConcurrente() {
        GestorBbdd gBbdd = new GestorBbdd();
        Hilo hilo = null;
        gBbdd.lectura();//Para saber cuantos registros hay en total
        int auxInicial = 0, auxFinal = 0;
        long iLecturaConcurrente, fLecturaConcurrente;
        int nRegistros = gBbdd.getNumeroRegistros();
        int registrosPorHilo = nRegistros / N_HILOS;

        //Empezamos a contar el tiempo
        iLecturaConcurrente = System.currentTimeMillis();
        for (int i = 1; i <= N_HILOS; i++) {
            auxInicial = registrosPorHilo * i + 1;
            auxFinal = registrosPorHilo * (i + 1);
            System.out.println("••••••••••••••••• HILO " + i + " •••••••••••••••••");
            System.out.println("DE => " + auxInicial + " A => " + auxFinal);
            hilo = new Hilo(auxInicial, auxFinal);
            hilo.start();
            try {
                hilo.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        //terminamos contar tiempo
        fLecturaConcurrente = System.currentTimeMillis();
        System.out.println("La suma de los ingresos es de: " + gBbdd.getSumaIngresos());
        System.out.println("El tiempo en leer los registros de forma concurrente es de: " + (fLecturaConcurrente - iLecturaConcurrente) + " milisegundos.");
    }
}
