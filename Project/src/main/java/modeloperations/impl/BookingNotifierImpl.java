package modeloperations.impl;

import exceptions.MailAuthenticationException;
import exceptions.SendMailException;
import modeloperations.BookingNotifier;
import org.apache.commons.lang3.StringUtils;


import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class BookingNotifierImpl implements BookingNotifier {
    //по-хорошему в код это зашивать нельзя. Лучше хранить в файле на сервере
    private final String SUBJECT = "Кинотеатр ПОП_Кино: Бронирование";
    private final String SMTP_HOST = "smtp.google.com";
    private final String SMTP_PORT = "587";
    private final String ENCODING = "UTF-8";
    private final String FROM = "cinema.popkino@gmail.com";
    @Resource(name = "java:jboss/mail/gmail")
    private Session wildFlysession;

    public BookingNotifierImpl() {
    }

    public void sendKeyToContacts(String key, String contacts) throws SendMailException {
        String content = "Код бронирования ваших билетов:\n" + key;
        Authenticator auth = null;
        try {
//            auth = new MyAuthenticator();
//            Properties props = System.getProperties();
//            props.put("mail.transport.protocol" , "smtp");
//            props.put("mail.smtp.port", SMTP_PORT);
//            props.put("mail.smtp.host", SMTP_HOST);
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.mime.charset", ENCODING);
//            Session session = Session.getInstance(props, auth);

            Message msg = new MimeMessage(wildFlysession);
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(contacts));
            msg.setSubject(SUBJECT);
            msg.setText(content);
            Transport.send(msg);
        } catch (Exception e) {
            throw new SendMailException(e.getMessage());
        }
    }

    private class MyAuthenticator extends Authenticator {
        private String login;
        private String password;

        public MyAuthenticator() throws IOException, MailAuthenticationException {
            login = "cinema.popkino@gmail.com";
            password = "cinema6413";
//            login = "cinema.popkino";
//            password = "cinema";
            if ((StringUtils.isBlank(login)) && (StringUtils.isBlank(password))) {
                throw new MailAuthenticationException("Login and password not found");
            }
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(login,password);
        }
    }
}


