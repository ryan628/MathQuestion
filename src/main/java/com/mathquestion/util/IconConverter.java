package com.mathquestion.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 图像转 Windows 标准 ICO 与 JavaFX 现代透明 PNG 图标生成工具
 */
public class IconConverter {

    public static void main(String[] args) {
        String inputPath = "C:/Users/Administrator/.gemini/antigravity/brain/34e07ea1-92d0-43c1-ae81-209c978e3795/math_app_icon_1795405617153.jpg";
        if (args.length > 0) {
            inputPath = args[0];
        }

        try {
            File inputFile = new File(inputPath);
            if (!inputFile.exists()) {
                System.err.println("Input image not found: " + inputPath);
                return;
            }

            BufferedImage src = ImageIO.read(inputFile);
            System.out.println("Loaded source image: " + src.getWidth() + "x" + src.getHeight());

            // 1. 去除四周纯白背景，保留圆角柔和 Squircle
            BufferedImage transparent256 = createTransparentSquircle(src, 256);

            // 2. 导出到项目资源目录 app.png
            File resIconDir = new File("src/main/resources/icons");
            resIconDir.mkdirs();
            File resPng = new File(resIconDir, "app.png");
            ImageIO.write(transparent256, "PNG", resPng);
            System.out.println("Saved JavaFX resource icon to: " + resPng.getAbsolutePath());

            // 3. 构建多尺寸图标集合 (256, 128, 64, 48, 32, 16) 用于 Windows .ico
            int[] sizes = {256, 128, 64, 48, 32, 16};
            List<byte[]> pngBytesList = new ArrayList<>();
            for (int s : sizes) {
                BufferedImage scaled = resizeWithHighQuality(transparent256, s, s);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(scaled, "PNG", baos);
                pngBytesList.add(baos.toByteArray());
            }

            // 4. 组装标准 Windows ICO 二进制
            byte[] icoBytes = buildIcoBytes(sizes, pngBytesList);

            File projectIco = new File("d:/projects/MathQuestion/app.ico");
            try (FileOutputStream fos = new FileOutputStream(projectIco)) {
                fos.write(icoBytes);
            }
            System.out.println("Saved Windows ICO to: " + projectIco.getAbsolutePath() + " (size: " + icoBytes.length + " bytes)");

            File exportIco = new File("C:/口算题/app.ico");
            exportIco.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(exportIco)) {
                fos.write(icoBytes);
            }
            System.out.println("Saved backup ICO to: " + exportIco.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将带有纯白底色的图标做高精度圆角平滑透明化裁切
     */
    private static BufferedImage createTransparentSquircle(BufferedImage src, int targetSize) {
        BufferedImage resized = resizeWithHighQuality(src, targetSize, targetSize);
        BufferedImage output = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // 裁切圆角 (根据图标本身的 squircle 比例，弧度约为宽度的 22%)
        int arc = (int) (targetSize * 0.44);
        RoundRectangle2D.Float clipShape = new RoundRectangle2D.Float(0, 0, targetSize, targetSize, arc, arc);
        g2.setClip(clipShape);
        g2.drawImage(resized, 0, 0, null);
        g2.dispose();

        return output;
    }

    /**
     * 高质量平滑缩放
     */
    private static BufferedImage resizeWithHighQuality(BufferedImage src, int width, int height) {
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(src, 0, 0, width, height, null);
        g2.dispose();
        return dest;
    }

    /**
     * 构建标准 Windows ICO 文件二进制数据 (支持嵌入 PNG 格式)
     */
    private static byte[] buildIcoBytes(int[] sizes, List<byte[]> pngBytesList) throws IOException {
        int count = sizes.length;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer header = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
        header.putShort((short) 0);      // Reserved
        header.putShort((short) 1);      // Type: 1 = ICO
        header.putShort((short) count);  // Image Count
        baos.write(header.array());

        // 计算每个条目的偏移量 (头部 6 字节 + 每个条目 16 字节)
        int offset = 6 + (count * 16);

        // 写入每个条目目录
        for (int i = 0; i < count; i++) {
            int s = sizes[i];
            byte[] png = pngBytesList.get(i);

            ByteBuffer entry = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
            entry.put((byte) (s >= 256 ? 0 : s)); // Width: 0 表示 256
            entry.put((byte) (s >= 256 ? 0 : s)); // Height: 0 表示 256
            entry.put((byte) 0);                 // Color count
            entry.put((byte) 0);                 // Reserved
            entry.putShort((short) 1);           // Color planes
            entry.putShort((short) 32);          // Bits per pixel
            entry.putInt(png.length);            // Image size in bytes
            entry.putInt(offset);                // Image offset in file
            baos.write(entry.array());

            offset += png.length;
        }

        // 依次追加每个尺寸的实际 PNG 数据块
        for (byte[] png : pngBytesList) {
            baos.write(png);
        }

        return baos.toByteArray();
    }
}
