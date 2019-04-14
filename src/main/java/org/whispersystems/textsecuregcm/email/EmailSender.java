/**
 * by @W!L 2019
 */
package org.whispersystems.textsecuregcm.email;

import org.whispersystems.textsecuregcm.configuration.EmailConfiguration;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import java.io.IOException;
import java.util.ArrayList;

public class EmailSender {

	private final String smtpUser;
	private final String smtpPass;
	private final String smtpHost;
	private final String smtpPort;
	private final String email;
	private final boolean allDomainsAllowed;
	private final ArrayList<String> allowedDomains;

    private final String SUBJECT = "Verification Code";
    
    private final String BODY = String.join(
		System.getProperty("line.separator"),
		"<h1>{service_name(change it)}</h1>",
		"<p>{service_message(change it)} : {code}.</p>"
	);

	public EmailSender(EmailConfiguration config) {
		this.smtpUser 		   = config.getSmtpUser();
		this.smtpPass 		   = config.getSmtpPass();
		this.smtpHost 		   = config.getSmtpHost();
		this.smtpPort 		   = config.getSmtpPort();
		this.email 	  		   = config.getEmail();
		this.allDomainsAllowed = config.allDomainsAllowed();
		this.allowedDomains    = new ArrayList<String>(config.getAllowedDomains());
	}

	public void deliverEmailVerification(String destination, String verificationCode) 
		throws IOException
	{
		if (allDomainsAllowed || validateEmailDomain(destination)) {
			Properties properties = System.getProperties();

			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.host", smtpHost);
			properties.put("mail.smtp.socketFactory.port", smtpPort);
			properties.put("mail.smtp.port", smtpPort);
			properties.put("mail.auth", "true");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getDefaultInstance(properties,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(smtpUser, smtpPass);
				}
			});

			try {
				MimeMessage message = new MimeMessage(session);

				message.setFrom(new InternetAddress(email));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
				message.setSubject(SUBJECT);
				message.setContent(BODY.replace("{code}", verificationCode),"text/html; charset=UTF-8");

				Transport.send(message, smtpUser, smtpPass);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean validateEmailDomain(String email) {
		String domain = email.split("@")[1];

		if (allowedDomains.contains(domain)) {
			return true;
		} else {
			return false;	
		}
	}
}
