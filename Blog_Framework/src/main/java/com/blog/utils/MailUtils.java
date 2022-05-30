package com.blog.utils;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailUtils {
    @Autowired
    JavaMailSenderImpl mailSender;
    //发送方邮箱
    private  final String senter="chenbyf1314@foxmail.com";

    /**发送普通文本邮件不带附件
     *
     * @param subject 邮件标题
     * @param context  邮件正文
     * @param acceptor  接收方邮箱
     * @return true/false
     */
    @Async
    public  boolean sendSimply(String subject, String context, String... acceptor){
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject(subject);
        //设置邮件内容
        message.setText(context);
        //设置接收方邮箱
        message.setTo(acceptor);

        message.setFrom(senter);
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            throw new RuntimeSqlException("发生未知错误！");
        }
    }

    /**发送普通文本邮件带附件
     * @param subject 邮件标题
     * @param filePath 附件路径数组
     * @param context   邮件正文 可以是text字符串还可以是Html文本
     * @param acceptor  接收方邮箱
     * @return   true/false
     * @throws MessagingException
     */
    @Async
    public boolean sendWithAccessory(String subject,String[] filePath, String context, String... acceptor) throws MessagingException {

        MimeMessage message= mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //设置邮件标题
        helper.setSubject(subject);
        //设置邮件内容
        helper.setText(context,true);
        //设置接收方邮箱
        helper.setTo(acceptor);

        helper.setFrom(senter);
        //添加附件
        for (String file: filePath) {
            File file1 = new File(file);
            String name = file1.getName();
            helper.addAttachment(name,file1);
        }
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            throw new RuntimeSqlException("发生未知错误！");
        }

    }


}
