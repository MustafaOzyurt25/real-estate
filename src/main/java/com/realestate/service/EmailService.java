package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendAdvertCreationEmail(String to, Advert advert) {


        String advertTitle = advert.getTitle();
        String advertDistrict = advert.getDistrict().getName();
        String advertCity = advert.getCity().getName();
        Double advertPrice = advert.getPrice();
        String userFullName = advert.getUser().getFirstName() + " " + advert.getUser().getLastName();


        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "Your advert has started to be published";

            helper.setTo(to);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("userFullName", userFullName);
            context.setVariable("advertTitle", advertTitle);
            context.setVariable("advertDistrict", advertDistrict);
            context.setVariable("advertCity", advertCity);
            context.setVariable("advertPrice", advertPrice);

            String emailContent =templateEngine.process("EmailContentForAdvert", context);
            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendTourRequestGuestCreationEmail(String to, TourRequest tourRequest) {

        LocalDate tourDate = tourRequest.getTourDate();
        LocalTime tourTime = tourRequest.getTourTime();
        String guestUserFullName = tourRequest.getGuestUser().getFirstName() + " " + tourRequest.getGuestUser().getLastName();


        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Your tour request has been created successfully.");

            Context guestContext = new Context();
            guestContext.setVariable("guestUserFullName", guestUserFullName);
            guestContext.setVariable("tourDate", tourDate);
            guestContext.setVariable("tourTime", tourTime);


            String processedHtmlForGuest = templateEngine.process("EmailContentForTourRequestGuest", guestContext);
            helper.setText(processedHtmlForGuest, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendTourRequestOwnerCreationEmail(String to, TourRequest tourRequest) {

        LocalDate tourDate = tourRequest.getTourDate();
        LocalTime tourTime = tourRequest.getTourTime();
        String ownerUserFullName = tourRequest.getOwnerUser().getFirstName() + " " + tourRequest.getOwnerUser().getLastName();

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Your tour request has been created successfully.");

            Context ownerContext = new Context();
            ownerContext.setVariable("ownerUserFullName", ownerUserFullName);
            ownerContext.setVariable("tourDate", tourDate);
            ownerContext.setVariable("tourTime", tourTime);

            String processedHtmlForOwner = templateEngine.process("EmailContentForTourRequestOwner", ownerContext);
            helper.setText(processedHtmlForOwner, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
