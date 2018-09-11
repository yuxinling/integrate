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

		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	//保存
	function save(){
		var value = $("#giveDay").val();
		var fixDay = $("#fixDay").val();
		var regex = /^[0-9]+$/;
		if(regex.test(value) && regex.test(fixDay)){
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}else{
			$("#error").html("请输入正确的数值(大于或等于'0'的整数)");
		}
	}
	
	function checkValue(id){
		var value = $("#"+id+"Day").val();
		var regex = /^[0-9]+$/;
		if(!regex.test(value)){
			$("#"+id+"-error").html("请输入正确的数值(大于或等于'0'的整数)");
		}else{
			$("#"+id+"-error").html("");
		}
	}
	
	
	
</script>
	</head>
<body>
	<form action="bg/edit.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="uid" id="uid" value="${uid}"/>
		<input type="hidden" name="hasproxy" id="hasproxy" value="${hasproxy}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<th>说明:</th>
				<td>
				<c:if test="${isAreaProxy==1}">
				   该用户是区域代理
				</c:if>
				<c:if test="${isAreaProxy==0}">
				  该用户不是区域代理
				</c:if>
				
				</td>
			</tr>
			<tr>
				<th>是否设置区域代理:</th>
				<td>
				<select name="isAreaProxy">
				 <option <c:if test="${isAreaProxy==0}">selected </c:if> value="0">否</option>
				  <option <c:if test="${isAreaProxy==1}">selected </c:if> value="1">是</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>固定时间:</th>
				<td>
					<input id="fixDay" type="text" name="fixDay" onkeyup="checkValue('fix');" value="${fixDay}"/>
					<div id="fix-error" style="color: red;font-size: 8px;margin-top: -10px;"></div>
				</td>
			</tr>
			<tr>
				<th>奖励时间:</th>
				<td>
					<input id="giveDay" type="text" name="giveDay" onkeyup="checkValue('give');" value="${giveDay}"/>
					<div id="give-error" style="color: red;font-size: 8px;margin-top: -10px;"></div>
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
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
		$(function() {
			//上传
			$('#tp').ace_file_input({
				no_file:'请选择图片 ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'gif|png|jpg|jpeg',
				//blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});
			
		});
		
		</script>
</body>
</html>