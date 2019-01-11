<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
	<h1>[ LoginTestProject ]</h1>

	<c:if test="${loginId != null}">
		${sessionScope.loginName}(${sessionScope.loginId})님 로그인 중<br>
	</c:if>

	<ul>
		<c:if test="${loginId == null}">
			<li><a href="join">회원 가입</a>
			<li><a href="login">로그인</a>
		</c:if>
		<c:if test="${loginId != null}">
			<li><a href="logout">로그아웃</a>
			<li><a href="update">개인정보 수정</a>
		</c:if>
	</ul>

</body>
</html>
