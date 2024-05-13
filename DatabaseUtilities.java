package utils;

import enums.IsolationLevel;

import java.sql.*;

public class DatabaseUtilities {

    private static Connection con;
    private static final String conString = "jdbc:sqlserver://localhost:1433;databaseName=AdventureWorks2019;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

    public static void connectToDatabase() throws SQLException {

        con = DriverManager.getConnection(conString);


    }

    public static int update(String sql) throws SQLException {

        Statement stmt = con.createStatement();
        return stmt.executeUpdate(sql);

    }

    public static int select(String sql) throws SQLException{

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        return rs.getMetaData().getColumnCount();

    }

    public static void changeIsolationLevel(IsolationLevel isolationLevel) throws SQLException {

        switch(isolationLevel) {
            case READ_UNCOMMITTED:
                con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                break;
            case READ_COMMITTED:
                con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                break;
            case SERIALIZABLE:
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                break;
            case REPEATABLE_READ:
                con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                break;
            default:
                System.out.println("ERROR: ISOLATION LEVEL UNKNOWN.");
        }

    }

    public static void debug() throws SQLException{

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM Person.Person");

        while(rs.next()){

            System.out.println("First name: " + rs.getString("FirstName") + " , Last name: " + rs.getString("LastName"));

        }

    }

}
