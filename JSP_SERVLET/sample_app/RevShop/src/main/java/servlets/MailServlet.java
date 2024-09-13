package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import configs.EmailConfig;

/**
 * Servlet implementation class MailServlet
 */
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String to = request.getParameter("to");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		
		System.out.println("EMAIL::::SESSION:"+request.getSession().getAttribute("user"));
		System.out.println("EMAIL::::SESSION:"+request.getSession().getAttribute("att1"));

		if(request.getSession().getAttribute("user") != null) {
			EmailConfig.sendEmail(to, subject, message);

		}else {
			request.getRequestDispatcher("/demo.jsp").forward(request, response);
		}
		
		
	}

}
