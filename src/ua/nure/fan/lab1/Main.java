package ua.nure.fan;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
//        var sobelFilter = new EdgeDetection();
//        sobelFilter.demo("img.png", "imgOut.png");
//        sobelFilter.demo("wallpaper.png", "wallpaperOut.png");

        var multiThreadedSobelFilter = new MultiThreadedEdgeDetection(16);
        multiThreadedSobelFilter.demo("img.png", "imgOutMulti.png");
//        multiThreadedSobelFilter.demo("wallpaper.png", "wallpaperOutMulti.png");
    }
}

