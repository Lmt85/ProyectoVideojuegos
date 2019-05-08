/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;
import java.awt.Graphics;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author marcelosuarez
 */
public class dbManager {
    Connection c;           // Database connection
    Statement stmt;         // Sql statement to execute
    Game game;
    public dbManager(Game game) {
        this.game = game;
    }
    
    
    // Establishes the db connection
    public void init() {
        c = null;
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:test.db");
           c.setAutoCommit(false);
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    
    public boolean searchPlayer(){
        String sql = "SELECT * FROM player WHERE playerid = '" + getGame().getPlayerid() + "';";
        try {
             Statement stmt  = c.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);
             if(rs.next()) {
                 return true;
             }
             stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public void registerPlayer() {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO player (playerid,playername) VALUES('" + getGame().getPlayerid() + "','" + getGame().getPlayerName() + "');";
            stmt.executeUpdate(sql);
            
            stmt.close();
            c.commit();

        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
    }
    
    public void registerGame(long timeElapsed) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO game (playerid, time) VALUES('" + getGame().getPlayerid() + "'," + timeElapsed/1000 + ");";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO scores (playerid, score) VALUES('" + getGame().getPlayerid() + "'," + getGame().getScore() + ");";
            stmt.executeUpdate(sql);
            
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
    }
    
     public ArrayList<String> searchUserScores() {
        ArrayList<String> res = new ArrayList<>();
        try {
            stmt = c.createStatement();
            String sql = "Select * FROM player Where playerid = '" + getGame().getPlayerid() + "';";
            ResultSet rs  = stmt.executeQuery(sql);
            if(rs.next()) {
                getGame().setPlayerName(rs.getString("playername"));
                res.add(getGame().getPlayerName().toUpperCase() + " SCORES:");
                res.add("==========");
            }
            
            sql = "Select * From Scores Where playerid = '" + getGame().getPlayerid() + "' ORDER BY SCORE DESC LIMIT 5;";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                res.add(rs.getString("score"));
            }
            
            stmt.close();
            c.commit();
 
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        
        return res;
    }
    
     
    public ArrayList<String> searchGlobalScores() {
        ArrayList<String> res = new ArrayList<>();
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM SCORES GROUP BY playerid ORDER BY SCORE DESC LIMIT 5;";
            ResultSet rs  = stmt.executeQuery(sql);
            res.add("GLOBAL RANKING");
            res.add("==========");
            while(rs.next()) {
                res.add(rs.getString("playerid") + ": " + rs.getInt("score"));
            }
            
            stmt.close();
            c.commit();
 
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        
        return res;
    }
     
    public Connection getC() {
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    
    
}

