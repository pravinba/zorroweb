package com.zorro.web.controllers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zorro.backend.persistence.domain.backend.PasswordResetToken;
import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.backend.service.EmailService;
import com.zorro.backend.service.I18NService;
import com.zorro.backend.service.PasswordResetTokenService;
import com.zorro.backend.service.UserService;
import com.zorro.utils.UserUtils;

@Controller
public class ForgotMyPasswordController {

	public static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
	
	public static final String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailForm";
	
	public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgotmypassword";
	
	public static final String MAIL_SENT_KEY="mailSent";
	
	public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";
	
	public static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgotmypassword.email.text";
	
	public static final String CHANGE_PASSWORD_VIEW_NAME = "forgotmypassword/changePassword";
	
    private static final String PASSWORD_RESET_ATTRIBUTE_NAME = "passwordReset";

    private static final String MESSAGE_ATTRIBUTE_NAME = "message";
	
	@Autowired
	private I18NService i18nService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Value("S{webmaster.email}")
	private String webMasterEmail;
	
	
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method=RequestMethod.GET)
	public String forgotPasswordGet() {		
		return EMAIL_ADDRESS_VIEW_NAME;
	}
	
	@RequestMapping(value = FORGOT_PASSWORD_URL_MAPPING, method=RequestMethod.POST)
	public String forgotPasswordPost(HttpServletRequest request, @RequestParam("email") String email, ModelMap model) {
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);
		if(null == passwordResetToken) {
			LOG.info("Could not find a password reset token for email {}",email);
		}
		else
		{
			User user = passwordResetToken.getUser();
			String token = passwordResetToken.getToken();
			String resetPasswordUrl = UserUtils.createPasswordResetUrl(request, user.getId(), token);
			LOG.info("Token value{}",passwordResetToken);
			LOG.info("Username {}",passwordResetToken.getUser().getUsername());
			LOG.info("Reset Password Url {}",resetPasswordUrl);
			
			String emailText = i18nService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME,request.getLocale());
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom("zorrowebapp@support.com");
			mailMessage.setTo(webMasterEmail);
			mailMessage.setSubject("[ZorroWeb] Password Reset Email");
			mailMessage.setText(emailText + "\r\n" + resetPasswordUrl);
			
			//emailService.sendGenericEmailMessage(mailMessage);
			
		}
		
		model.addAttribute(MAIL_SENT_KEY,"true");
		
		return EMAIL_ADDRESS_VIEW_NAME;
	}
	
	@RequestMapping(value = CHANGE_PASSWORD_PATH, method=RequestMethod.GET)
	public String changePasswordGet(@RequestParam("id") long id,
									@RequestParam("token") String token,
									Locale locale,
									ModelMap model) {
		
		 if (StringUtils.isEmpty(token) || id == 0) {
	            LOG.error("Invalid user id {}  or token value {}", id, token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Invalid user id or token value");
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

	        if (null == passwordResetToken) {
	            LOG.warn("A token couldn't be found with value {}", token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Token not found");
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        User user = passwordResetToken.getUser();
	        if (user.getId() != id) {
	            LOG.error("The user id {} passed as parameter does not match the user id {} associated with the token {}",
	                    id, user.getId(), token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, i18nService.getMessage("resetPassword.token.invalid", locale));
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
	            LOG.error("The token {} has expired", token);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, i18nService.getMessage("resetPassword.token.expired", locale));
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        model.addAttribute("principalId", user.getId());

	        // OK to proceed. We auto-authenticate the user so that in the POST request we can check if the user
	        // is authenticated
	        Authentication auth = new UsernamePasswordAuthenticationToken(
	                user, null, user.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(auth);

		LOG.info("Control Being Transferred to Change Password Page");
		return CHANGE_PASSWORD_VIEW_NAME;
	}
	
	 @RequestMapping(value = CHANGE_PASSWORD_PATH, method = RequestMethod.POST)
	    public String changeUserPasswordPost(@RequestParam("principal_id") long userId,
	                                         @RequestParam("password") String password,
	                                         ModelMap model) {

	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (null == authentication) {
	            LOG.error("An unauthenticated user tried to invoke the reset password POST method");
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request.");
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        User user = (User) authentication.getPrincipal();
	        if (user.getId() != userId) {
	            LOG.error("Security breach! User {} is trying to make a password reset request on behalf of {}",
	                    user.getId(), userId);
	            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
	            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request.");
	            return CHANGE_PASSWORD_VIEW_NAME;
	        }

	        userService.updateUserPassword(userId, password);
	        LOG.info("Password successfully updated for user {}", user.getUsername());

	        model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "true");

	        return CHANGE_PASSWORD_VIEW_NAME;

	    }
}
