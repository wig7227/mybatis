package com.mybatis.board.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import com.mybatis.board.service.BoardServiceImpl;
import com.mybatis.board.vo.Board;
import com.mybatis.common.template.Pagination;
import com.mybatis.common.vo.PageInfo;

public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//조회수 증가 시키기
		int totalRecord = new BoardServiceImpl().selectTotalRecord();
		int nowPage = Integer.parseInt(request.getParameter("nowPage"));
		
		PageInfo pi = Pagination.getPageInfo(totalRecord, nowPage, 5, 2);
		ArrayList<Board> list = new BoardServiceImpl().selectList(pi);
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		
		int boardNo = new BoardServiceImpl().increaseCount(boardNo);
		
		increaseCount count = new BoardServiceImpl().increaseCount(boardNo);
		//조회수 증가가 잘되었으면 검색하여 boardDetailView.jsp로 가져
		request.getRequestDispatcher("WEB-INF/views/board/boardDetailView.jsp").forward(request, response);
	}

}
