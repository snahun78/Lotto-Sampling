<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common-include.jsp"%>

<body style="padding: 15px">
	${result }
	<br>
	<c:forEach var="item" items="${result }">
		${item}
	</c:forEach>
</body>
</html>