package com.zorro.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class smtpEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(smtpEmailService.class); 
	
	@Autowired
	private MailSender mailsender;
	
	@Override
	public void sendGenericEmailMessage(SimpleMailMessage message) {
		// TODO Auto-generated method stub
		LOG.debug("Invoking actual smtp email service...");		
		mailsender.send(message);		
		LOG.debug("Email is sent...");
		
	}

}
