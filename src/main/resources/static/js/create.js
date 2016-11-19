$(document).ready(function() {
	Site.start();
});

var Site = (function() {
	var workerList = $('ul#workers');
	var availableWorkerList = $('ul#availWorkers');

	function start() {
		// Add listeners for adding workers
		availableWorkerList.find('li a.add').each(function() {
			$(this).click(addWorker);
		});
		// Add listeners for removing worker from list of assigned workers
		// and adding to available workers
		workerList.find('li a.add').each(function() {
			$(this).click(addAvailableWorker);
		});
		// Add listeners for adding head worker in available workers
		workerList.find('li a.set-head-worker').click(addHeadWorker);
		// Add listeners for removing head workers from available workers
		workerList.find('li a.remove-head-worker').click(removeHeadWorker);
	}
	// Add worker to list of workers on job
	function addWorker(e) {
		e.preventDefault();
		var worker = $(this).parent().clone();
		worker.find('a.add').html("<i class=\"fa fa-minus-square-o\" aria-hidden=\"true\"></i> Remove worker");
		// Add link for setting worker as head worker
		var addHeadWorkerLink = $('<a href="#" class="set-head-worker"><i class="fa fa-star-o" aria-hidden="true"></i> Add as head worker</a>');
		addHeadWorkerLink.click(addHeadWorker);
		worker.find('a.add').after(addHeadWorkerLink);
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
		// Remove link for setting worker or removing worker as head worker
		worker.find('a.set-head-worker').remove();
		worker.find('a.remove-head-worker').remove();
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
	// Add worker to head workers
	function addHeadWorker(e) {
		e.preventDefault();
		$(this).html('<i class="fa fa-eraser" aria-hidden="true"></i> Remove from head workers');
		$(this).off('click');
		$(this).click(removeHeadWorker);
		// Set information for posting to server
		// Set name attribute for hidden input headWorkers
		$(this).parent().find('.is-head-worker').attr('name', 'headWorkers');
	}
	// Remove worker from head workers
	function removeHeadWorker(e) {
		e.preventDefault();
		$(this).html('<i class="fa fa-star-o" aria-hidden="true"></i> Add as head worker');
		$(this).off('click');
		$(this).click(addHeadWorker);
		// Set information for posting to server
		// Remove name attribute from hidden input headWorkers
		$(this).parent().find('.is-head-worker').removeAttr('name');
	}
	return {
		start: start
	};
})();
