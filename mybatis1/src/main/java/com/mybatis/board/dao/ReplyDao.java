package com.mybatis.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mybatis.board.controller.DBConnectionMgr;
import com.mybatis.board.vo.Reply;

import org.json.simple.JSONObject;


public class ReplyDao {
	DBConnectionMgr pool = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	// 전체 reply데이터 가져오기
	public ArrayList<Reply> getAllReply(int ref) {
		ArrayList<Reply> alist = new ArrayList<Reply>();
		try {
			con = pool.getConnection();
			sql = "select * from reply where ref=? order by no desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Reply bean = new Reply();
				bean.setRefBno(rs.getInt("no"));	//primary키는 항상 가져오기
				bean.setReplyContent(rs.getString("content"));
				/* bean.setRef(rs.getInt("ref")); */
				bean.setReplyWriter(rs.getString("name"));
				bean.setCreateDate(rs.getString("rdate"));
				alist.add(bean);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return alist;
	}
	
	public int insertReply(Reply bean) {
		int result = 0;
		try {
			con = pool.getConnection();
			sql = "insert into reply values(seq_reply.nextval, ?, ?, ?, sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getReplyContent());
			pstmt.setInt(2, bean.getRefBno());
			pstmt.setString(3, bean.getReplyWriter());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con);
		}
		return result;
	}
	
}
	
