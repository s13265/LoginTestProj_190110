package com.test.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 개인정보 수정 콘트롤러
 * 모델에 "customer"라는 이름의 값이 저장될 때, 세션에도 저장
 * 수정단계의 시작부터 마지막단계까지 세션의 값 유지
 */
@Controller
public class CustomerUpdateController {
	
	@Autowired
	CustomerDAO dao;		//회원 관련 데이터 처리 객체
	
	/**
	 * 회원 정보 수정 폼 보기
	 */
	@RequestMapping (value="update", method=RequestMethod.GET)
	public String updateForm(HttpSession session, Model model) {
		//세션의 로그인ID로 개인정보를 검색하여 모델에 저장
		String id = (String) session.getAttribute("loginId");
		Customer customer = dao.get(id);
	
		model.addAttribute("customer", customer);
		return "updateForm";
	}

	/**
	 * 회원 정보 수정 처리
	 * @param customer 사용자가 입력한 수정 정보가 추가된 객체.
	 */
	@RequestMapping (value="update", method=RequestMethod.POST)
	public String update(Customer customer, Model model, HttpSession session) {
		System.out.println("customer : "+customer);
		if (session.getAttribute("custid") == null) {
			model.addAttribute("errorMsg", "세션 끊김");
			return "updateForm";
		}
		customer.setCustid((String)session.getAttribute("custid"));
		int result = dao.update(customer);
		if (result != 1) {
			//DB update에 실패한 경우 alert() 출력용 메시지를 모델에 저장
			model.addAttribute("errorMsg", "수정 실패");
			return "updateForm";
		} else {
			session.setAttribute("customer", customer);
		}
		
		return "redirect:updateComplete";
	}

	/**
	 * 회원 정보 수정 처리 완료
	 * @param customer DB에 최종 저장된 정보
	 */
	@RequestMapping(value="updateComplete", method=RequestMethod.GET)
	public String updateComplete(HttpSession session,	Model model) {

		// 세션 확인
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null) {
			//수정된 정보를 세션에도 반영 (로그인한 사용자 이름)
			session.setAttribute("loginName", customer.getName());
			
			//수정 처리된 정보를 모델에 저장
			model.addAttribute("result", customer);
		}
		
		return "updateComplete";
	}
}
