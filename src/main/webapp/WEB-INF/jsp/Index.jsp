<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<title>Verkvitinn</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>"/>
	</head>
	<body>
		<header>
			<h1>Verkvitinn</h1>
		</header>
		<main>
			<div class="form-container">
				<form	action="" method="post" class="login-form">
					<div class="field">
						<label for="username">Username</label>
						<input type="text" id="username" name="username" autofocus>
					</div>
					<div class="field">
						<label for="password">Password</label>
						<input type="password" id="password" name="password">
					</div>
					<div class="field">
						<input type="submit" value="Login">
					</div>
				</form>
				<div class="error">${error}</div>
			</div>
		</main>
		<!--<script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>-->
</html>
