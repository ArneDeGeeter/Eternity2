package UI;

import com.google.common.base.Strings;
import eternity2.Game;
import eternity2.WrongLengthException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static File pieces = new File("pieces.png");
    public static BufferedImage in;

    static {
        try {
            in = ImageIO.read(pieces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int y = in.getWidth() / 8;
    public static int x = in.getHeight() / 3;
    public static HashMap<Character, int[][]> map = new HashMap();
    public static ArrayList<Character> list = new ArrayList<>(Arrays.asList('X', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'));

    public static void main(String[] args) {
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i),img2matrix(in,i));
        }
        makeImage(img2matrix(in, list.size()-1));
        try {
            Game g=Game.generateFromFile(new File("E:\\school\\OS1\\Labos\\Eternity2\\Output\\16\\26\\1546034473735_26.txt"));
            g.printImage();
        } catch (WrongLengthException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static int[][] img2matrix(BufferedImage bi, int number) {
        int[][] c = new int[bi.getHeight() / 3][bi.getWidth() / 4];
        for (int i = x*(number%4); i < (number%4+1) * x; i++) {
            for (int j = (number/4)*y; j < (number/4+1) * y; j++) {
                c[i % x][j % y] = (bi.getRGB(i, j));
            }
        }
        return c;
    }

    public static void makeImage(int[][] c) {
        BufferedImage image = new BufferedImage(c.length, c[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                int a = c[i][j];
                Color newColor = new Color(a);
                image.setRGB(i, j, newColor.getRGB());
            }
        }
        File output = new File("GrayScale.jpg");
        try {
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
