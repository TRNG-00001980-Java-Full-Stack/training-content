package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;
import java.util.List;

import dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDao dao = new UserDao();
		
		System.out.println("path::"+request.getRequestURI());
		String[] paths = request.getRequestURI().split("/");
		for(String i : paths) {
			System.out.println(i);
		}
	
		List<User> users = dao.getAllUsers();
		request.setAttribute("userlist", users);
		request.setAttribute("topic", "List of Users");
		request.getRequestDispatcher("/displayusers.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setUsername(request.getParameter("username"));
		UserDao dao = new UserDao();
		dao.insertUser(user);
		System.out.println(request.getParameter("username"));
		
		HttpSession session = request.getSession();
		session.setAttribute("user", request.getParameter("username"));
		
		request.getRequestDispatcher("/email.jsp").forward(request, response);

		
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("id"));
		UserDao dao = new UserDao();
		if(dao.deleteUser(Integer.parseInt(request.getParameter("id")))) {
			doGet(request, response);
		}
	}

}
