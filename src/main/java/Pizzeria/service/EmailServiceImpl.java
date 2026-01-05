package Pizzeria.service;

import Pizzeria.entity.Order;
import Pizzeria.entity.OrderItem;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOrderConfirmation(Order order) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(order.getCustomer().getEmail());
            helper.setSubject("🍕 Potvrdenie objednávky " + order.getCode());

            helper.setText(buildEmailText(order), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Nepodarilo sa odoslať e-mail", e);
        }
    }

    private String buildEmailText(Order order) {

        StringBuilder sb = new StringBuilder();

        sb.append("""
            <h2>Ďakujeme za Vašu objednávku!</h2>
            <p>Objednávka <strong>%s</strong> bola úspešne vytvorená.</p>
            <hr>
            <ul>
        """.formatted(order.getCode()));

        for (OrderItem item : order.getItems()) {
            sb.append("""
                <li>
                    %s (%s) – %d ks – %.2f €
                </li>
            """.formatted(
                    item.getPizzaNameSnapshot(),
                    item.getSizeLabelSnapshot(),
                    item.getQuantity(),
                    item.getUnitPrice() * item.getQuantity()
            ));
        }

        sb.append("""
            </ul>
            <hr>
            <p><strong>Celková suma:</strong> %.2f €</p>
            <p>🍕 Vaša Pizzeria</p>
        """.formatted(order.getTotalPrice()));

        return sb.toString();
    }
}
