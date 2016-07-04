package com.simple.picBrowser;

import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Azet on 2015-10-18.
 */
class ImagePropertiesParser {

    static String parseImageProperties(File file) throws MalformedURLException {
        Image img = new Image(file.toURI().toURL().toString());
        StringBuilder output = new StringBuilder();

        String ratioString = getTwoDecimalPlacesFromDouble(img.getHeight()/img.getWidth());
        output.append("Height: ").append(img.getHeight())
                .append("\nWidth: ")
                .append(img.getWidth())
                .append("\nIs smooth: ")
                .append(img.isSmooth())
                .append("\nRatio: ")
                .append(ratioString);

        String sizeString = getTwoDecimalPlacesFromDouble((double)file.length()/1048576);
        output.append("\nSize: ")
                .append(sizeString)
                .append(" MB");


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        output.append("\nLast modified: \n")
                .append(sdf.format(file.lastModified()));

        return  output.toString();
    }

    private static String getTwoDecimalPlacesFromDouble(double number){
        String value = "???";
        try{
            value = new DecimalFormat("##.##").format(number);
        } catch (NumberFormatException ex){
            ex.printStackTrace();
        }

        return value;
    }
}
