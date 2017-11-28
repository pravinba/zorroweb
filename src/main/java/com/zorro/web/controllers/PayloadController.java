package com.zorro.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayloadController {
	
	private static final String PAYLOAD_VIEW_NAME = "payload/payload";
	
	@RequestMapping("/payload")
	public String getPayload() {		
		return PAYLOAD_VIEW_NAME;
	}

}
