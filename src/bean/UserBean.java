package bean;

public class UserBean {
	private String user_name;
    private int  user_id;
    private String user_pwd;
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int id) {
        this.user_id = id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String name) {
        this.user_name = name;
    }
    public String getUser_pwd() {
        return user_pwd;
    }
    public void setUser_pwd(String pwd) {
        this.user_pwd = pwd;
    }
	
}
