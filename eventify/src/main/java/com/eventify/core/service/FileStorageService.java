package com.eventify.core.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path root = Paths.get("/app/uploads/events");

    public FileStorageService() throws IOException {
        Files.createDirectories(root);
    }

    public String store(MultipartFile file) throws IOException {
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + ext;

        Path target = root.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/events/" + filename;
    }

    public void delete(String fileUrl) throws IOException {
        if (fileUrl == null || fileUrl.isEmpty()) return;

        String filename = Paths.get(fileUrl).getFileName().toString();
        Path filePath = root.resolve(filename);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            System.out.println("File not found: " + filePath.toAbsolutePath());
        }
    }

}
