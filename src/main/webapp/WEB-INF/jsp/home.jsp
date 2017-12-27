<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common-include.jsp"%>

<head>
	<title>Good Luck</title>
	<meta charset="utf-8">

	<script type="text/template" id="inputRow">
	<tr>
		<td><input type="text" id="drwNo" name="drwNo" value="" style="width: 50px"></td>
		<td><input type="text" id="number1" name="number1" value=""></td>
		<td><input type="text" id="number2" name="number2" value=""></td>
		<td><input type="text" id="number3" name="number3" value=""></td>
		<td><input type="text" id="number4" name="number4" value=""></td>
		<td><input type="text" id="number5" name="number5" value=""></td>
		<td><input type="text" id="number6" name="number6" value=""></td>
		<td><input type="text" id="bnusNo" name="bnusNo" value=""></td>
	</tr>
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			$('#addInput').on("click", function(){
				addInputRow();
			});
			
			$('#searchData').on("click", function(){
				var searchCnt = $("#drwNo").val();
				
				if(!searchCnt){
					if(confirm("차수를 입력하지 않으면 모든 데이터가 조회됩니다.")){
						$.ajax({
							url : '/sampling/getAllWinningNumber.ax',
							type : 'post',
							dataType : 'json',
							async : false,
							contentType : 'application/json; charset=UTF-8',
							success : function(data) {
								if(data){
									alert(data);
								}
							}
						});
					}
				}
				else{
					$.ajax({
						url : '/sampling/getWinningNumberLteDrwNo.ax',
						data : searchCnt,
						type : 'get',
						dataType : 'json',
						async : false,
						contentType : 'application/json; charset=UTF-8',
						success : function(data) {
							if(data){
								alert(data);
							}
						}
					});
				}
			});
			
			$("#saveData").on("click", function(){
				var datas = [];
				var numberVo = {};//$("#inputForm").serialize();
				
				numberVo.drwNo = $("#drwNo").val();
				numberVo.number1 = $("#number1").val();
				numberVo.number2 = $("#number2").val();
				numberVo.number3 = $("#number3").val();
				numberVo.number4 = $("#number4").val();
				numberVo.number5 = $("#number5").val();
				numberVo.number6 = $("#number6").val();
				numberVo.bnusNo = $("#bnusNo").val();
				
				console.log(numberVo);
				$.ajax({
					url : '/sampling/insertWinningNumber.ax',
					data : JSON.stringify(numberVo),
					type : 'post',
					dataType : 'text',
					async : false,
					contentType : 'application/json; charset=UTF-8',
					success : function(data) {
						if(data){
							alert(data);
							location.href = "/";
						}
					}
				})
			});
		});
		
		var addInputRow = function(){
			var row = $("#inputRow").html();
			$("#inputTbody").append(row);
		}
	</script>
	
</head>

<body style="padding: 15px">
	<div class="alert alert-secondary" role="alert">
		<div>총 입력 수 : ${count}</div>
		
		<div>마지막 입력 회차 : <c:if test="${lastItem != null && lastItem.drwNo != null }">${lastItem.drwNo }</c:if></div>
	</div>
	
	<form id="inputForm">
		<table class="table" style="width: 80%">
			<thead>
				<tr>
					<th scope="col" style="width: 50px">차수</th>
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
				<tr>
					<td><input type="text" id="drwNo" name="drwNo" value="" style="width: 50px"></td>
					<td><input type="text" id="number1" name="number1" value=""></td>
					<td><input type="text" id="number2" name="number2" value=""></td>
					<td><input type="text" id="number3" name="number3" value=""></td>
					<td><input type="text" id="number4" name="number4" value=""></td>
					<td><input type="text" id="number5" name="number5" value=""></td>
					<td><input type="text" id="number6" name="number6" value=""></td>
					<td><input type="text" id="bnusNo" name="bnusNo" value=""></td>
				</tr>
			</tbody>
		</table>

		<!-- <button type="button" class="btn btn-info" id="addInput">Add</button> -->
		<button type="button" class="btn btn-secondary" id="searchData">Search</button>
		<button type="button" class="btn btn-outline-success" id="saveData">Save</button>
	</form>
</body>
</html>