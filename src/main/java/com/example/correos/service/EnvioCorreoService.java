package com.example.correos.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.correos.entity.InfoEmail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.activation.FileDataSource;
import jakarta.mail.internet.MimeMessage;

@Service
public class EnvioCorreoService {

	@Autowired
	public Configuration config;
	
	@Autowired
	public JavaMailSender mailSender;
	
	public InfoEmail sendEmail() {
		InfoEmail respEmail = new InfoEmail();
		String template = "email-template-ticket.html";
		String asunto = "Prueba email";
		
		
		Map<String,Object> model = new HashMap<>();
				
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			
			Template t = config.getTemplate(template);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			
			helper.setTo("Se agrega el correo detinatario");
			helper.setText(html, true);
			helper.setSubject(asunto);
			helper.setFrom("Se agrega el correo emisor");
			// Linea para agregar adjuntos al envio de correo
//			helper.addAttachment("hola.pdf", new FileDataSource("E:\\andre\\Documents\\tmp\\MANAGER\\TK\\85059-35401.mng"));
			
			System.out.println(message == null);
			if(message != null) {
				mailSender.send(message);
				respEmail.setRi_error_nro(0);
				respEmail.setRv_state("Correo Enviado");
				respEmail.setRv_error_des("");
			} else {
				respEmail.setRi_error_nro(999);
				respEmail.setRv_state("Correo No Enviado");
				respEmail.setRv_error_des("EMAIL DEL SUSCRIPTOR INCORRECTO1");
			}
		} catch (Exception e) {
			respEmail.setRi_error_nro(999);
			respEmail.setRv_state("Correo No Enviado");
			respEmail.setRv_error_des("EMAIL DEL SUSCRIPTOR INCORRECTO2");
			System.out.println(e);
		}
		
		return respEmail;
	}
	
}
