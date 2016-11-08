package com.simple.picBrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azet on 2015-10-17.
 */
class FilesListCreator {


    static List<File> getAllFilesPathsList(File selectedFile, List<String> extensionsList) {
        List<File> list = new ArrayList<>();

        File[] filesInParentFolder = selectedFile.getParentFile().listFiles();
        if (filesInParentFolder != null) {
            for (File file : filesInParentFolder) {
                if (file.isFile()) {
                    if (checkFileExtension(file, extensionsList)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    private static boolean checkFileExtension(File file, List<String> extensions) {
        for (String extension : extensions) {
            if (file.getAbsolutePath().toLowerCase().contains("." + extension)) {
                return true;
            }
        }
        return false;
    }
}
