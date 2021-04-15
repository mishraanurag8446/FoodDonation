package com.example.fooddonate.operations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail extends AsyncTask<Void, Void,Void> {

    private Context context;

    private Session session;
    private String email, name;
    private String from = "mishraanurag171@gmail.com";
    private String pass = "Anubaba123";


    public SendMail(Context context, String email, String name) {
        this.context = context;
        this.email = email;
        this.name = name;
    }
//    public Boolean sendMail()
//    {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.socketFactory.port", "465");
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.port", "465");
//
//        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from,pass);
//            }
//        });
//
//        MimeMessage mimeMessage = new MimeMessage(session);
//        try {
//            mimeMessage.setFrom(new InternetAddress(from));
//            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
//            mimeMessage.setSubject("you got recipient");
//            mimeMessage.setText("Your recipient is "+name);
//            Transport.send(mimeMessage);
//            Log.d("d","mail send successfully");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,pass);
            }
        });
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject("you got recipient");
            mimeMessage.setText("Your recipient is "+name);
            Transport.send(mimeMessage);
            Log.d("d","mail send successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//import android.os.StrictMode;
//
//import java.util.Properties;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class SendMail {
////    String to = "mishraanurag7020@gmail.com";
////    String from = "mishra@gmail.com";
//    final String username = "mishraanurag171@gmail.com";
//    final String password = "Anubaba123";
//    String host = "smtp.gmail.com";
//    Properties props;
//    Message message;
//    public SendMail() {
//        props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "2525");
//    }
//
//    public void createMassage(String to,String name)
//    {
//        Session session = Session.getInstance(props,
//                new Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//            // Create a default MimeMessage object
//            try {
//                message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(username));
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//                message.setSubject("HTML message with an image and attachment");
//                // Put your HTML content here as well as refer to the hosted image
//
////                message.setContent("<p>You got Recipient "+name+"</p>", "text/html");
//                message.setText("you got recipient "+name);
//                Transport.send(message);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//
//    }
//    public  void sendMail()
//    {
////        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy().Builder().permitAll().build();
////        StrictMode.setThreadPolicy(policy);
////        try {
////            Transport.send(message);
////        } catch (MessagingException e) {
////            e.printStackTrace();
////        }
//    }
//
//}
//}