package Menus;

import java.util.LinkedList;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DBHandler {
    public LinkedList<Score> scores = new LinkedList<>();

    public DBHandler(){
    }

    // Connect and create database methods taken from class
    private Connection connectToDataBase(){
        try{
            Connection conn =
                    DriverManager.getConnection("JDBC:sqlite:D:\\MyThings\\Programming\\TDGame\\Database.db");
            return conn;
        }catch(SQLException e){
            System.out.println("Something Went Wrong @connectToDatabase " + e.getMessage());
            return null;
        }

    }

    public void createTable(){
        try {
            Connection conn = this.connectToDataBase();
            Statement query = conn.createStatement();
            query.execute("CREATE TABLE IF NOT EXISTS scores(name TEXT, score INT)");

        }catch(SQLException e){
            System.out.println("Something Went wrong @createTable " + e.getMessage());
        }
    }

    public void deleteDB(){
        try {
            Connection conn = this.connectToDataBase();
            Statement query = conn.createStatement();
            query.execute("DELETE FROM scores");

        }catch(SQLException e){
            System.out.println("Something Went wrong @createTable " + e.getMessage());
        }
    }

    public void insertDB(String name, int score){
        Score entered = new Score(name, score);
        Score scoreNext = new Score("out", 1);
        Score scoreNext2 = new Score("out", 1);
        boolean replaced = false;

        if (!scores.isEmpty()){
            for(int i = 0; i < scores.size() || i < 10; i++ ){
                if(entered.score > scores.get(i).getScore() && !replaced){
                    scoreNext = new Score(scores.get(i).getName(), scores.get(i).getScore());

                    scores.get(i).setScore(entered.score);
                    scores.get(i).setName(entered.name);
                    replaced = true;
                }else if(replaced){
                    scores.get(i).setScore(scoreNext.getScore());
                    scores.get(i).setName(scoreNext.getName());
                }
        }
        }


        String insert = "INSERT INTO scores VALUES(?, ?)";
        try{
            Connection conn = connectToDataBase();

            PreparedStatement query = conn.prepareStatement(insert);
            query.setString(1, name);
            query.setInt(2, score);
            query.execute();


            Statement sort = conn.createStatement();
            sort.execute("SELECT name, score FROM scores ORDER BY score DESC");


        }catch(SQLException e){

            System.out.println("Something Went wrong@insertScores " + e.getMessage());
        }
    }


    public void sortDB(){
        try{
            Connection conn = connectToDataBase();

            conn.setAutoCommit(false);

            Statement query = conn.createStatement();
            query.execute("SELECT * FROM scores ORDER BY score DESC");


            conn.commit();
            conn.close();
        }catch(SQLException e){

            System.out.println("Something Went wrong@insertScores " + e.getMessage());
        }
    }


    public void retrieveTable(){
        scores.clear();
        String get = "SELECT name, score FROM scores";

        try(
                Connection conn = connectToDataBase();
                Statement query = conn.createStatement();
                ResultSet rs = query.executeQuery(get)
        ){
            while (rs.next()){
                scores.add(new Score(
                        rs.getString("name"),
                        rs.getInt("score")
                ));
            }

        }catch(SQLException e){
            System.out.println("Something Went wrong2: " + e.getMessage());
        }
    }


    public void insertScores(){
        String insert = "INSERT INTO scores VALUES(?, ?)";

        try{
            Connection conn = connectToDataBase();
        for(int i = 0; i < 10; i++ ){
            PreparedStatement query = conn.prepareStatement(insert);
            query.setString(1, scores.get(i).getName());
            query.setInt(2, scores.get(i).getScore());
            query.execute();

            Statement sort = conn.createStatement();
            sort.execute("SELECT name, score FROM scores ORDER BY score DESC");


        }

        }catch(SQLException e){
            System.out.println("Something Went wrong@insertScores " + e.getMessage());
        }

    }


    public static boolean validateName(String name){
        String regex = "^[A-Z0-9]{3}+$";
        Pattern p = Pattern.compile(regex);

        if(name == null) return false;

        Matcher m = p.matcher(name);
        return m.matches();

    }

    public class Score{
        private String name;
        private int score;

        public Score(String name, int score){
            this.name = name;
            this.score = score;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

    }


}
