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
					<form action="bg/exchangeList.do" method="post" name="Form"
						id="Form">
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="hidden"
										name="type" value="1" placeholder="这里输入昵称" /> <span
										class="input-icon"> <input class="date-picker"
											name="begin" id="begin" value="${begin}" type="text"
											data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 75px;" placeholder="开始日期" /> - <input
											class="date-picker" name="end" id="end" value="${end}"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width: 75px;" placeholder="结束日期" />
									</span> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>

							</tr>
						</table>
						<!-- 检索  -->

						<table id="table_report"
							class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th>姓名</th>
									<th>手机</th>
									<th>兑换积分</th>
									<th>兑换状态</th>
									<th>日期</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td style="width: 15%;">${var.name}</td>
												<td style="width: 15%;">${var.mobile}</td>
												<td style="width: 15%;">${var.money}</td>
												<td style="width: 15%;">
													<c:if test="${var.status==0}"> 
														<font color="red"> </font>
													</c:if>
													<c:if test="${var.status==1}">
														<font color="green">处理中</font>
													</c:if>
													<c:if test="${var.status==2}">
														<font color="red">已兑换</font>
													</c:if>
												</td>
												<td style="width: 15%;">${var.time}</td>
												<td style="width: 5%;" class="center">
												  <a style="cursor: pointer;" title="编辑-修改状态" onclick="edit('${var.id}','${var.name}','${var.mobile}','${var.money}','${var.status}');" class="tooltip-success" 
												  		data-rel="tooltip" title="" data-placement="left">
														<span class="green"><i class="icon-edit"></i></span>
												  </a>&nbsp;
												</td>
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
		
		//删除
		function del(Id){
			
			if(confirm("确定要删除?")){ 
				top.jzts();
				var url = "<%=basePath%>bg/newsCommentDel.do?commentIds="+Id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					nextPage(${page.currentPage});
				});
			}
		}
		
		function edit(id,name,mobile,money,status){
			//修改
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = "<%=basePath%>bg/exchangeToEdit.do?id="+id+"&name="+name+"&mobile="+mobile+"&status="+status+"&money="+money+"&tm="+new Date().getTime();
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

