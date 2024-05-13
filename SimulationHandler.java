package handlers;

import enums.IsolationLevel;
import objs.Simulation;

public class SimulationHandler {

    public static long startTime, finishTimeA, finishTimeB;

    public static int typeACount, typeBCount, transactionCount;

    private static Simulation simulation;
    private static Thread simulationThread;

    public static void startSimulation(int a, int b, int t, IsolationLevel i){


        typeACount = a;
        typeBCount = b;
        transactionCount = t;

        finishTimeA = System.currentTimeMillis();
        finishTimeB = System.currentTimeMillis();

        simulation = new Simulation(typeACount, typeBCount, transactionCount, i);

        simulationThread = new Thread(simulation);

        startTime = System.currentTimeMillis();
        simulationThread.start();
        Main.mainFrame.getMainPanel().log(String.format("Simulation started. A: %s, B: %s, T: %s, I: %s", a, b, t, i));



    }

    public static void stopSimulation(){
        simulation.stopAllThreads();
        simulationThread.interrupt();
    }

}
