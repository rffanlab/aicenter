package io.afu.aicenterapi.utils;



import io.afu.aicenterapi.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;


/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class ImgUtils {

    private static final Logger logger  = LoggerFactory.getLogger(ImgUtils.class);

    public static byte[] scale(Integer targetWidth,byte[] sourceBytes,String suffix) throws BaseException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sourceBytes);
        try {
            BufferedImage image = ImageIO.read(byteArrayInputStream);
            int width = image.getWidth();
            int height = image.getHeight();
            // 根据目标宽度来机选比例
            double percent = targetWidth.doubleValue() / (double) width;
            if (percent>1) {
                percent = 1;
            }
            // 开始根据比例来计算高度
            int targetHeight = (int)(height * percent);
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            resizedImage.getGraphics().drawImage(image, 0, 0, targetWidth, targetHeight, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, suffix, outputStream);

            return outputStream.toByteArray();
        }catch (IOException e) {
            logger.error("图片缩放错误",e);
            throw new BaseException("缩放失败");
        }
    }


    public static boolean isImage(byte[] data) {
        return isJPEG(data) || isPNG(data) || isGIF(data);
    }

    public static String getSuffix(byte[] data) {
        if (isGIF(data)){
            return ".gif";
        }
        if (isJPEG(data)){
            return ".jpeg";
        }
        if (isPNG(data)){
            return ".png";
        }
        return "";
    }

    private static boolean isJPEG(byte[] data) {
        return data.length >= 2 && data[0] == (byte) 0xFF && data[1] == (byte) 0xD8;
    }

    private static boolean isPNG(byte[] data) {
        return data.length >= 8
                && data[0] == (byte) 0x89
                && data[1] == (byte) 0x50 // 'P'
                && data[2] == (byte) 0x4E // 'N'
                && data[3] == (byte) 0x47 // 'G'
                && data[4] == (byte) 0x0D
                && data[5] == (byte) 0x0A
                && data[6] == (byte) 0x1A
                && data[7] == (byte) 0x0A;
    }

    private static boolean isGIF(byte[] data) {
        return data.length >= 6
                && data[0] == (byte) 'G'
                && data[1] == (byte) 'I'
                && data[2] == (byte) 'F'
                && data[3] == (byte) '8'
                && (data[4] == (byte) '7' || data[4] == (byte) '9')
                && data[5] == (byte) 'a';
    }


    public static String getFileSuffix(String pathOrFileName) {
        String suffix = "";
        int index = pathOrFileName.lastIndexOf(".");
        if (index != -1){
            suffix = pathOrFileName.substring(index+1);
        }
        return suffix;
    }

    public static void main(String[] args) throws IOException, BaseException {

        byte[] bytes = scale(800, Files.readAllBytes(new File("D:\\jetbrainstoolbox.png").toPath()),"jpg");
        try (FileOutputStream outputStream = new FileOutputStream("D:\\scaledjetbrainstoolbox.png")) {
            outputStream.write(bytes);
        }
    }




}
