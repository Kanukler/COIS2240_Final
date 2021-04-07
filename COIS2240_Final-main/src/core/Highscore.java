package core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Highscore {
    public static void Highscore(String fileName){
        String url = "jdbc:sqlite:C/sqlite" + fileName;

        try {
            Connection conn = DriverManager.getConnection(url);
        if(conn != null){
            DatabaseMetaData meta = conn.getMetaData();

        }
        }catch (SQLException e){
            System.out.println(e.getMessage()
            );
        }

    }

}
