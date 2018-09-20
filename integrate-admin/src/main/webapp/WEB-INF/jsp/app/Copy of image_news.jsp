<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<!-- jsp文件头和头部 -->
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
<style type="text/css">
li {
	list-style-type: none;
}
</style>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form>
						<%-- <c:choose>
							<c:when test="${not empty images}">
								<div style="width: 100%;text-align: center;">
									<c:forEach items="${images}" var="var" varStatus="vs">
										<img src="${var.src}" width="400" height="200"/>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<tr class="main_info">
									<td colspan="100" class="center">没有相关数据</td>
								</tr>
							</c:otherwise>
						</c:choose> --%>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>序号</th>
									<th>标题</th>
									<th>预览</th>
									<th>操作</th>
								</tr>
							</thead>

							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty images}">
										<c:forEach items="${images}" var="var" varStatus="vs">
											<tr>
												<td style="width: 15%;text-align: center;vertical-align: middle;">${var.id}</td>
												<td style="width: 20%;text-align: center;vertical-align: middle;">${var.title}</td>
												<td style="width: 50%;text-align: center;vertical-align: middle;"><img src="${var.src}" width="400" height="200"/></td>
												<td style="width: 15%;text-align: center;vertical-align: middle;"></td>
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
									<td style="vertical-align: top;"></td>
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
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->


	<script type="text/javascript">
		$(top.hangge());

		//检索
		function search() {
			top.jzts();
			$("#Form").submit();
		}

		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
	</script>
	<ul class="navigationTabs">
		<li><a></a></li>
		<li></li>
	</ul>
</body>
</html>

