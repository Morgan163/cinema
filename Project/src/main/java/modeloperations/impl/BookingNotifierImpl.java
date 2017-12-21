package modeloperations.impl;

import exceptions.MailAuthenticationException;
import exceptions.SendMailException;
import modeloperations.BookingNotifier;
import org.apache.commons.lang3.StringUtils;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class BookingNotifierImpl implements BookingNotifier {
    //по-хорошему в код это зашивать нельзя. Лучше хранить в файле на сервере
    private final String SUBJECT = "Кинотеатр такой то: Бронирование";
    private final String SMTP_HOST = "smtp.yandex.ru";
    private final String SMTP_PORT = "25";
    private final String ENCODING = "UTF-8";
    private final String FROM = "styartmc@yandex.ru";

    public void sendKeyToContacts(String key, String contacts) throws SendMailException {
        String content = "Код бронирования ваших билетов:\n" + key;
        Authenticator auth = null;
        try {
            auth = new MyAuthenticator();
            Properties props = System.getProperties();
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", "true");
            props.put("mail.mime.charset", ENCODING);
            Session session = Session.getDefaultInstance(props, auth);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(contacts));
            msg.setSubject(SUBJECT);
            msg.setText(content);
            Transport.send(msg);
        } catch (IOException |MailAuthenticationException | MessagingException  e) {
            throw new SendMailException(e.getMessage());
        }
    }

    private class MyAuthenticator extends Authenticator {
        private String login;
        private String password;

        public MyAuthenticator() throws IOException, MailAuthenticationException {
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("auth.txt")));
            login = bufferedReader.readLine();
            password = bufferedReader.readLine();
            if ((StringUtils.isBlank(login)) && (StringUtils.isBlank(password))) {
                throw new MailAuthenticationException("Login and password not found");
            }
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(login,password);
        }
    }
}


