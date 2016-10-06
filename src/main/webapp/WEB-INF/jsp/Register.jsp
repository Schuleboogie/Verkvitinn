<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
	<head>
		<title>Register bakdyr</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<h1>Register me</h1>
		<sf:form action="" method="post" class="login-form" commandName="user">
			<label>Choose username</label>
			<sf:input path="username" type="text" />
			<label>Choose password</label>
			<sf:input path="password" type="password" />
			<label>Choose role</label>
			<sf:select path="role">
				<sf:option value="admin">Admin</sf:option>
				<sf:option value="worker">Worker</sf:option>
			</sf:select>
			<input type="submit" value="Register user">
		</sf:form>
</html>
