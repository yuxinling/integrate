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
	<base href="<%=basePath%>">

	<meta charset="utf-8" />
	<title></title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
	<!-- ace styles -->
	<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>

	<script type="text/javascript">


	</script>
</head>
<body>
<form action="bg/userrecord.do" method="post" name="Form" id="Form">
	<input type="hidden" name="userId" id="userId" value="${userId}"/>
	<div id="zhongxin">
	
	<table>
					<tr>
						<td>
						<select name="type">
						<option <c:if test="${type==0}"> selected="selected" </c:if>  value="0">充值记录</option>
						<option <c:if test="${type==1}"> selected="selected" </c:if>  value="1">兑换记录</option>
						<option <c:if test="${type==2}"> selected="selected" </c:if>  value="2">提现记录</option>
						</select>
						</td>
                       <td style="vertical-align:top;"><button onclick="search();"  title="检索" value="检索">查询</button>
                       </td>
					</tr>
		</table>
		<table id="table_report" class="table table-striped table-bordered table-hover">

			<thead>
			<tr>
				<c:if test="${type<2}">
				<th>订单编号</th>
				<th>金额</th>
				<c:if test="${type<1}">
				<th>代理人ID</th>
				<th>代理人姓名</th>
				<th>是否后台代理充值</th>
				</c:if>
				<th>时间</th>
				</c:if>
				<c:if test="${type==2}">
				<th>姓名</th>				
				<th>持卡人</th>
				<th>银行</th>
				<th>开户行</th>
				<th>银行卡号</th>
				<th>金额</th>
				<th>状态</th>
				<th>日期</th>
				</c:if>
			</tr>
			</thead>

			<tbody>

			<!-- 开始循环 <fmt:formatDate value="${var.tradeNo}" pattern="yyyy-MM-dd HH:mm:ss"/> -->
			<c:choose>
				<c:when test="${not empty varList}">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
							<c:if test="${type<2}">
							
								<td style="width: 20%;">${var.tradeNo}</td>
								<td style="width: 10%;" >${var.money}</td>
								<c:if test="${type<1}">
								<td style="width: 15%;" >${var.proxyUserId}</td>
								<td style="width: 15%;" >${var.nickName}</td>
								
								<td style="width: 15%;" >
									<c:choose>
										<c:when test="${var.artificial == 0}">
											否
										</c:when>										
										<c:when test="${var.artificial == 1}">
											是
										</c:when>
									</c:choose>
								</td>
								</c:if>
								<td style="width: 20%;" >${var.time}</td>
								</c:if>
							
							
							<c:if test="${type==2}">
									<td style="width: 8%;">${var.nickName}</td>												
									<td style="width: 8%;">${var.cardholder}</td>
									<td style="width: 8%;">${var.bankName}</td>
									<td style="width: 15%;">${var.branch}</td>
									<td style="width: 10%;">${var.number}</td>
									<td style="width: 8%;">${var.money}</td>
									
									<c:if test="${var.state==0}">
									<td style="width: 8%;">
									<font color="red">待转账</font></td>	

									</c:if>
									<c:if test="${var.state==1 }">
										<td style="width: 8%;">已转账</td>
									</c:if>
							       <td style="width: 15%;">${var.time}</td>
							</c:if>
							</tr>
							
						</c:forEach>
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
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>

</form>


<!-- 引入 -->
<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script src="static/js/bootstrap.min.js"></script>
<!-- ace scripts -->
<script src="static/assets/js/ace/elements.fileinput.js"></script>
<script src="static/assets/js/ace/ace.js"></script>
<script type="text/javascript">
	$(top.hangge());

	//检索
	function search(){
		top.jzts();
		$("#Form").submit();
	}

	$(function() {

	});

</script>
</body>
</html>