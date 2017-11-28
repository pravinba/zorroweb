package com.zorro.backend.service;

import org.springframework.mail.SimpleMailMessage;

import com.zorro.web.domain.frontend.FeedbackPojo;

public interface EmailService {
	
	/**
	 * Sends email with content in parameter
	 * @param feedbackPojo
	 */
	public void sendFeedbackEmail(FeedbackPojo feedbackPojo);
	
	
	
	/**
	 * Send an email with the content of SimpleMessage object
	 * @param message
	 */
	public void sendGenericEmailMessage(SimpleMailMessage message);

}
