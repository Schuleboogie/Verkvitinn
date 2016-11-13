$(document).ready(function() {
	Site.start();
});

var Site = (function() {
	var deleteLink = $('#delete-link');

	function start() {
		// Add Icelandic date and time formatting for messages
		moment.locale();
		// Format all message and milestone times
		$('.message-time').each(formatTime);
		$('.milestone-time').each(formatTime);
		$('.project-time').each(formatTime);
		// Find elapsed time
		$('.project-elapsed').each(function() {
			var startTime = moment($(this).html());
			var now = moment();
			var elapsedTime = startTime.from(now);
			$(this).html(elapsedTime);
		});
		// Find duration of project
		$('.project-duration').each(function() {
			var startTime = moment($(this).attr('data-start'));
			var finishTime = moment($(this).attr('data-finish'));
			var duration = finishTime.diff(startTime, 'days');
			$(this).html(duration);
		});
		// Add link to delete project
		deleteLink.click(deleteProject);
		// Add delete link for all user messages
		$('.message-delete-link').click(deleteMessage);
		// Add toggle for opening milestones
		$('a.milestone-link').click(toggleMilestones);
		// Add handler for starting and finishing project
		$('a.start-finish-button').click(changeProject);
	}
	// Time formatting
	function formatTime() {
		var time = $(this);
		time.html(moment(time.html()).format('LLL'));
	}
	// Handler for clicking delete project
	function deleteProject(e) {
		e.preventDefault();
		// Prevent delete dialog from appearing again
		deleteLink.off('click');
		deleteLink.click(function(e) { e.preventDefault(); });
		var deleteDialog = $('<div class="delete-dialog" style="right: -20%"><a href="#" id="delete-yes"><span class="link-content">Delete</span></a><a href="#" id="delete-cancel"><span class="link-content">Cancel</span></a></div>');
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
	// Handler for clicking delete message
	function deleteMessage(e) {
		e.preventDefault();
		var messageDeleteLink = $(this);
		var messageBox = messageDeleteLink.parent();
		// Prevent delete dialog from appearing again
		messageDeleteLink.off('click');
		messageDeleteLink.click(function(e) { e.preventDefault(); });
		var deleteDialog = $('<div class="message-delete-dialog" style="right: -20%"><a href="#" id="message-delete-yes"><span class="link-content">Delete message</span></a> or <a href="#" id="message-delete-cancel"><span class="link-content">Cancel</span></a></div>');
		deleteDialog.find('#message-delete-yes').attr('data-href', $(this).attr('data-href'));
		// Delete
		deleteDialog.find('#message-delete-yes').click(function(e) {
			e.preventDefault();
			submitDeleteMessage($(this).attr('data-href'), messageBox);
		});
		// Cancel
		deleteDialog.find('#message-delete-cancel').click(function(e) {
			e.preventDefault();
			$(this).parent().remove();
			messageDeleteLink.off('click');
			messageDeleteLink.click(deleteMessage);
		});
		messageDeleteLink.click(function(e) {
			e.preventDefault();
			$(this).parent().find('.message-delete-dialog').remove();
			$(this).click(deleteMessage);
		});
		$(this).parent().append(deleteDialog);
	}
	// Delete message
	// url is delete GET path
	// messageBox is message object to remove after deletion
	function submitDeleteMessage(url, messageBox) {
		$.ajax('./' + url).done(function() {
			messageBox.remove();
			// Add info for no messages if there are no messages
			if ($('div.message').length == 0)  {
				var emptyAlert = $('<p class="empty">There are no messages</p>');
				$('div.message-form').after(emptyAlert);
			}
		});
	}
	// Toggle milestones
	function toggleMilestones(e) {
		e.preventDefault();
		var milestoneLink = $(this);
		if ($(this).hasClass('clicked')) {
			// Close milestones
			$('.other-milestone').each(function() {
				$(this).hide();
			});
			$(this).html('See other milestones');
			// Remove class clicked
			$(this).removeClass('clicked');
		}
		else {
			// Open milestones
			$('.other-milestone').each(function() {
				$(this).show();
			});
			$(this).html('Hide milestones');
			// Add class clicked
			$(this).addClass('clicked');
		}
	}
	// Changing project
	// Displaying "Are you sure?" dialog
	function changeProject(e) {
		e.preventDefault();
		var buttonClicked = $(this);
		var buttonClickedCopy = buttonClicked.clone();
		var yesButton = buttonClicked.clone();
		var noButton = buttonClicked.clone();
		yesButton.html('Yes');
		noButton.html('Cancel');
		noButton.attr('href', '#');
		noButton.click(function(e) {
			e.preventDefault();
			buttonClickedCopy.click(changeProject);
			noButton.after(buttonClickedCopy);
			yesButton.remove();
			noButton.remove();
			$('p#sure').remove();
		});

		buttonClicked.after(yesButton);
		yesButton.after(noButton);
		buttonClicked.after($('<p id="sure">Are you sure?</p>'));
		buttonClicked.remove();
	}
	return {
		start: start
	};
})();
