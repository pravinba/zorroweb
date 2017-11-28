package com.zorro.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.zorro.web.domain.frontend.FeedbackPojo;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.to.address}")
	private String defaultToAddress;
	
	protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedbackpojo) {		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(defaultToAddress);
		message.setFrom(feedbackpojo.getEmail());
		message.setSubject("ZorroWeb Feedback received from "+feedbackpojo.getFirstName()+" "+feedbackpojo.getLastName() );
		message.setText(feedbackpojo.getFeedback());
		return message;
	}
	
	
	@Override
	public void sendFeedbackEmail(FeedbackPojo feedbackpojo) {
		sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedbackpojo));
	}
	
}
