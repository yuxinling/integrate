<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	    <script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
	<%@ include file="../system/admin/top.jsp"%>
	
	<!--查看图片插件 -->
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />

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
			<form action="bg/statisticsRechrage.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
					<span class="input-icon">
						<input  type="hidden" name="type" value="0"  />
				<span class="input-icon" >
						<input class="date-picker" name="begin" id="begin" value="${begin}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:75px;" placeholder="开始日期"/>
						-
						<input class="date-picker" name="end" id="end" value="${end}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:75px;" placeholder="结束日期"/>
							</span>
						<i id="nav-search-icon" class="icon-search"></i>
					</span>
					
					<select  style="width: 100px" name="provinceCode" id="provinceCode" class="input_txt" onchange="cityFresh(this.options[this.options.selectedIndex].value);">
							<option  value="0">--省份--</option>
							<c:forEach items="${provinceMap}" var="mapEntry">
								<option <c:if test="${provincecode==mapEntry.key}">selected</c:if> value="${mapEntry.key}">${mapEntry.value}</option>
							</c:forEach>
						</select>

						<select  style="width: 100px" name="cityCode" id="cityCode" class="input_txt" onchange="areaFresh(this.options[this.options.selectedIndex].value);">
							<c:forEach items="${cityMap}" var="city">
								<option <c:if test="${citycode==city.cityCode}">selected</c:if> value="${city.cityCode}">${city.viewCityName}</option>
							</c:forEach>
						</select>

						<select  style="width: 100px" name="areaCode" id="areaCode" class="input_txt" onchange="proxyFresh(this.options[this.options.selectedIndex].value);" >
							<c:forEach items="${areaInfoList}" var="area">
								<option <c:if test="${areacode==area.areaCode}">selected</c:if> value="${area.areaCode}">${area.areaName}</option>
							</c:forEach>
						</select>
						代理人：<select  style="width: 100px" name="proxyUserId" id="proxyUserId" class="input_txt" >
						  <c:forEach items="${proxy}" var="proxy">
								<option <c:if test="${proxyUserId==proxy.userId}">selected</c:if> value="${proxy.userId}">${proxy.name}</option>
							</c:forEach>
					    </select>
					</td>
					
				
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align: top;"><a
							class="btn btn-mini btn-light" onclick="excel();"
							title="导出到EXCEL"><i id="nav-search-icon2"
												class="icon-download-alt"></i></a></td>
				</tr> 
				<tr style="font-size: 18px;"><td >			
				<c:if test="${rechrageTotal >= 0}">
				充值总金额:<font color="red">${rechrageTotal }</font> 
				</c:if><br>
				<c:if test="${returnTotal>=0 }">
				已退还的积分:<font color="red">${returnTotal}</font> 
				</c:if>
				</td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						
						<th>姓名</th>
						<th>手机</th>
						<th>区域</th>
						<th>区域代理人</th>
						<th>充值积分</th>
						<th>日期</th>
						
					
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty varList}">
		
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									
									<td style="width: 10%;" >${var.name}</td>
									
									<td style="width: 15%;" >${var.mobile}</td>
									<td style="width: 15%;" >${var.area}</td>
									<td style="width: 10%;" >${var.proxyName}</td>
									<td style="width: 10%;" >${var.money}</td>
									<td style="width: 15%;" >${var.time}</td>
									
									
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
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					
					
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
			<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
		
		
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
		
		//冻结 解冻
		function edit(id,isFreeze){
			if(confirm("改变冻结状态")){ 
				if(isFreeze==0){
					isFreeze=1;
				}else{
					isFreeze=0;
				}
				top.jzts();
				var url = "<%=basePath%>bg/rechrageIsfreeze.do?id="+id+"&isFreeze="+isFreeze;
				$.get(url,function(data){
					nextPage(${page.currentPage});
				});
			}
		}

		//导出
		function excel(){
			var begin=$('#begin').val();
			var end=$('#end').val();
			var cityCode=$('#cityCode').val();
			var areaCode=$('#areaCode').val();
			var proxyUserId=$('#proxyUserId').val();
			var url = "<%=basePath%>bg/statisticsRechrageExcel?cityCode="+cityCode+"&areaCode="+areaCode+"&begin="+begin+"&end="+end+"&proxyUserId="+proxyUserId+"&type=0";
			window.location.href=url;
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
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>bg/newsCommentDelAll.do?tm='+new Date().getTime(),
						    	data: {commentIds:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									nextPage(${page.currentPage});
								}
							});
						}
					}
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
		
		function proxyFresh(areaCode){
			$.ajax({
				type : "POST",
				url : "bg/proxyList.do",
				data : {
					areaCode : areaCode
				},
				dataType : "json",
				success : function(data) {
					var newOption = "<option value=''></option>";
					$(data).each(function(){
						newOption += "<option value=" + this.userId + ">" + this.name + "</option>";
					});

					$("#proxyUserId").html(newOption);
				}
			});
		}
		</script>
		<style type="text/css">
		li {list-style-type:none;}
		</style>
		<ul class="navigationTabs">
            <li><a></a></li>
            <li></li>
        </ul>
	</body>
</html>

