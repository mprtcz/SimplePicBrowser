package com.simple.picBrowser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Azet on 2015-10-28.
 */
class NewFolderCreator {

    static String createNewFolder(String filePath){
        File folderPath = FilesListCreator.getFolderPath(filePath);
        String folderPathString = folderPath.getAbsolutePath();
        folderPathString += "\\ChosenFiles";
        System.out.println("folderPathString = " + folderPathString);

        Path path = Paths.get(folderPathString);
        if(!Files.exists(path)){
            new File(folderPathString).mkdirs();
        }
        return folderPathString;
    }
}
