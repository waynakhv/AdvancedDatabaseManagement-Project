package objs;

import enums.IsolationLevel;
import handlers.Main;
import handlers.SimulationHandler;
import objs.TypeAUser;
import objs.TypeBUser;
import utils.DatabaseUtilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable{

    private IsolationLevel isolationLevel = IsolationLevel.READ_UNCOMMITTED;
    int transactionCount = 10;
    int typeACount = 0;
    int typeBCount = 0;

    List<Thread> allThreads = new ArrayList<>();

    public Simulation(int typeACount, int typeBCount, int transactionCount, IsolationLevel isolationLevel){

        this.typeACount = typeACount;
        this.typeBCount = typeBCount;
        this.transactionCount = transactionCount;
        this.isolationLevel = isolationLevel;

    }


    @Override
    public void run() {

        try {
            DatabaseUtilities.connectToDatabase();
            DatabaseUtilities.changeIsolationLevel(this.isolationLevel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        List<TypeAUser> typeAUsers = new ArrayList<>();
        List<TypeBUser> typeBUsers = new ArrayList<>();



        long startTime = System.currentTimeMillis();

        for (int i = 0; i < typeACount; i++) {
            TypeAUser userA = new TypeAUser(transactionCount);
            typeAUsers.add(userA);
            Thread th = new Thread(userA);

            th.start();
            allThreads.add(th);
        }


        for (int i = 0; i < typeBCount; i++) {
            TypeBUser userB = new TypeBUser(transactionCount);
            typeBUsers.add(userB);
            Thread th = new Thread(userB);

            th.start();
            allThreads.add(th);

        }

        for(Thread t : allThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                Main.mainFrame.getMainPanel().log("Simulation stopped unexpectedly.");
                System.out.println("SimStop");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Simulation ran for " + (endTime - startTime) / 1000 + " seconds.");
        Main.mainFrame.getMainPanel().log("Simulation ran for " + (endTime - startTime) / 1000 + " seconds.");

        System.out.println("Average time for A: " + ((SimulationHandler.finishTimeA - startTime) / (SimulationHandler.typeACount * 1000)) + " seconds.");
        System.out.println("Average time for B: " + ((SimulationHandler.finishTimeB - startTime) / (SimulationHandler.typeBCount * 1000)) + " seconds.");

        Main.mainFrame.getMainPanel().log("Average time for A: " + ((SimulationHandler.finishTimeA - startTime) / (SimulationHandler.typeACount * 1000))+ " seconds");
        Main.mainFrame.getMainPanel().log("Average time for B: " + ((SimulationHandler.finishTimeB - startTime) / (SimulationHandler.typeBCount * 1000)) + " seconds.");

        Main.mainFrame.getMainPanel().activateExecuteButton();

    }

    public void stopAllThreads(){

        for(Thread t : this.allThreads){

            t.interrupt();

        }

    }
}
