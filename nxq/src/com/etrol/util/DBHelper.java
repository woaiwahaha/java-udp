package com.etrol.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBHelper {
    //----------------------------------sqlserver----------------------------------
    private static final String URL_STRING ="";
    private static final String DRIVER_STRING = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String USER_STRING = "sa";
    private static final String PASSWORD_STRING = "";

    //-----------------------------------mysql------------------------------------------
//  private static final String URL_STRING ="jdbc:mysql://localhost:3306/test";
//  private static final String DRIVER_STRING = "com.mysql.jdbc.Driver";
//  private static final String USER_STRING = "jy";
//  private static final String PASSWORD_STRING = "jy";

    //-----------------------------------oracle------------------------------------------------
//  private static final String URL_STRING = "jdbc:oracle:thin:127.0.0.1:1521:orcl";
//  private static final String DRIVER_STRING = "oracle.jdbc.driver.OracleDriver";
//  private static final String USER_STRING = "scott";
//  private static final String PASSWORD_STRING = "tiger";



    private static Connection connection = null ;
    private DBHelper(){

    }

    /**
     * 获取数据库连接
     * Connection
     * @return
     * DBHelper
     */
    public static Connection getConnection(){
        if (connection==null) {
            try {
                Class.forName(DRIVER_STRING);
                try {
                    connection = DriverManager.getConnection(URL_STRING, USER_STRING, PASSWORD_STRING);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return connection;
    }

    /**
     * 关闭数据库连接
     * void
     * @param connection
     * DBHelper
     */
    public static void connectionClose(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
        //--------------------------------sqlserver select----------------------------------------
        Connection connection = getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet reSet = st.executeQuery("select * from baseinfo_AssayResultItem;");
            reSet.next();
            System.out.println(reSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //---------------------------------mysql select--------------------------------------------

//      try {
//          PreparedStatement ps = (PreparedStatement) connection.prepareStatement("select * from baseinfo_AssayResultItem");
//          ResultSet rs = ps.executeQuery();
//          rs.next();
//          System.out.println(rs.getString(1));
//      } catch (SQLException e) {
//          e.printStackTrace();
//      }

    }





}