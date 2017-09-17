package com.github.frajimiba.commonstruct.test.healthcheck;

import java.util.Properties;
import javax.mail.Session;

import com.github.frajimiba.commonstruct.healthcheck.AbstractMailSessionHealthCheck;
import com.github.frajimiba.commonstruct.mail.MailSession;

public class KoMailSessionHealthCheck extends AbstractMailSessionHealthCheck {

  public static final int PORT = 9966;
  public static final int TIMEOUT = 20000;
  
  @Override
  public MailSession getMailSession() {
    return new MailSession(){
      @Override
      public Session getMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.user", "user2");
        props.put("mail.smtp.timeout", Integer.toString(TIMEOUT));
        props.put("mail.smtp.host", "externalhost");
        props.put("mail.smtp.auth", Boolean.toString(true));
        props.put("mail.smtp.password", "password2");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", Boolean.toString(false));
        props.put("mail.smtp.from", "user2@test.com");
        props.put("mail.smtp.port", Integer.toString(PORT));
        return Session.getInstance(props);
      }
    };
  }

  @Override
  public String getFrom() {
    return "testFrom";
  }

  @Override
  public String getTo() {
    return "testTo";
  }

  @Override
  public String getMessage() {
    return "testMessage";
  }

  @Override
  public String getSubject() {
    return "testSubject";
  }

  @Override
  public String getName() {
    return "TestMailSessionHealthCheck";
  }

}
