package com.simple.picBrowser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Azet on 2015-10-28.
 */
class NewFolderCreator {

    private static final String DEFAULT_FOLDER_NAME = "ChosenFiles";

    static String createSubFolder(File file, String folderName){
        if(folderName==null || folderName.equals("")) {
            folderName = DEFAULT_FOLDER_NAME;
        }
        StringBuilder stringBuilder = new StringBuilder(file.getParent());
        stringBuilder.append("\\\\")
                .append(folderName);
        Path path = Paths.get(stringBuilder.toString());
        if(!Files.exists(path)){
            new File(stringBuilder.toString()).mkdirs();
        }
        return path.toString();
    }
}
