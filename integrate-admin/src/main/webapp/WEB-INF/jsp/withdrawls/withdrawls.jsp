<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>

<!--查看图片插件 -->
<link rel="stylesheet" media="screen" type="text/css"
	href="plugins/zoomimage/css/zoomimage.css" />
<link rel="stylesheet" media="screen" type="text/css"
	href="plugins/zoomimage/css/custom.css" />

<script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
<!--查看图片插件 -->

</head>
<body>
	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">

				<div class="row-fluid">

					<!-- 检索  -->
					<form action="bg/withdrawlsList.do" method="post" name="Form"
						id="Form">
						<table>
							<tr>
								<td><select style="width: 100px" name="provinceCode"
									id="provinceCode" class="input_txt"
									onchange="cityFresh(this.options[this.options.selectedIndex].value);">
										<option value="0">--省份--</option>
										<c:forEach items="${provinceMap}" var="mapEntry">
											<option
												<c:if test="${provincecode==mapEntry.key}">selected</c:if>
												value="${mapEntry.key}">${mapEntry.value}</option>
										</c:forEach>
								</select> <select style="width: 100px" name="cityCode" id="cityCode"
									class="input_txt"
									onchange="areaFresh(this.options[this.options.selectedIndex].value);">
										<c:forEach items="${cityMap}" var="city">
											<option
												<c:if test="${citycode==city.cityCode}">selected</c:if>
												value="${city.cityCode}">${city.viewCityName}</option>
										</c:forEach>
								</select> <select style="width: 100px" name="areaCode" id="areaCode"
									class="input_txt">
										<c:forEach items="${areaInfoList}" var="area">
											<option
												<c:if test="${areacode==area.areaCode}">selected</c:if>
												value="${area.areaCode}">${area.areaName}</option>
										</c:forEach>
								</select></td>
								<td><span class="input-icon"> <input
										class="date-picker" name="begin" id="begin" value="${begin}"
										type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
										style="width: 75px;" placeholder="开始日期" /> - <input
										class="date-picker" name="end" id="end" value="${end}"
										type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
										style="width: 75px;" placeholder="结束日期" />
								</span></td>
								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>
								<td style="vertical-align: top;"><a
									class="btn btn-mini btn-light" onclick="excel();"
									title="导出到EXCEL"><i id="nav-search-icon"
										class="icon-download-alt"></i></a></td>

							</tr>
						</table>
						<!-- 检索  -->

						<table id="table_report"
							class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center" onclick="selectAll()"><label><input
											type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th>姓名</th>
									<th>手机</th>
									<th>区域</th>
									<th>持卡人</th>
									<th>银行</th>
									<th>开户行</th>
									<th>银行卡号</th>
									<th>提现金额</th>
									<th>日期</th>
									<th>操作栏</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">

										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 2%;"><label><input
														type='checkbox' name='ids' value="${var.id}" /><span
														class="lbl"></span></label></td>
												<td style="width: 10%;">${var.nickName}</td>

												<td style="width: 10%;">${var.mobile}</td>
												<td style="width: 10%;">${var.area}</td>
												<td style="width: 8%;">${var.cardholder}</td>
												<td style="width: 10%;">${var.bankName}</td>
												<td style="width: 15%;">${var.branch}</td>
												<td style="width: 10%;">${var.number}</td>
												<td style="width: 8%;">${var.money}</td>
												<td style="width: 10%;">${var.time}</td>
												<c:if test="${var.state==0}">
													<td style="width: 5%;" class="center"><a
														style="cursor: pointer;" title="确认转账"
														onclick="edit('${var.id}');" class="tooltip-success"
														data-rel="tooltip" title="" data-placement="left"><span
															class="green"><i class="icon-edit"></i></span></a></td>

												</c:if>
												<c:if test="${var.state==1 }">
													<td style="width: 10%;">已转账</td>
												</c:if>
											</tr>
										</c:forEach>


										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>


							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;"><a
										class="btn btn-small btn-danger"
										onclick="makeAll('确定选中的数据已转账?');" title="批量转账"><i
											class='icon-edit'></i></a></td>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>

					</form>
				</div>




				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->


	<script type="text/javascript">
		
		$(top.hangge());

		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		//导出
		function excel(){
			
			var begin=$('#begin').val();
			var end=$('#end').val();
			var url = "<%=basePath%>bg/withdrawlsExcel?begin="+begin+"&end="+end;
			window.location.href=url;
		}
		
		
		//转账
		function edit(id){
		
			if(confirm("确定已转账?")){ 
				top.jzts();
				var url = "<%=basePath%>bg/withdrawlsEdit.do?id="+id;
				$.get(url,function(data){
					nextPage(${page.currentPage});
				});
			}
		}
		
		function cityFresh(provinceCode){
			$.ajax({
				type : "POST",
				url : "bg/cityList.do",
				data : {
					provinceCode : provinceCode
				},
				dataType : "json",
				success : function(data) {
					var newOption = "<option value=''></option>";
					$(data).each(function(){
						newOption += "<option value=" + this.cityCode + ">" + this.viewCityName + "</option>";
					});

					$("#cityCode").html(newOption);
					$("#areaCode").html("<option value=''></option>");
				}
			});
		}

		function areaFresh(cityCode){
			$.ajax({
				type : "POST",
				url : "bg/areaList.do",
				data : {
					cityCode : cityCode
				},
				dataType : "json",
				success : function(data) {
					var newOption = "<option value=''></option>";
					$(data).each(function(){
						newOption += "<option value=" + this.areaCode + ">" + this.areaName + "</option>";
					});

					$("#areaCode").html(newOption);
				}
			});
		}
		
		
		</script>

	<script type="text/javascript">
		
		//全选 （是/否）
		function selectAll(){
			 var checklist = document.getElementsByName ("ids");
			   if(document.getElementById("zcheckbox").checked){
			   for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			 }
		}

		
		
		//批量操作
		function makeAll(msg){
			
			if(confirm(msg)){ 
				
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						alert("您没有选择任何内容!"); 
						return;
					}else{
						
					 if(msg == '确定选中的数据已转账?'){
						
								top.jzts();
								var url = "<%=basePath%>bg/withdrawlsEdit.do?id="+str;
								$.get(url,function(data){
									nextPage(${page.currentPage});
								});
							
						} 
					}
			}
		}
		
		</script>

	<style type="text/css">
li {
	list-style-type: none;
}
</style>
	<ul class="navigationTabs">
		<li><a></a></li>
		<li></li>
	</ul>
</body>
</html>

