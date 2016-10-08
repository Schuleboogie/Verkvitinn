<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<title>${user.username}</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/user.css"/>"/>
	</head>
	<body>
		<header>
			<h1>${user.username}</h1>
		</header>
		<main>
			<section class="user-info">
				<h2>User info</h2>
				<img src="https://api.adorable.io/avatars/120/${user.username}.png" alt="${user.username}">
				<ul>
					<li><span class="item-title">Username:</span> ${user.username}</li>
					<li><span class="item-title">Role:</span> ${user.role}</li>
				</ul>
			</section>
		</main>
</html>
