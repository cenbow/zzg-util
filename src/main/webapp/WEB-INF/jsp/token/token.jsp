<%@page import="com.zzg.util.token.Token"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>新增终端 - 酷云大数据</title>
<!--[if lt IE 9]><script src="../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
	
		
</head>
<body class="page-body">
	
	<span>${message}</span>
	
	<form action="../token_test/do_submit">
		<input type="hidden" name="<%=Token.TOKEN_KEY %>" value="<%=Token.createToken()%>"/>
		<button type="submit">狂点我</button>
	</form>

</body>
</html>