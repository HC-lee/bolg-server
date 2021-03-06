package com.supcoder.blog.service.impl;

import com.supcoder.blog.model.domain.Comment;
import com.supcoder.blog.model.dto.SiteConfig;
import com.supcoder.blog.service.ConfigService;
import com.supcoder.blog.service.EmailService;
import com.supcoder.blog.service.LogService;
import com.supcoder.blog.util.FameConsts;
import com.supcoder.blog.util.Types;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 发送邮件 Service 实现类
 *
 * @author zbw
 * @since 2018/4/9 15:52
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class EmailServiceImpl implements EmailService {

    @Autowired
    private ConfigService configService;

    @Autowired
    private LogService logService;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    @Async
    public void sendEmailToAdmin(Comment comments) {
        SiteConfig config = configService.getSiteConfig();
        if (null == config || !config.isEmailSend()) {
            return;
        }
        Context context = new Context();
        context.setVariable("name", comments.getName());
        context.setVariable("content", comments.getContent());
        context.setVariable("articleId", comments.getArticleId());
        String content = templateEngine.process("mail_admin", context);
        String logData = content + ";  发送给管理员";
        log.info("sendEmailToAdmin start: {}", new Date().toString());
        try {
            sendEmail(FameConsts.EMAIL_TEMPLATE_DEFAULT_SUBJECT, content, config.getEmailUsername());
            logService.save(Types.LOG_ACTION_SEND_EMAIL, logData, Types.LOG_MESSAGE_SEND_EMAIL_SUCCESS, Types.LOG_TYPE_EMAIL);
        } catch (Exception e) {
            logService.save(Types.LOG_ACTION_SEND_EMAIL, logData, Types.LOG_MESSAGE_SEND_EMAIL_FAIL, Types.LOG_TYPE_EMAIL);
            log.error(e.getMessage());
        }
    }

    @Override
    @Async
    public void sendEmailToUser(Comment comments, String replyEmail) {
        SiteConfig config = configService.getSiteConfig();
        if (null == config || !config.isEmailSend()) {
            return;
        }
        Context context = new Context();
        context.setVariable("name", comments.getName());
        context.setVariable("content", comments.getContent());
        context.setVariable("articleId", comments.getArticleId());
        String content = templateEngine.process("mail_user", context);
        String logData = content + ";  发送给:" + replyEmail;
        log.info("sendEmailToUser start: {}", new Date().toString());
        try {
            sendEmail(FameConsts.EMAIL_TEMPLATE_DEFAULT_SUBJECT, content, replyEmail);
            logService.save(Types.LOG_ACTION_SEND_EMAIL, logData, Types.LOG_MESSAGE_SEND_EMAIL_SUCCESS, Types.LOG_TYPE_EMAIL);
        } catch (Exception e) {
            logService.save(Types.LOG_ACTION_SEND_EMAIL, logData, Types.LOG_MESSAGE_SEND_EMAIL_FAIL, Types.LOG_TYPE_EMAIL);
            log.error(e.getMessage());
        }
    }

    /**
     * 发送邮件
     *
     * @param subject 邮件标题
     * @param content 邮件内容(html)
     * @param to      收件人
     * @throws MessagingException
     */
    private void sendEmail(String subject, String content, String to) throws MessagingException {
        SiteConfig config = configService.getSiteConfig();
        JavaMailSender mailSender = (JavaMailSender) mailSender(config.getEmailHost(), config.getEmailPort(),
                config.getEmailUsername(), config.getEmailPassword());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(config.getEmailUsername());
        helper.setTo(to);
        helper.setText(content, true);
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
    }


    /**
     * 获取MailSender
     *
     * @param host     主机名
     * @param port     端口
     * @param username 邮件名
     * @param password 密码
     * @return MailSender
     */
    private MailSender mailSender(String host, Integer port, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }
}
