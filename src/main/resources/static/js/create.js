$(document).ready(function() {
	Site.start();
});

var Site = (function() {
	var workerList = $('ul#workers');
	var availableWorkerList = $('ul#availWorkers');

	function start() {
		availableWorkerList.find('li a.add').each(function() {
			$(this).click(addWorker);
		});
		workerList.find('li a.add').each(function() {
			$(this).click(addAvailableWorker);
		});
	}
	// Add worker to list of workers on job
	function addWorker(e) {
		e.preventDefault();
		var worker = $(this).parent().clone();
		worker.find('a.add').html("<i class=\"fa fa-minus-square-o\" aria-hidden=\"true\"></i> Remove worker");
		// Add event listener for removing worker
		worker.find('a.add').click(addAvailableWorker);

		// Remove empty error if found
		if (workerList.find('p.empty'))
			workerList.find('p.empty').remove();
		workerList.append(worker);
		$(this).parent().remove();
		// Add text for empty list if list empty
		if ($.trim(availableWorkerList.html()) == '')
			availableWorkerList.append($('<p class="empty">There are no available workers</p>'));
	}
	// Add worker to list of available workers
	function addAvailableWorker(e) {
		e.preventDefault();
		var worker = $(this).parent().clone();
		worker.find('a.add').html("<i class=\"fa fa-plus-square-o\" aria-hidden=\"true\"></i> Add worker");
		// Add event listener for removing worker
		worker.find('a.add').click(addWorker);

		// Remove empty error if found
		if (availableWorkerList.find('p.empty'))
			availableWorkerList.find('p.empty').remove();
		availableWorkerList.append(worker);
		$(this).parent().remove();
		// Add text for empty list if list empty
		if ($.trim(workerList.html()) == '')
			workerList.append($('<p class="empty">There are no assigned workers</p>'));
	}
	return {
		start: start
	};
})();
