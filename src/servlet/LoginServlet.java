package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.BaseBean;
import bean.UserBean;
import utils.DBUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username"); // 获取客户端传过来的参数
	    String password = request.getParameter("password");
	    response.setHeader("Content-Type", "text/html;charset=UTF-8");
	    if (username.equals("")||username==null||password.equals("")||password==null) {
			System.out.println("用户名或密码为空");
			return;
		}
	    //请求数据库
	    DBUtils dbUtils=new DBUtils();
	    dbUtils.openConnect();//打开数据库连接
	    UserBean user=new UserBean();//User对象
	    BaseBean response_data=new BaseBean();//返回的基类bean
	    if(dbUtils.isExistInDB(username, password)) {
	    	response_data.setCode(0);
	    	response_data.setMsg("登录成功！");
	    	ResultSet set=dbUtils.getUser();
	    	user.setUser_name(username);
			user.setUser_pwd(password);
			int id=-1;
			 if (set != null) {
		            try {
		                while (set.next()) {
		                    if (set.getString("user_name").equals(username) && set.getString("user_pwd").equals(password)) {
		                        id = set.getInt("user_id");
		                    }
		                }
		                user.setUser_id(id);
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
	    	
	    	response_data.setData(user);
	    }else {
	    	 response_data.setCode(-1);
	    	 response_data.setData(user);
	    	 response_data.setMsg("该账号不存在");
		}
	    	Gson gson=new Gson();
	    	String json=gson.toJson(response_data);//将对象转化成json字符串
		    try {
		        response.getWriter().println(json); // 将json数据传给客户端
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        response.getWriter().close(); // 关闭这个流，不然会发生错误的
		    }
		    dbUtils.closeConnect(); // 关闭数据库连接
	    
	}

}
