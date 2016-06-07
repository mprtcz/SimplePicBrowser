package com.simple.picBrowser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Azet on 2015-10-28.
 */
public class NewFolderCreator {

    public static String createNewFolder(String filePath){
        String[] pathParts = filePath.split("\\\\");
        StringBuilder stringPathParts = new StringBuilder();
        for(int i = 0; i < pathParts.length-1; i++){
            String pathPart = pathParts[i] + "\\\\";
            stringPathParts.append(pathPart);
        }
        stringPathParts.append("ChosenFiles");

        boolean isSuccess = false;
        Path path = Paths.get(stringPathParts.toString());
        if(!Files.exists(path)){
            isSuccess = (new File(stringPathParts.toString())).mkdirs();
        }
        return path.toString();
    }
}
