<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<title>User</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/user.css"/>"/>
	</head>
	<body>
		<header>
			<h1>Welcome User</h1>
			<img src="http://www.90s411.com/images/office-space-lumbergh.jpg" alt="User Image">
		</header>
		<main>
			<section class="menu">
				<ul>
					<li>Create new project</li>
					<li>Settings</li>
				</ul>
			</section>
			<section class="projects">
				<h2>Ongoing projects</h2>
				<ul>
					<li>Eskivellir 11 apartments</li>
					<li>Hvergigata 57 house</li>
					<li>East Harbor Hotel</li>
				</ul>
			</section>
		</main>
		<!--<script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>-->
</html>
