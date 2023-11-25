package ua.nure.fan.lab1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EdgeDetection {
    public static int[] sobel_y = {-1, 0, 1,
            -2, 0, 2,
            -1, 0, 1};
    public static int[] sobel_x = {-1, -2, -1,
            0, 0, 0,
            1, 2, 1};

    public void demo(String initialImagePath, String outputImagePath) throws IOException {
        BufferedImage imgIn = ImageIO.read(new File(initialImagePath));
        var timer = System.currentTimeMillis();
        BufferedImage result = applySobelFilter(imgIn);
        System.out.println("Processing took " + (System.currentTimeMillis() - timer) + "ms");
        ImageIO.write(result, "PNG", new File(outputImagePath));
    }

    private BufferedImage applySobelFilter(BufferedImage img) {
        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage edgesX = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage edgesY = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color black = new Color(0, 0, 0);


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || x == (width - 1) || y == 0 || y == (height - 1)) {
                    int blackRGB = black.getRGB();
                    edgesX.setRGB(x, y, 0xff000000 | (blackRGB << 16) | (blackRGB << 8) | blackRGB);
                    edgesY.setRGB(x, y, 0xff000000 | (blackRGB << 16) | (blackRGB << 8) | blackRGB);
                } else {
                    //get pixel values
                    int[] pixelValues = {
                            img.getRGB(x - 1, y - 1) & 0xff, img.getRGB(x, y - 1) & 0xff, img.getRGB(x + 1, y - 1) & 0xff, //[0][0] [1][0] [2][0]
                            img.getRGB(x - 1, y) & 0xff, img.getRGB(x, y) & 0xff, img.getRGB(x + 1, y) & 0xff,                        //[0][1] [1][1] [2][1]
                            img.getRGB(x - 1, y + 1) & 0xff, img.getRGB(x, y + 1) & 0xff, img.getRGB(x + 1, y + 1) & 0xff}; //[0][2] [1][2] [2][2]

                    //get pixelValues value
                    int valueX = convolution(sobel_x, pixelValues);
                    int valueY = convolution(sobel_y, pixelValues);
                    edgesX.setRGB(x, y, 0xff000000 | (valueX << 16) | (valueX << 8) | valueX);
                    edgesY.setRGB(x, y, 0xff000000 | (valueY << 16) | (valueY << 8) | valueY);
                }
                int outputValue = Math.abs(edgesX.getRGB(x, y) & 0xff) + Math.abs(edgesY.getRGB(x, y) & 0xff);
                result.setRGB(x, y, 0xff000000 | (outputValue << 16) | (outputValue << 8) | outputValue);
            }
        }
        return result;
    }

    private int convolution(int[] kernel, int[] pixel) {
        int result = 0;
        //get Gx/Gy by multiplying each filter value by a pixel value
        // and then summing the products together
        for (int i = 0; i < pixel.length; i++) {
            result += kernel[i] * pixel[i];
        }
        return (int) (Math.abs(result)/4);
    }
}
