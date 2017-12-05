package com.zorro.backend.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class I18NService {

	@Autowired
	private MessageSource messageSource;

	private static final Logger LOG = LoggerFactory.getLogger(I18NService.class);
	
	public String getMessage(String messageId) {
		LOG.info("Returning  i18n text for message:", messageId);
		LOG.trace("Hello World!");
		LOG.debug("How are you today?");
		LOG.info("I am fine.");
		LOG.warn("I love programming.");
		LOG.error("I am programming.");
		
		Locale locale = LocaleContextHolder.getLocale();
		System.out.println(locale.getCountry());
		return getMessage(messageId,locale);
	}
	
	public String getMessage(String messageId,Locale locale) {
		return messageSource.getMessage(messageId,null,locale);
	}
}
