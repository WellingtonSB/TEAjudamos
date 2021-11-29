package com.grupo34.Teapoio.services;


import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.grupo34.Teapoio.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(usuario.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}

	
	protected String htmlFromTemplateUsuario(Usuario obj, String newPass) {
		Context context = new Context();
		context.setVariable("usuario", obj);
		context.setVariable("password", newPass);
		return templateEngine.process("email/resetDeSenha", context);
	}
	
	
	@Override
	public void sendResetHtmlEmail(Usuario obj, String newPass) {
		try {
			MimeMessage mm = prepareMimeMessageFromUsuario(obj, newPass);
			sendHtmlEmail(mm);
		}
		catch(MessagingException e) {
			sendNewPasswordEmail(obj, newPass);
			
		}
	}
	
	protected MimeMessage prepareMimeMessageFromUsuario(Usuario obj, String newPass) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Você solicitou uma nova senha de acesso");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateUsuario(obj, newPass),true);
		return mimeMessage;
	}
	
	
}
