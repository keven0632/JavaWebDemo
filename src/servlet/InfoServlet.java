package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.BaseBean;
import bean.InfoBodyBean;
import utils.DBUtils;

/**
 * Servlet implementation class InfoServlet
 */
@WebServlet("/InfoServlet")
public class InfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoServlet() {
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
		DBUtils dbUtils=new DBUtils();
		dbUtils.openConnect();
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		BaseBean response_data=new BaseBean();
		ArrayList<InfoBodyBean> list=new ArrayList<InfoBodyBean>();
		ResultSet set=dbUtils.getInfo();
		if(set!=null) {
			response_data.setCode(0);
			response_data.setMsg("请求数据成功");
			try {
				while (set.next()) {
					InfoBodyBean bean=new InfoBodyBean();
					bean.setDetail(set.getString("detail"));
					bean.setImgurl(set.getString("imgUrl"));
					bean.setSummary(set.getString("summary"));
					bean.setTitle(set.getString("title"));
					list.add(bean);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response_data.setData(list);
		}else {
			response_data.setCode(-1);
			response_data.setMsg("未查询到相关数据");
			response_data.setData(list);
		}
		Gson gson=new Gson();
		String json=gson.toJson(response_data);
		response.getWriter().println(json);
		response.getWriter().close();
		dbUtils.closeConnect();
	}

}
