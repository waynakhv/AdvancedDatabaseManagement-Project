package objs;

import handlers.Main;
import handlers.SimulationHandler;
import utils.DatabaseUtilities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TypeBUser implements Runnable{

    //READ USER

    private int transactionCount;
    SimpleDateFormat clock = new SimpleDateFormat("HH:mm:ss"); //Creating timer
    SimpleDateFormat seconds = new SimpleDateFormat("ss"); //Time is converting to second
    Random random = new Random();
    private int deadlockOccurB;

    public TypeBUser(int transactionCount){
        super();
        this.transactionCount = transactionCount;

    }

    public void run(){

        long start = System.currentTimeMillis(); //Timer started

        System.out.println("Select Transactions started at: " + clock.format(new Date(start))); //Timer print at the beginning

        while(transactionCount > 0){
            try {
                select();
                Main.mainFrame.getMainPanel().completeATransaction();
                transactionCount--;
            } catch (SQLException e) {
                if(e.getSQLState() != null && e.getSQLState().startsWith("12")){
                    deadlockOccurB++;
                    System.out.println("DEADLOCK OCCURED ON TYPE B" + " : " + deadlockOccurB);
                    Main.mainFrame.getMainPanel().log("Deadlock observed on type B. B Deadlock Count: " + deadlockOccurB);
                    Main.mainFrame.getMainPanel().completeATransaction();
                }
                e.printStackTrace();
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("Select Transactions finished at: " + clock.format(new Date(finish))); //Timer print at the end

        long runtime = finish - start;
        System.out.println("Select Transactions runned for: " + runtime/1000 + " seconds"); //Timer differences between beginning and end

        SimulationHandler.finishTimeB = System.currentTimeMillis();
    }

    private void select() throws SQLException{

        int affectedRows = 0;

        String query =
                "SELECT SUM(Sales.SalesOrderDetail.OrderQty) " +
                        "FROM Sales.SalesOrderDetail " +
                        "WHERE UnitPrice > 100 " +
                        "AND EXISTS (SELECT * FROM Sales.SalesOrderHeader " +
                        "WHERE Sales.SalesOrderHeader.SalesOrderID = " +
                        "Sales.SalesOrderDetail.SalesOrderID " +
                        "AND Sales.SalesOrderHeader.OrderDate " +
                        "BETWEEN @BeginDate AND @EndDate " +
                        "AND Sales.SalesOrderHeader.OnlineOrderFlag = 1) ";

        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.select(query.replaceAll("@BeginDate", "'2011-01-01'")
                    .replaceAll("@EndDate", "'2011-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.select(query.replaceAll("@BeginDate", "'2012-01-01'")
                    .replaceAll("@EndDate", "'2012-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.select(query.replaceAll("@BeginDate", "'2013-01-01'")
                    .replaceAll("@EndDate", "'2013-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.select(query.replaceAll("@BeginDate", "'2014-01-01'")
                    .replaceAll("@EndDate", "'2014-12-31'"));
        if(random.nextBoolean())
            affectedRows += DatabaseUtilities.select(query.replaceAll("@BeginDate", "'2015-01-01'")
                    .replaceAll("@EndDate", "'2015-12-31'"));

        System.out.println("1 transaction completed. Returned row size: " + affectedRows);

    }



}
