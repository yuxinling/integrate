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
	<!--查看图片插件 -->
	
	</head>
<body>
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">

			<!-- 检索  -->
			<form action="bg/statisticsUser.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>																				
						<td>
						<span class="input-icon"> <input autocomplete="off"
										id="nav-search-input" type="hidden" name="type" value="1"
										placeholder="这里输入昵称" />
										 <span class="input-icon">
										  <input class="date-picker"	name="begin" id="begin" value="${begin}" type="text"	data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 75px;" placeholder="开始日期" /> - <input
											class="date-picker" name="end" id="end" value="${end}"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 75px;" placeholder="结束日期" />
									</span> <i id="nav-search-icon" class="icon-search"></i>
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

						<select  style="width: 100px" name="areaCode" id="areaCode" class="input_txt" >
							<c:forEach items="${areaInfoList}" var="area">
								<option <c:if test="${areacode==area.areaCode}">selected</c:if> value="${area.areaCode}">${area.areaName}</option>
							</c:forEach>
						</select>
					</td>
					
					
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align: top;"><a
							class="btn btn-mini btn-light" onclick="excel();"
							title="导出到EXCEL"><i id="nav-search-icon2"
												class="icon-download-alt"></i></a></td>

				</tr>
			</table>
			<!-- 检索  -->

			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						
						<th>姓名</th>
						<th>手机号</th>							
						<th>区域</th>
					     <th>积分</th>
					     <th>注册时间</th>
				
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
								
									<td class='center' style="width: 10%;">${var.nickName}</td>
									<td style="width: 10%;" >${var.mobile}</td>
									
									<td style="width: 15%;" >${var.area}</td>
									<td style="width: 10%;" >${var.integrate}</td>
									<td style="width: 10%;" >${var.time}</td>
									
								</tr>
							</c:forEach>
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
		src="static/js/bootstrap-datepicker.min.js">
		
		
		</script>
		
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
			var cityCode=$('#cityCode').val();
			var areaCode=$('#areaCode').val();
			var url = "<%=basePath%>bg/statisticsUserExcel?cityCode="+cityCode+"&areaCode="+areaCode+"&begin="+begin+"&end="+end;
			window.location.href=url;
		}

		
		//发红包
		function humanRecharge(userId){

			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="代理充值";
			diag.URL = '<%=basePath%>bg/humanRechargeToEdit.do?userId='+userId;
			diag.Width = 500;
			diag.Height = 450;
			diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			};
			diag.show();
		}
		
		
		//设置区域代理
		function edit(Id,hasproxy,isAreaProxy){
			//已有区域代理情况  改其他用户状态
		/* 	if(isAreaProxy==0&& hasproxy==1){
				alert("该地区已有区域代理无法设置");
				return false;
			} */
			//
			//修改
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = "<%=basePath%>bg/tosetproxy.do?id="+Id+"&hasproxy="+hasproxy+"&isAreaProxy="+isAreaProxy+"&tm="+new Date().getTime();
				 diag.Width = 600;
				 diag.Height = 305;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 nextPage(${page.currentPage});
					}
					diag.close();
				 };
				 diag.show();			
		}
		
		
		//设置冻结
		function editFreeze(Id,isFreeze){
			//已有区域代理情况  改其他用户状态
		/* 	if(isAreaProxy==0&& hasproxy==1){
				alert("该地区已有区域代理无法设置");
				return false;
			} */
			//
			//修改
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="冻结";
				 diag.URL = "<%=basePath%>bg/isfreezeToedit.do?userId="+Id+"&isFreeze="+isFreeze+"&tm="+new Date().getTime();
				 diag.Width = 600;
				 diag.Height = 205;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 nextPage(${page.currentPage});
					}
					diag.close();
				 };
				 diag.show();			
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
		//冻结 解冻
		function editFreeze(id,isFreeze){
			if(confirm("改变冻结状态")){ 
				if(isFreeze==0){
					isFreeze=1;
				}else{
					isFreeze=0;
				}
				top.jzts();
				var url = "<%=basePath%>bg/isfreezeEdit.do?userId="+id+"&isFreeze="+isFreeze;
				$.get(url,function(data){
					nextPage(${page.currentPage});
				});
			}
		}
		
		

		function record(userId){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="详细记录";
			diag.URL = '<%=basePath%>bg/usertorecord.do?userId='+userId;
			diag.Width = 1000;
			diag.Height = 650;
			diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			};
			diag.show();
		}


		
		//bg/isfreezeEdit
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

