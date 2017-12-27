<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common-include.jsp"%>

<head>
	<title>Good Luck</title>
	<meta charset="utf-8">

	<script type="text/javascript">
		$(document).ready(function(){
			
		});			
	</script>
	
</head>

<body style="padding: 15px">
	<div class="alert alert-secondary" role="alert">
		<div>총 입력 수 : ${count}</div>
	</div>
	
	<form id="inputForm">
		<table class="table" style="width: 80%">
			<thead>
				<tr>
					<th scope="col" style="width: 100px">차수</th>
					<th scope="col">날짜</th>
					<th scope="col">1</th>
					<th scope="col">2</th>
					<th scope="col">3</th>
					<th scope="col">4</th>
					<th scope="col">5</th>
					<th scope="col">6</th>
					<th scope="col">보너스번호</th>
				</tr>
			</thead>
			<tbody id="inputTbody">
				<c:forEach var="item" items="${winningList }">
					<tr>
					<td>${item.drwNo }</td>
					<td>${item.winningDate }</td>
					<td>${item.number1 }</td>
					<td>${item.number2 }</td>
					<td>${item.number3 }</td>
					<td>${item.number4 }</td>
					<td>${item.number5 }</td>
					<td>${item.number6 }</td>
					<td>${item.bnusNo }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>