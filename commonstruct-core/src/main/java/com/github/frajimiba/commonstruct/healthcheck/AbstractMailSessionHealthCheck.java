package com.github.frajimiba.commonstruct.healthcheck;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.github.frajimiba.commonstruct.util.MailSessionUtil;

/**
 * Common MailSession HealthCheck.
 * 
 * @author Francisco José Jiménez
 *
 */
public abstract class AbstractMailSessionHealthCheck implements MailSessionHealthCheck {

  private static final Logger LOG = Logger.getLogger(AbstractMailSessionHealthCheck.class.getName());

  private HealthCheckStatus status;
  /**
   * {@inheritDoc}
   */
  @Override
  public HealthCheckStatus getStatus() {
    return this.status;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void check() {
    this.status = HealthCheckStatus.KO;
    Session session = this.getMailSession().getMailSession();
    if (MailSessionUtil.isAuthenticateSession(session)){
      session = MailSessionUtil.getAuthenticatedMailSession(session);
    }
    
    try {

      MimeMultipart multipart = new MimeMultipart();
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(getFrom()));
      msg.setRecipient(Message.RecipientType.TO, new InternetAddress(getTo()));

      String subject = MimeUtility.encodeText(this.getSubject(), "utf-8", "B");
      msg.setSubject(subject);

      msg.setSentDate(new Date());
      MimeBodyPart mbp = new MimeBodyPart();
      mbp.setContent(getMessage(), "text/html; charset=utf-8");
      multipart.addBodyPart(mbp);
      msg.setContent(multipart);
      Transport.send(msg);
      this.status = HealthCheckStatus.OK;

    } catch (UnsupportedEncodingException ex) {
      LOG.log(Level.SEVERE, "ERROR AL CHECKEAR MAILSESSION", ex);
    } catch (MessagingException ex) {
      LOG.log(Level.SEVERE, "ERROR AL CHECKEAR MAILSESSION", ex);
    }
  }
}
