package com.simple.picBrowser;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azet on 2015-10-28.
 */
public class NewFolderCreatorTest {
    String folderPath = "D:\\JAVA test\\omg.jpg";
    String expected = "D:\\JAVA test\\ChosenFiles";

    @Test
    public void createNewFolderTest(){
        NewFolderCreator folder = new NewFolderCreator();
        String result = folder.createSubFolder(new File(folderPath), "");
        assertEquals(result, expected);
    }
}
