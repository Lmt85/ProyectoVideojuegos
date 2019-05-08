/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;
import java.sql.*;

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
    
    public void registerGame() {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO game (playerid, time) VALUES('" + getGame().getPlayerid() + "',"  + ");";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO scores (playerid, scores) VALUES(" + getGame().getPlayerid() + ");";
            stmt.executeUpdate(sql);
            
            stmt.close();
            c.commit();

        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
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

