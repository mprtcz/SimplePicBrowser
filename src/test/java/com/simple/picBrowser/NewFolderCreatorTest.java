package com.simple.picBrowser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azet on 2015-10-28.
 */
public class NewFolderCreatorTest {
    String folderPath = "D:\\JAVA heheszki\\omg.jpg";
    String expected = "D:\\JAVA heheszki\\ChosenFiles";

    @Test
    public void createNewFolderTest(){
        NewFolderCreator folder = new NewFolderCreator();
        String result = folder.createNewFolder(folderPath);
        //System.out.println(result);
        assertEquals(result, expected);
    }
}
