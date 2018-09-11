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

		if($("#areaCode").val()==null || $("#areaCode").val()==""){
			$("#areaCode").tips({
				side:3,
				msg:'请输入所属区域',
				bg:'#AE81FF',
				time:2
			});
			$("#areaCode").focus();
			return false;
		}
		if($("#proxy").val()==null || $("#proxy").val()==""){
			$("#proxy").tips({
				side:3,
				msg:'请选择代理人，没有代理人请前往设置',
				bg:'#AE81FF',
				time:2
			});
			$("#areaCode").focus();
			return false;
		}

		if($("#money").val()=="" || !isPositiveNum($("#money").val())){
			$("#money").tips({
				side:3,
				msg:'请输入整数的金额',
				bg:'#AE81FF',
				time:2
			});
			$("#money").focus();
			return false;
		}

		var text = "确定要给用户：【"+ $("#userName").text() + "】"+ "转账：【"+ $("#money").val() + "】 元，"+"区域代理人为【"+$("#proxy").find("option:selected").text()+"】， " +"且所属县为：【"+ $("#areaCode").find("option:selected").text() +"】吗？";
		if(!confirm(text)){
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function isPositiveNum(s){//是否为正整数
		var re = /^[0-9]*[1-9][0-9]*$/ ;
		return re.test(s);
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
	
	function proxyFresh(areaCode){
		$.ajax({
			type : "POST",
			url : "bg/proxyList.do",
			data : {
				areaCode : areaCode
			},
			dataType : "json",
			success : function(data) {
				var newOption = "<option value=''></option>";
				$(data).each(function(){
					newOption += "<option value=" + this.userId + ">" + this.name + "</option>";
				});

				$("#proxy").html(newOption);
			}
		});
	}
	
	
</script>
	</head>
<body>
	<form action="bg/humanRechargeEdit.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="userId" id="userId" value="${userBase.userId}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<th>会员号:</th>
				<td>${userBase.userId}</td>
			</tr>
			<tr>
				<th>姓名:</th>
				<td id="userName">${userBase.name}</td>
			</tr>
			<tr>
				<th>手机:</th>
				<td id="mobile">${userBase.mobile}</td>
			</tr>
			<tr>
				<th>充值对应区域:</th>
				<td>
					<select  style="width: 100px" name="provinceCode" id="provinceCode" class="input_txt" onchange="cityFresh(this.options[this.options.selectedIndex].value);">
						<option  value="0">--省份--</option>
						<c:forEach items="${provinceMap}" var="mapEntry">
							<option value="${mapEntry.key}">${mapEntry.value}</option>
						</c:forEach>
					</select>

					<select  style="width: 100px" name="cityCode" id="cityCode" class="input_txt" onchange="areaFresh(this.options[this.options.selectedIndex].value);">
						<c:forEach items="${cityMap}" var="city">
							<option value="${city.cityCode}">${city.viewCityName}</option>
						</c:forEach>
					</select>

					<select  style="width: 100px" name="areaCode" id="areaCode" class="input_txt"  onchange="proxyFresh(this.options[this.options.selectedIndex].value);">
						<c:forEach items="${areaInfoList}" var="area">
							<option value="${area.areaCode}">${area.areaName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
			
			</tr>
			<th>区域代理人:</th>
			<td>
			<select  style="width: 100px" name="proxy" id="proxy" class="input_txt" >
						
					</select>
			</td>
			<tr>
				<th>充值金额</th>
				<td><input type="text" name="money" id="money" value="" maxlength="32" style="width:95%;" placeholder="这里输入充值金额" title="充值金额"/></td>
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