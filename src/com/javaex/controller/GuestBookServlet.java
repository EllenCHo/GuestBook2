package com.javaex.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

/**
 * Servlet implementation class GeustBookServlet
 */
@WebServlet("/gb")
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");
		
		if("add".equals(action)) {
			System.out.println("add");
			
			String name = request.getParameter("name");
			String pw = request.getParameter("password");
			String content = request.getParameter("content");
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(cal.getTime());
			
			GuestBookDao dao = new GuestBookDao();
			dao.insert(new GuestBookVo(name, pw, content, date));
			
			response.sendRedirect("/g2/gb");
			
		} else if("deleteform".equals(action)) {
			System.out.println("deleteform");
			
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			RequestDispatcher rd = request.getRequestDispatcher("deleteform.jsp");
			rd.forward(request, response);
			
		} else if("delete".equals(action)) {
			System.out.println("delete");
			
			String no = request.getParameter("no");
			String pw = request.getParameter("password");
			
			GuestBookDao dao = new GuestBookDao();
			dao.delete(no, pw);	
			
			response.sendRedirect("/g2/gb");
		} else {
			//리스트
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getlist();
			
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
			rd.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
