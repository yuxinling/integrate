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
	<script type="text/javascript" src="plugins/ueditor/ueditor.config.js"></script>
   	<!-- 编辑器源码文件 -->
   	<script type="text/javascript" src="plugins/ueditor/ueditor.all.js"></script>
	
	<script type="text/javascript">
	
	</script>
	<style type="text/css">
	.ace-file-input {
		width:200px;
	}
	</style>
	</head>
	
<body>
	<form action="bg/productEdit.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" id="id" value="${article.id}"/>
		<input type="hidden" name="detail" id="detail" />
		<div id="zhongxin">
			<div style="text-align: center;">
				<span style="font-size: 20px;">添加商品</span>
			</div>
			
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<tr>
					<th nowrap="nowrap">商品名称:</th>
					<td>
						<input id="title" type="text" name="title" value="${article.title}" style="width:720px;"/>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">缩略图片:</th>
					<td>
						<div style="padding-bottom: 5px;">
						<img alt="" width="150" height="150"  src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537500267&di=e595eae82dcd7015899604bd13702e52&imgtype=jpg&er=1&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D3267847dcbea15ce41bbe80d833016c5%2F4bed2e738bd4b31cb65d7e4385d6277f9f2ff8e1.jpg">
						</div>
						<input type="file" id="thumbnail" name="thumbnail" onchange="fileType(this)"/>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">兑换积分:</th>
					<td>
						<input id="title" type="text" name="title" value="${article.title}" style="width:720px;"/>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">商品详情:</th>
					<td>
						<script id="container" name="content" type="text/plain">${article.detail}</script>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<a class="btn btn-mini btn-primary" onclick="saveArticle();">保存</a>
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
		var ue = UE.getEditor('container');
		function saveArticle(){
			var detail = ue.getContent();
			$("#detail").val(detail);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//上传
			$('#thumbnail').ace_file_input({
				style:{width:200},
				no_file:'请选择app ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				maxSize:false,
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