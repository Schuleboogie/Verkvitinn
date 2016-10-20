package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.persistence.entities.Message;
import project.persistence.repositories.MessageRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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
		return messages.findByProjectId(projectId);
	}

	// Delete message
	public void delete(Message message) {
		messages.delete(message);
	}
}
