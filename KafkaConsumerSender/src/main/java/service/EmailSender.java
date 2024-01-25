package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope("prototype")
public class EmailSender{

    @Autowired
    public JavaMailSender sender;

    @Value("${mail.sender}")
    private String senderFrom;

    public void sendSimpleEmail(String toAddress, String subject, String text)throws MailException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(subject);
        msg.setFrom(this.senderFrom);
        msg.setTo(toAddress);
        msg.setText(text);
        sender.send(msg);
    }
}
