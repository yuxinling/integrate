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
	<%@ include file="../system/admin/top.jsp"%>
	
	<!--查看图片插件 -->
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
    <script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
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
			<form action="bg/userList.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
					
					<span class="input-icon">区域代理
						<select name="isAreaProxy">
						    <option <c:if test="${isAreaProxy==''}">selected</c:if> value="">全部</option>
							 <option <c:if test="${isAreaProxy=='0'}">selected</c:if> value="0">否</option>
							  <option <c:if test="${isAreaProxy=='1'}">selected</c:if> value="1">是</option>
				    </select>
						<span class="input-icon">
						<input autocomplete="off" id="nav-search-input" type="text" name="mobile" value="${mobile}" placeholder="这里输入手机" />
						
					</span>
					
					</span>
					<%-- <select  style="width: 100px" name="provinceCode" id="provinceCode" class="input_txt" onchange="cityFresh(this.options[this.options.selectedIndex].value);">
							<option value=""></option>
							<c:forEach items="${provinceMap}" var="mapEntry">
								<option value="${mapEntry.key}">${mapEntry.value}</option>
							</c:forEach>
						</select>

						<select  style="width: 100px" name="cityCode" id="cityCode" class="input_txt" onchange="areaFresh(this.options[this.options.selectedIndex].value);">

						</select>

						<select  style="width: 100px" name="areaCode" id="areaCode" class="input_txt" >

						</select> --%>
						
						<td>
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
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search">检索</i></button></td>
					<td style="vertical-align:top;padding-top: 2px;padding-left: 15px;">
						<a class="btn btn-mini btn-info" onclick="freezeAll();"><i>冻结全部</i></a>
						<a class="btn btn-mini btn-info" onclick="unFreezeAll();"><i>全部解冻</i></a>
					</td>
				
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
					     <th>固定时间</th>
					     <th>奖励时间</th>
					     <th>充值金额</th>
					     <th>兑换金额</th>
					     <th>提现金额</th>
						<th>是否区域代理</th>
						<th>是否冻结</th>
						<th nowrap="nowrap">冻结全部功能</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
								
									<td class='center' style="width: 7%;">${var.nickName}</td>
									<td style="width: 8%;" >${var.mobile}</td>
									
									<td style="width: 10%;" >${var.area}</td>
									<td style="width: 10%;" >${var.integrate}</td>
									<td style="width: 5%;" >${var.fixDay}</td>
									<td style="width: 5%;" >${var.giveDay}</td>
									<td style="width: 10%;" >${var.rechrageTotal}</td>
									<td style="width: 10%;" >${var.exchangeTotal}</td>
									<td style="width: 10%;" >${var.withdrawalsTotal}</td>
									<td style="width: 10%;" >
									<c:if test="${var.isAreaProxy==1}">
									<font color="red">是</font>
									</c:if>
									<c:if test="${var.isAreaProxy==0}">
									否
									</c:if>
								
									</td>
									<td style="width: 5%;" >
									<c:if test="${var.isFreeze==0}">
									<font color="red">否</font>
									</c:if>
									<c:if test="${var.isFreeze==1}">
									是
									</c:if>
								
									</td>
									<td style="width: 5%;" >
									<c:if test="${var.isFreezeAll==0}">
									<font color="red">否</font>
									</c:if>
									<c:if test="${var.isFreezeAll==1}">
									是
									</c:if>
								
									</td>
									<td style="width: 15%;" class="center">
									
									<a style="cursor: pointer;" title="用户参数设置"
											onclick="edit('${var.userId}','${var.hasproxy }','${var.isAreaProxy }','${var.fixDay}','${var.giveDay}');" class="tooltip-success"
											data-rel="tooltip" title="" data-placement="left"><span
												class="green"><i class="icon-edit"></i></span></a>

									<a style="cursor:pointer;" title="代理充值" onclick="humanRecharge('${var.userId}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-money"></i></span></a>
                          
                                   <a style="cursor: pointer;" title="冻结用户"
											onclick="editFreeze('${var.userId}','${var.isFreeze }');" class="tooltip-success"
											data-rel="tooltip" title="" data-placement="left"><span
												class="green"><i class="icon-tint"></i></span></a>
									<a style="cursor:pointer;" title="用户详细记录" onclick="record('${var.userId}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="pink"><i class="icon-list"></i></span></a>
									</td>
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
		
		
		
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("Form").submit();
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
		function edit(Id,hasproxy,isAreaProxy,fixDay,giveDay){
			//已有区域代理情况  改其他用户状态
			//修改
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = "<%=basePath%>bg/toedit.do?id="+Id+"&hasproxy="+hasproxy+"&isAreaProxy="+isAreaProxy+"&fixDay="+fixDay+"&giveDay="+giveDay+"&tm="+new Date().getTime();
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
		
		
<%-- 		//设置冻结
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
		} --%>
		
		
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
		
		function freezeAll(){
			if(confirm("确定全部冻结？")){
	 			$.ajax({    
					url:'bg/freezeAll.do',
					data: {},
					type:'post',
					dataType:'json',
					success:function(data) {
						if(data["code"] == 200){
							$("Form").submit();
						}
					},    
					error : function() {}    
				}); 
			}
		}
		
		function unFreezeAll(){
			if(confirm("确定全部解冻？")){
				$.ajax({    
					url:'bg/unFreezeAll.do',
					data: {},
					type:'post', 
					dataType:'json',
					success:function(data) {
						if(data.code == 200){
							$("Form").submit();
						}
					},    
					error : function() {}    
				});
			}
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

