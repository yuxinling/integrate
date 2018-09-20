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
<link rel="stylesheet" href="plugins/nivo-slider/nivo-slider.css" type="text/css" media="screen" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
<!-- ace styles -->
<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script> 
<script type="text/javascript" src="plugins/nivo-slider/jquery.nivo.slider.pack.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<style type="text/css">

</style>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="bg/images/upload.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
						<div id="wrapper">
							<div id="slider-wrapper">
								<div id="slider" class="nivoSlider">
									<c:choose>
										<c:when test="${not empty images}">
											<c:forEach items="${images}" var="var" varStatus="vs">
												<img src="${var.src}" alt="" />
											</c:forEach>
										</c:when>
									</c:choose> 
								</div>

								 <div id="htmlcaption" class="nivo-html-caption">
									<strong>This</strong> is an example of a <em>HTML</em> caption
									with <a href="#">a link</a>.
								</div>
							</div>
						</div>
						<div style="width: 30%;height: 20px;float: left;">
						</div>
						<div style="width: 40%;float: left;padding-top: 20px;">
							<div style="width: 50%;float: left;"><input type="file" id="imageUp" name="imageUp" onchange="fileType(this)"/></div>
							<div style="width: 50%;float: left;"><a class="btn btn-mini btn-info" onclick="uploadImage();" style="margin-left: 10px;"><i>上传照片</i></a></div>
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
	      $('#slider').nivoSlider();
	    });

		$(function() {
		 	//上传
 			$('#imageUp').ace_file_input({
				style:{width:200},
				no_file:'请选择图片 ...',
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
		
		//保存
		function uploadImage(){
			if(typeof($("#imageUpz").val()) == "undefined"){
				if($("#imageUp").val()=="" || document.getElementById("imageUp").files[0] =='请选择图片'){
					
					$("#imageUp").tips({
						side:3,
			            msg:'请选图片',
			            bg:'#AE81FF',
			            time:3
			        });
					return false;
				}
			}
		
			$("#Form").submit();
		}

	</script>
</body>
</html>

