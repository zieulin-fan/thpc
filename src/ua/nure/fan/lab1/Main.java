package ua.nure.fan.lab1;

public class Main {
    public static void main(String[] args) throws Exception {
//        var sobelFilter = new EdgeDetection();
//        sobelFilter.demo("wallpaper.png", "wallpaperOut.png");

        var multiThreadedSobelFilter = new MultiThreadedEdgeDetection(16);
        multiThreadedSobelFilter.demo("wallpaper.png", "wallpaperOutMulti.png");
    }
}

