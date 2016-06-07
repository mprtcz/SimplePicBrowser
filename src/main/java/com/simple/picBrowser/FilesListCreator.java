package com.simple.picBrowser;

import javax.naming.ldap.Control;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azet on 2015-10-17.
 */
public class FilesListCreator {


    public static List<File> getPathsList(String filePath){
        List<File> list = new ArrayList<File>();

        String[] pathParts = filePath.split("\\\\");
        StringBuilder stringPathParts = new StringBuilder();
        for(int i = 0; i < pathParts.length-1; i++){
            String pathPart = pathParts[i] + "\\\\";
            stringPathParts.append(pathPart);

        }
        File folder = new File(stringPathParts.toString());
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles){
            if(file.isFile()){
                if(file.getAbsolutePath().contains("bmp") || file.getAbsolutePath().contains("jpg")
                        ||file.getAbsolutePath().contains("png") ||file.getAbsolutePath().contains("gif")){
                    list.add(file);
                }
            }
        }
        return list;
    }

}
