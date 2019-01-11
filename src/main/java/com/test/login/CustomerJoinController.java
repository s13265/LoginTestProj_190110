package com.test.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.login.Customer;
import com.test.login.CustomerDAO;

/**
 * 회원 가입 콘트롤러
 * 모델에 "customer"라는 이름의 값이 저장될 때, 세션에도 저장
 * 가입단계의 시작부터 마지막단계까지 세션의 값 유지
 */
@Controller
public class CustomerJoinController {
	
	@Autowired
	CustomerDAO dao;		//회원 관련 데이터 처리 객체
	
	/**
	 * 회원 가입 폼 보기
	 */
	@RequestMapping (value="join", method=RequestMethod.GET)
	public String joinForm() {
		return "joinForm";
	}

	/**
	 * 회원 가입 처리
	 * @param customer 사용자가 입력한 가입 정보가 추가된 객체.
	 */
	@RequestMapping (value="join", method=RequestMethod.POST)
	public String join(Customer customer, Model model, HttpSession session) {
		
		int result = dao.insert(customer);
		if (result != 1) {
			//DB저장에 실패한 경우 alert() 출력용 메시지를 모델에 저장
			model.addAttribute("errorMsg", "가입 실패");
			return "customer/joinForm";
		} else {
			session.setAttribute("customer", customer);
		}
		
		return "redirect:joinComplete";
	}

	/**
	 * 회원 가입 처리 완료
	 * @param customer DB에 최종 저장된 정보
	 */
	@RequestMapping(value="joinComplete", method=RequestMethod.GET)
	public String joinComplete(HttpSession session, Model model) {
		
		// 세션 확인
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null) {
			//가입 처리된 ID를 모델에 저장
			model.addAttribute("id", customer.getCustid());
			
			//세션해제
			session.invalidate();
		}
		
		return "joinComplete";
	}
	
	/**
	 * ID 중복 확인 폼 보기
	 */
	@RequestMapping(value="idcheck", method=RequestMethod.GET)
	public String idcheck(Model model) {
		//검색 전후 확인용
		model.addAttribute("search", false);
		return "idcheck";
	}
	
	/**
	 * ID 중복 검사
	 */
	@RequestMapping(value="idcheck", method=RequestMethod.POST)
	public String idcheck(String searchId, Model model) {
		//ID 검색
		Customer c = dao.get(searchId);
		
		//검색ID와 검색 결과, 검색전후 확인용 값 저장
		model.addAttribute("searchId", searchId);
		model.addAttribute("searchResult", c);
		model.addAttribute("search", true);
		
		return "idcheck";
	}
	
}
