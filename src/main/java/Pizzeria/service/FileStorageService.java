package Pizzeria.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Path ROOT = Paths.get("uploads/avatars");

    public String storeProfileImage(MultipartFile file, Integer userId) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Súbor je prázdny");
        }

        // Povolené typy
        List<String> allowedTypes = List.of(
                "image/jpeg",
                "image/png",
                "image/webp"
        );

        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new IllegalArgumentException("Nepovolený typ súboru");
        }

        // Prípona podľa typu
        String extension = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new IllegalStateException("Neznámy typ");
        };

        try {
            // Vytvorenie adresára ak neexistuje
            Files.createDirectories(ROOT);

            // Bezpečný názov súboru
            String filename = "user-" + userId + "-" + UUID.randomUUID() + extension;
            Path target = ROOT.resolve(filename).normalize();

            // Uloženie súboru
            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Do databázy sa uloží iba táto cesta
            return "/uploads/avatars/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Chyba pri ukladaní súboru", e);
        }
    }
}
