package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
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

            String emailContent = buildEmailContentForAdvert(userFullName, advertTitle, advertDistrict, advertCity, advertPrice);
            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildEmailContentForAdvert(String userFullName, String advertTitle, String district, String city, Double price) {
        return String.format(
                "<html><body>" +
                        "<div style='background-color: #ffffff; color: #000000; padding: 10px;'>" +
                        "<p style='text-align: center; font-size: 18px; font-weight: bold;'>Dear %s,</p>" +
                        "<p>Your advert has started to be published on our ritzy homes platform.</p>" +
                        "<p>Here are the details of your advert:</p>" +
                        "<div style='background-color: #6cb533; padding: 10px;'>" +
                        "<p style='font-weight: bold;'>%s</p>" +
                        "<p>%s, %s</p>" +
                        "<p>Price: %s $</p>" +
                        "</div>" +
                        "<p>Thank you for choosing our platform to list your property. Your advert is now visible to potential buyers and renters.</p>" +
                        "<p>If you have any questions or need further assistance, feel free to reach out to our customer support team.</p>" +
                        "<p style='font-style: italic;'>Best regards,<br>Ritzy Homes</p>" +
                        "</div>" +
                        "</body></html>",
                userFullName, advertTitle, district, city, price);
    }

    public void sendTourRequestCreationEmail(String to, TourRequest tourRequest) {


        LocalDate tourDate = tourRequest.getTourDate();
        LocalTime tourTime = tourRequest.getTourTime();
        String userFullName = tourRequest.getGuestUser().getFirstName() + " " + tourRequest.getGuestUser().getLastName();


        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "Your tour request has been created successfully.";

            helper.setTo(to);
            helper.setSubject(subject);

            String emailContent = buildEmailContentForTourRequest(userFullName, tourDate, tourTime);
            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private String buildEmailContentForTourRequest(String userFullName, LocalDate tourDate, LocalTime tourTime) {

        return String.format(
                "<html><body>" +
                        "<div style='background-color: #ffffff; color: #000000; padding: 10px;'>" +
                        "<p style='text-align: center; font-size: 18px; font-weight: bold;'>Dear %s,</p>" +
                        "<p>Your advert has started to be published on our ritzy homes platform.</p>" +
                        "<p>Here are the details of your tour request:</p>" +
                        "<div style='background-color: #6cb533; padding: 10px;'>" +

                        "<p>Tour Date : %s</p>" +
                        "<p>Tour Date : %s</p>" +
                        "</div>" +
                        "<p>Thank you for choosing our platform to list your property. Your advert is now visible to potential buyers and renters.</p>" +
                        "<p>If you have any questions or need further assistance, feel free to reach out to our customer support team.</p>" +
                        "<p style='font-style: italic;'>Best regards,<br>Ritzy Homes</p>" +
                        "</div>" +
                        "</body></html>",
                userFullName,tourDate,tourTime);
    }









}
