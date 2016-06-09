package com.simple.picBrowser;

import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Azet on 2015-10-18.
 */
public class ImagePropertiesParser {

    public static String parseImageProperties(File file) throws MalformedURLException {
        Image img = new Image(file.toURI().toURL().toString());
        StringBuilder output = new StringBuilder();
        output.append("Height: " +img.getHeight() +"\nWidth: " +img.getWidth() +"\nIs smooth: " +img.isSmooth()
                +"\nRatio: " +img.getHeight()/img.getWidth());

        double size = (double)file.length()/1048576;
        try {
            String value = new DecimalFormat("##.##").format(size);
            output.append("\nSize: " + value + " MB");
        } catch (NumberFormatException e){
            output.append("\nSize: " + size + " MB");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        output.append("\nLast modified: \n" + sdf.format(file.lastModified()));
        return  output.toString();
    }
}
