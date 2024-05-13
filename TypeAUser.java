package objs;

import handlers.Main;
import handlers.SimulationHandler;
import utils.DatabaseUtilities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TypeAUser implements Runnable{

    //UPDATE USER

    private int transactionCount;
    SimpleDateFormat clock = new SimpleDateFormat("HH:mm:ss"); //Creating timer
    //SimpleDateFormat seconds = new SimpleDateFormat("HH:mm:ss"); //Time is converting to second
    Random random = new Random();
    private int deadlockOccurA;





    public TypeAUser(int transActionCount){
        super();
        this.transactionCount = transActionCount;
    }

    public void run(){

        long start = System.currentTimeMillis(); //Timer started

        System.out.println("Update Transactions started at: " + clock.format(new Date(start))); //Timer print at the beginning

        while(transactionCount > 0){
            try {
                update();
                Main.mainFrame.getMainPanel().completeATransaction();
                transactionCount--;
            } catch (SQLException e) {
                if(e.getSQLState() != null && e.getSQLState().startsWith("12")){
                    deadlockOccurA++;
                    System.out.println("DEADLOCK OCCURED ON TYPE A" + " : " + deadlockOccurA);
                    Main.mainFrame.getMainPanel().log("Deadlock observed on type A. A Deadlock Count: " + deadlockOccurA);
                    Main.mainFrame.getMainPanel().completeATransaction();
                }
                e.printStackTrace();
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("Update Transactions finished at: " + clock.format(new Date(finish))); //Timer print at the end

        long runtime = finish - start;
        System.out.println("Update Transactions runned for: " + runtime/1000 + " seconds."); //Timer differences between beginning and end

        SimulationHandler.finishTimeA = System.currentTimeMillis();


    }

    private void update() throws SQLException{

        int affectedRows = 0;

        String query = "UPDATE Sales.SalesOrderDetail " +
                "SET UnitPrice = UnitPrice * 10.0 / 10.0 " +
                "WHERE UnitPrice > 100 " +
                "AND EXISTS (SELECT * FROM Sales.SalesOrderHeader " +
                "WHERE Sales.SalesOrderHeader.SalesOrderID = " +
                "Sales.SalesOrderDetail.SalesOrderID " +
                "AND Sales.SalesOrderHeader.OrderDate " +
                "BETWEEN @BeginDate AND @EndDate " +
                "AND Sales.SalesOrderHeader.OnlineOrderFlag = 1);";

        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.update(query.replaceAll("@BeginDate", "'2011-01-01'")
                    .replaceAll("@EndDate", "'2011-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.update(query.replaceAll("@BeginDate", "'2012-01-01'")
                    .replaceAll("@EndDate", "'2012-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.update(query.replaceAll("@BeginDate", "'2013-01-01'")
                    .replaceAll("@EndDate", "'2013-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.update(query.replaceAll("@BeginDate", "'2014-01-01'")
                    .replaceAll("@EndDate", "'2014-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.update(query.replaceAll("@BeginDate", "'2015-01-01'")
                    .replaceAll("@EndDate", "'2015-12-31'"));

        System.out.println("1 transaction completed. Affected rows: " + affectedRows);

    }










}



