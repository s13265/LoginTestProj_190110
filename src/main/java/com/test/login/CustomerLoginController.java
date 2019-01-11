package com.test.login;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 회원 로그인, 로그아웃 처리 콘트롤러
 */
@Controller
public class CustomerLoginController {

	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 로그인 폼 보기
	 */
	@RequestMapping (value="login", method=RequestMethod.GET)
	public String loginForm() {
		return "loginForm";
	}

	/**
	 * 로그인 처리
	 * @param id 사용자가 입력한 아이디
	 * @param password 사용자가 입력한 비밀번호
	 * @param model Model객체
	 * @param session HttpSession객체
	 */
	@RequestMapping (value="login", method=RequestMethod.POST)
	public String login(
			String id, String password, 
			Model model, HttpSession session) {
		
		CustomerMapper dao = sqlSession.getMapper(CustomerMapper.class);
		Customer customer = dao.selectCustomer(id);
		
		if (customer != null && customer.getPassword().equals(password)) {
			session.setAttribute("loginId", customer.getCustid());
			session.setAttribute("loginName", customer.getName());
			return "redirect:/";
		}
		else {
			model.addAttribute("errorMsg", "ID 또는 비밀번호가 틀립니다.");
			return "loginForm";
		}
	}
	
	/**
	 * 로그아웃 처리
	 * @param session HttpSession객체
	 */
	@RequestMapping (value="logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
