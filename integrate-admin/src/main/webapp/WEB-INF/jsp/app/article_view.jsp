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
	</head>
<body>
	<form action="bg/articleNewEdit.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
			<div>
				<iframe id="iframe-detail" frameborder="0" style="border: 0px;width: 100%;height: 576px;"></iframe>
			</div>
		</div>
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
		$("#iframe-detail").contents().find("body").append('${article.detail}');
	});
	
	// document.domain = "caibaojian.com";

	function setIframeHeight(iframe) {
		if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
				iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	};

	window.onload = function() {
		//setIframeHeight(document.getElementById('iframe-detail'));
	};
	</script>
</body>
</html>