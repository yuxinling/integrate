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
						<div style="width: 100%;text-align: right;margin-bottom: 10px;">
							<a class="btn btn-mini btn-info" onclick="addArticle();" style="margin-right: 50px;"><i>添加新闻</i></a>
						</div>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>序号</th>
									<th>标题</th>
									<th>操作</th>
								</tr>
							</thead>

							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty articles}">
										<c:forEach items="${articles}" var="var" varStatus="vs">
											<tr>
												<td style="width: 20%;text-align: center;vertical-align: middle;">${var.id}</td>
												<td style="width: 60%;text-align: center;vertical-align: middle;">${var.title}</td>
												<td style="width: 20%;text-align: center;vertical-align: middle;">
													<a style="cursor: pointer;" title="编辑" onclick="editArticle('${var.id}')" 
														class="tooltip-success" data-rel="tooltip" title="" data-placement="left">
														<span class="green"><i class="icon-edit"></i></span>
													</a>
													
													<a style="cursor:pointer;" title="详细" onclick="viewArticle('${var.id}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="pink"><i class="icon-list"></i></span></a>
													<a style="cursor:pointer;" title="删除" onclick="deleteArticle('${var.id}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-remove"></i></span></a>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
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
		function addArticle(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = "<%=basePath%>bg/articleNewToEdit.do?tm="+new Date().getTime();
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 location.reload();
				}
				diag.close();
			 };
			 diag.show();			
		}
		function editArticle(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = "<%=basePath%>bg/articleNewToEdit.do?id="+id+"&tm="+new Date().getTime();
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 location.reload();
				}
				diag.close();
			 };
			 diag.show();			
		}
		function viewArticle(id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查看";
			 diag.URL = "<%=basePath%>bg/articleNewToView.do?id="+id+"&tm="+new Date().getTime();
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();			
		}
		
	<%-- 	function deleteArticle(id){
			if(confirm("确定要删除？")){ 
				top.jzts();
				var url = "<%=basePath%>bg/articleNewDelete.do?id="+id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					location.reload();
				});
			}
		} --%>
		
		function deleteArticle(id){
			if(confirm("确定要删除？")){ 
				$.ajax({    
					url:"<%=basePath%>bg/articleNewDelete.do?id="+id+"&tm="+new Date().getTime(),
					data: {},
					type:'post', 
					dataType:'json',
					success:function(data) {
						if(data.code == 200){
							location.reload();
						}
					},    
					error : function() {}    
				});
			}
		}
		
		
	</script>
	<ul class="navigationTabs">
		<li><a></a></li>
		<li></li>
	</ul>
</body>
</html>

