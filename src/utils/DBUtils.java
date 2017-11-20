package utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBUtils {
	private Connection conn;
    private String url = "jdbc:mysql://127.0.0.1:3306/login"; // 指定连接数据库的URL
    private String user = "root"; // 指定连接数据库的用户名
    private String password = "123456"; // 指定连接数据库的密码

    private Statement sta;
    private ResultSet rs;

    // 打开数据库连接
    public void openConnect() {
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url, user, password);// 创建数据库连接
            if (conn != null) {
                System.out.println("数据库连接成功"); // 连接成功的提示信息
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    //获得查询user表后的数据集
    public ResultSet getUser() {
        // 创建 statement对象
        try {
            sta = (Statement) conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Login");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    //获取查询info表后的数据集
    public ResultSet getInfo() {
		//创建statement对象
    	try {
			sta=(Statement) conn.createStatement();
			//执行SQL查询语句
			rs=sta.executeQuery("select * from info");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return rs;
	}
    
    // 判断数据库中是否存在某个用户名及其密码,注册和登录的时候判断
    public boolean isExistInDB(String username, String password) {
        boolean isFlag = false;
        // 创建 statement对象
        try {
            sta = (Statement) conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Login");//获得结果集
            if (rs != null) {
                while (rs.next()) {  //遍历结果集
                    if (rs.getString("user_name").equals(username)) {
                        if (rs.getString("user_pwd").equals(password)){
                            isFlag = true;
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isFlag = false;
        }
        return isFlag;
    }
    
    //注册  将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
    public boolean insertDataToDB(String username, String password){
        String sql = " insert into Login ( user_name , user_pwd ) values ( " + "'" + username
                + "', " + "'" + password + "' )";
        try {
            sta = (Statement) conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 关闭数据库连接
    public void closeConnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (sta != null) {
                sta.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("关闭数据库连接成功");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
