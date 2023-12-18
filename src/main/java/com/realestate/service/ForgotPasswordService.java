package com.realestate.service;

import com.realestate.entity.User;
import com.realestate.payload.request.ForgotPasswordRequest;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    public void forgotPasswordUser(ForgotPasswordRequest forgotPasswordRequest) {
        // Kullanıcıyı e-posta adresine göre bul
        Optional<User> optionalUser = userRepository.findByEmail(forgotPasswordRequest.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Reset token oluştur
            String resetToken = generateResetToken();

            // Token'i kullanıcıya kaydet (veritabanına)
            user.setResetToken(resetToken);
            userRepository.save(user);

            // E-posta gönderme işlemi
            sendEmail(forgotPasswordRequest.getEmail(), resetToken);
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
        message.setSubject("Şifre Sıfırlama");
        message.setText("Şifrenizi sıfırlamak için aşağıdaki bağlantıya tıklayın: "
                + "http://example.com/reset-password?token=" + resetToken);

        javaMailSender.send(message);
    }

}
