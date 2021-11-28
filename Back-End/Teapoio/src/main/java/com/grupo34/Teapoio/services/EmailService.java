package com.grupo34.Teapoio.services;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

import com.grupo34.Teapoio.domain.Usuario;

public interface EmailService {

	void sendEmail(SimpleMailMessage msg);

	void sendHtmlEmail(MimeMessage msg);

	void sendResetHtmlEmail(Usuario usuario, String newPass);

	void sendNewPasswordEmail(Usuario usuario, String newPass);
}