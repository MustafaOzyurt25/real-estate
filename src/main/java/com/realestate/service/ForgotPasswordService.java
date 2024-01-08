package com.realestate.service;

import com.realestate.entity.User;
import com.realestate.exception.PasswordResetException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.payload.request.ForgotPasswordRequest;
import com.realestate.payload.request.ResetPasswordRequest;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public void forgotPasswordUser(ForgotPasswordRequest forgotPasswordRequest) {

        try{

            User user = userRepository.findByEmailEquals(forgotPasswordRequest.getEmail());

            String resetToken = generateResetToken();

            user.setResetPasswordCode(resetToken);
            userRepository.save(user);

            sendEmail(forgotPasswordRequest.getEmail(), resetToken);

        }catch (RuntimeException e){
            throw new ResourceNotFoundException(ErrorMessages.NOT_VALID_EMAIL);
        }
    }

    private String generateResetToken() {
        // Burada benzersiz bir reset token oluşturabilirsiniz
        // Örneğin, UUID.randomUUID() kullanabilirsiniz.
        return UUID.randomUUID().toString();
    }

    private void sendEmail(String userEmail, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Reset Password");
        message.setText("Click the link below to reset your password: "
                + "http://localhost:3000/reset-password?token=" + resetToken);

        javaMailSender.send(message);
    }

    public void resetPassword(String resetToken , ResetPasswordRequest resetPasswordRequest) throws PasswordResetException {
        // Token doğrulama işlemi
        User user = (User) userRepository.findByResetPasswordCode(resetToken)
                .orElseThrow(() -> new PasswordResetException("Invalid reset token"));

        //Yeni şifrenin encode edilmesi ve kaydedilmesi
        if(resetPasswordRequest.getRetryNewPassword().equals(resetPasswordRequest.getRetryNewPassword())){
            user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            //user.setResetPasswordCode(null); // Token'ı geçersiz kıl
            userRepository.save(user);
        }else{
            throw new PasswordResetException(ErrorMessages.PASSWORD_NOT_MATCH);
        }
    }
}
