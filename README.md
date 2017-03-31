# SimplePicBrowser
Java application which allows user to browse pictures in gif, bmp, png, tiff and jpg formats and to create a custom named 
subdirectory files can can be copied to. 

Application's UI with all controls visible:
![app-ui-on](https://sc-cdn.scaleengine.net/i/de39743f1a7024b5df982baf16b559341.png)

At the left-upper corner of the window, there are two buttons which hide UI elements allowing the user to display image without any control's clutter. The app can be operated from the keyboard if UI is hidden. The only elements that are still visible are the progress bar which indicates file's position on the list of files and the status field which shows whether the file is copied successfully to subfolder after the user hits enter button.

![app-ui-off](https://sc-cdn.scaleengine.net/i/bd9d765f01e80067c04642e78719d1e5.png)

User selects a folder in which he wants to browse pictures by clicking `OPEN` 
 button at the top of the window. Folder is loaded by selecting an opening any file inside it. 
 
 After load, the application displays a list of all files which extensions match an extension checklist at the left side of 
 application's window. The image is displayed as a window's background.
 
 ![fileInfo](https://sc-cdn.scaleengine.net/i/588cbc92c4aaaf54e8487ab9d8476286.png)
   
  
  Above the Extensions Checklist there is a File Information section which displays metadata about currently displayed file:
  
  ![fileInfo](https://sc-cdn.scaleengine.net/i/753247fb22a1741f8dc1bdc105988587.png)
  
  
  Bottom of th window contains function buttons and fields providing additional navigation information:
  
  ![fileInfo](https://sc-cdn.scaleengine.net/i/9a2573b4ee5bb96ce16400e8d00a2344.png)
  
  * `Pev` and `Next` buttons used for switching between loaded images
  * `Rotate` button - image rotation
  * `Add To Folder` - adds currently displayed image to subfolder
  * `Enter Folder Name` - invokes new window inside which a user types subfolder name. 
  Default name is `Subfolder`
  * At the end there is a progress Bar indicating at which position in the file list the displayed image is.
    
  
 Keyboard's arrows can be used to navigate between the folder's images :
 
 * :arrow_right: - list's next image
 * :arrow_left: -  previous image
 * :arrow_down: :arrow_up: -  rotates image 90 degrees clockwise 
 * Enter key - adding displayed image to subfolder



---

To generate a jar file of this project using maven:
  1. after cloning the project, open terminal in the project's main directory and type:

  `mvn package`
  
  2. Next go to generated `target` directory:
  
  `cd target`
  
  3. To run generated jar file enter:
  
  `java -jar SimplePicBrowser.jar`
