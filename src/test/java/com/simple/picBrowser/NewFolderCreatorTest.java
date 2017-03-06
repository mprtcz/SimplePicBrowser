package com.simple.picBrowser;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azet on 2015-10-28.
 */
public class NewFolderCreatorTest {
    private String folderPath = "D:\\JAVA test\\omg.jpg";
    private String expected = "D:\\JAVA test\\ChosenFiles";

    @Test
    public void createNewFolderTest(){
        String result = NewFolderCreator.createSubFolder(new File(folderPath), "");
        assertEquals(result, expected);
    }
}
