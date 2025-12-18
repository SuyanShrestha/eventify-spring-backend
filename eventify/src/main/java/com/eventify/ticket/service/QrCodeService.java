package com.eventify.ticket.service;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.eventify.ticket.model.Ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeService {

    private static final String QR_DIR = "uploads/qr_codes/";

    @PostConstruct
    void init() throws IOException {
        Files.createDirectories(Paths.get(QR_DIR));
    }

    public String generateQrCodeData(Ticket ticket) {
        String raw = ticket.getId() + ":" +
                     ticket.getTicketCode() + ":" +
                     ticket.getEvent().getId() + ":" +
                     ticket.getUser().getId() + ":" +
                     UUID.randomUUID();

        return sha256(raw);
    }

    public String generateQrImage(String qrCodeData, String ticketCode) {
        try {
            int size = 300;

            BitMatrix matrix = new QRCodeWriter()
                .encode(qrCodeData, BarcodeFormat.QR_CODE, size, size);

            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            String filename = "QR_" + ticketCode + ".png";
            Path path = Paths.get(QR_DIR, filename);

            ImageIO.write(image, "PNG", path.toFile());

            return path.toString();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate QR image", e);
        }
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}