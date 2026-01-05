package Pizzeria.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @GetMapping("/uploads/avatars/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getAvatar(@PathVariable String filename)
            throws IOException {

        Path file = Paths.get("uploads/avatars")
                .resolve(filename)
                .normalize();

        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                // jednoduché riešenie – obrázky sa zobrazia správne
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
