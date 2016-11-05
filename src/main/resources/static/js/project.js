$(document).ready(function() {
	Site.start();
});

var Site = (function() {
	var deleteLink = $('#delete-link');

	function start() {
		// Add link to delete project
		deleteLink.click(deleteProject);
		// Add Icelandic date and time formatting for messages
		moment.locale('is');
		// Format all message times
		$('.message-time').each(function() {
			$(this).html(moment($(this).html()).format('LLL'));
		});
	}
	// Handler for clicking delete project
	function deleteProject(e) {
		e.preventDefault();
		// Prevent delete dialog from appearing again
		deleteLink.off('click');
		deleteLink.click(function(e) { e.preventDefault(); });
		var deleteDialog = $('<div class="delete-dialog" style="right: -20%"><a href="#" id="delete-yes"><span class="link-content">Yes</span></a><a href="#" id="delete-cancel"><span class="link-content">No</span></a></div>');
		deleteDialog.find('#delete-yes').attr('href', $(this).attr('href'));
		deleteDialog.find('#delete-cancel').click(function(e) {
			e.preventDefault();
			$(this).parent().animate({
				right: "-20%"
			}, 400, function() {
				// Animation done
				$(this).remove();
			});
			deleteLink.click(deleteProject);
		});
		$(this).parent().append(deleteDialog);
		deleteDialog.animate({
			right: "0"
		}, 400);
	}
	return {
		start: start
	};
})();
