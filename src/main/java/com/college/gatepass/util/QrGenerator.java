package com.college.gatepass.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class QrGenerator {

    public byte[] generateQrImage(String data) {
        try {
            BitMatrix matrix = new QRCodeWriter()
                    .encode(data, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("QR generation failed");
        }
    }
}

