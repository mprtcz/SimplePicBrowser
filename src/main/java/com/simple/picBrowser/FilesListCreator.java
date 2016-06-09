package com.simple.picBrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Azet on 2015-10-17.
 */
class FilesListCreator {


    static List<File> getImagePathsList(String filePath){
        File folder = getFolderPath(filePath);
        return getPictureFilesPathsFromFolder(folder.listFiles());
    }

    private static List<File> getPictureFilesPathsFromFolder(File[] listOfFiles){
        List<File> list = new ArrayList<>();

        String[] desiredExtensions = new String[]{".bmp", ".jpg", ".png", ".gif"};

        System.out.println("list of files: " + Arrays.asList(listOfFiles).toString());

        for(File file : listOfFiles){
            if(file.isFile()){
                for(String e : desiredExtensions){
                    if(file.getAbsolutePath().toLowerCase().contains(e)){
                        list.add(file);
                        break;
                    }
                }
            }
        }
        return list;
    }

    public static File getFolderPath(String filePath){
        String[] pathParts = filePath.split("\\\\");
        StringBuilder stringPathParts = new StringBuilder();

        for(int i = 0; i < pathParts.length-1; i++){
            String pathPart = pathParts[i] + "\\\\";
            stringPathParts.append(pathPart);
        }
        return new File(stringPathParts.toString());
    }

}
