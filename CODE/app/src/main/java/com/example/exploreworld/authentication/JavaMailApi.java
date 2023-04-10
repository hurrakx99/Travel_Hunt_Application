package com.example.exploreworld.authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Config;
import android.widget.Toast;

import com.example.exploreworld.payment.Google_pay;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailApi extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;
    private String email;
    private String msubject;
    private String msg;

     public JavaMailApi(Context context, String email, String msubject, String msg) {
        this.context = context;
       this.email = email;
        this.msubject = msubject;
        this.msg = msg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
            Properties properties=new Properties();
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.socketFactory.port","465");
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.port","465");
            Session session=Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Utils.EMAIL,Utils.PASSWORD);

                }
            });

            try {
                Message message=new MimeMessage(session);
                message.setFrom(new InternetAddress(Utils.EMAIL));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
                message.setSubject(msubject);
                message.setText(msg);
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }


        return null;
    }
}
