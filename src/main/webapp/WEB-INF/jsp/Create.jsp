<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<title>Create new project</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/normalize.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/user.css"/>"/>
	</head>
	<body>
		<header>
			<h1>Create new project</h1>
		</header>
		<main>
			<section class="project-form">
				<h2>Project info</h2>
				<form	action="" method="post">
					<div class="field">
						<label for="name">Project name</label>
						<input type="text" id="name" name="name" autofocus>
					</div>
					<div class="field">
						<label for="description">Short description of the project</label>
						<textarea id="description"></textarea>
					<div class="field">
						<label for="location">Location</label>
						<input type="text" id="location" name="location">
					</div>
					<div class="field">
						<label for="tools">Required tools</label>
						<input type="text" id="tools">
					</div>
					<div class="field">
						<label for="est-time">Estimated time</label>
						<input type="text" id="est-time">
					</div>
					<div class="field">
						<label for="workers">Assigned workers</label>
						<ul id="workers" class="worker-list">
							<p class="empty">There are no assigned workers</p>
						</ul>
					</div>
					<div class="field">
						<input type="submit" value="Create project">
					</div>
				</form>
			</section>
			<section class="available-workers">
				<h2>Available workers</h2>
				<ul id="availWorkers" class="worker-list">
					<li><img src="https://api.adorable.io/avatars/55/abott@adorable.io.png" alt="User avatar"><span class="worker-name">John<a href="#">See info</a></span><a href="#" class="add">Add worker</a></li>
					<li><img src="https://api.adorable.io/avatars/55/john@adorable.io.png" alt="User avatar"><span class="worker-name">Paul<a href="#">See info</a></span><a href="#" class="add">Add worker</a></li>
					<li><img src="https://api.adorable.io/avatars/55/skuli@adorable.io.png" alt="User avatar"><span class="worker-name">Will<a href="#">See info</a></span><a href="#" class="add">Add worker</a></li>
					<li><img src="https://api.adorable.io/avatars/55/sunna@adorable.io.png" alt="User avatar"><span class="worker-name">Roger<a href="#">See info</a></span><a href="#" class="add">Add worker</a></li>
					<li><img src="https://api.adorable.io/avatars/55/briem@adorable.io.png" alt="User avatar"><span class="worker-name">Brandon<a href="#">See info</a></span><a href="#" class="add">Add worker</a></li>
				</ul>
			</section>
		</main>
		<script type="text/javascript" src="<c:url value="/js/jquery-3.1.1.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/create.js"/>"></script>
</html>
