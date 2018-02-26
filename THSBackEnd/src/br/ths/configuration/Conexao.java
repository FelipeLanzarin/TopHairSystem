package br.ths.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexao {
    
    static String url = "jdbc:postgresql://convidas.cbxmqyd20yi8.us-east-2.rds.amazonaws.com/thsadmin"; //monta a url de conexão
    static String driver = "org.postgresql.Driver";//driver
    static String usuario = "thsadmin";//usuario do BD
    static String senha = "C0nnect123";//senha do BD
    static Connection con = null;
    
    public static Connection getConexao()
    {
        System.out.println("get conexao");
        if(con == null){
            System.out.println("con == null");
            System.out.println("starting conect");
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, usuario, senha);
                System.out.println("connect Sucess");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("connect ClassNotFoundException");
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("connect SQLException");
            }
        }
        return con;
    }
  
    public static Statement getStatement()
    {
        try {
            return getConexao().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static PreparedStatement getPreparedStatement(String sql){
        try {
            return getConexao().prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static void close(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        con = null;
    }
    
}