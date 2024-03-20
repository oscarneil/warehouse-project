package gjt.usblab.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import gjt.usblab.Main.main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class QRCodeGenerator {
    public static String make(String data){
        // Output file path

        // QR code size (in pixels)
        int size = 300;

        // Create a QRCodeWriter
        Writer writer = new QRCodeWriter();

        try {
            // Generate QR code matrix
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, size, size);

            // Create a buffered image from the QR code matrix
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    int color = bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF; // Black and white colors
                    image.setRGB(x, y, color);
                }
            }

            // Save the QR code image to a file
            File qrCodeFile = new File(main.applicationPath + "/" + main.getInstance().getFilePath()+data+".png");
            ImageIO.write(image, "png", qrCodeFile);
            return data+".png";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}