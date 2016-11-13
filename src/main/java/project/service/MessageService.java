package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Message;
import project.persistence.repositories.MessageRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Service
public class MessageService {
	// Message repository
	MessageRepository messages;

	// Dependency Injection
	@Autowired
	public MessageService(MessageRepository messages) {
		this.messages = messages;
	}

	// Create message
	public Message create(Message newMessage) {
		// Save message
		return messages.save(newMessage);
	}

	// Find messages by author
	public List<Message> findByProjectId(Long projectId) {
		List<Message> foundMessages = messages.findByProjectId(projectId);
		// Sort messages by timestamp
		Collections.sort(foundMessages, new Comparator<Message>() {
			public int compare(Message m1, Message m2) {
				if (m1.getTimestamp() == null || m2.getTimestamp() == null)
					return 0;
				return m2.getTimestamp().compareTo(m1.getTimestamp());
			}
		});
		return foundMessages;
	}

	// Find message by id
	public Message findOne(Long messageId) {
		return messages.findOne(messageId);
	}

	// Delete message
	public void delete(Message message) {
		messages.delete(message);
	}
}
