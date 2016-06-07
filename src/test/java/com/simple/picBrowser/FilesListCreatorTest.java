package com.simple.picBrowser;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azet on 2015-10-17.
 */
public class FilesListCreatorTest {
    String path = "C:\\Users\\Azet\\Documents\\BitbucketREPO\\test\\SimplePicBrowser\\src\\test\\resources\\1.txt";
    List<String> expectedList = new ArrayList<String>();
    {
        expectedList.add("C:\\Users\\Azet\\Documents\\BitbucketREPO\\test\\SimplePicBrowser\\src\\test\\resources\\2.bmp");
        expectedList.add("C:\\Users\\Azet\\Documents\\BitbucketREPO\\test\\SimplePicBrowser\\src\\test\\resources\\4.png");
    }
    @Test
    public void getPathListTest(){
        FilesListCreator files = new FilesListCreator();
        List<File> result = files.getPathsList(path);
        System.out.println(result.toString());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(expectedList.get(i), result.get(i).getAbsolutePath());
        }
    }
}
