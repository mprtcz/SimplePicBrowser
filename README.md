# SimplePicBrowser
Java application which allows user to browse pictures in gif, bmp, png, tiff and jpg formats and to create a custom named 
subdirectory to which files can can be copied. 

Application's UI:
![picture](https://sc-cdn.scaleengine.net/i/bd7435efa73646cb13cb9851bb93462e1.png)


User selects a folder in which he wants to browse pictures by clicking `OPEN` 
 button at the top of the window. Folder is loaded by selecting an opening any file inside it. 
 
 After load, the application displays a list of all files which extensions match an extension checklist at the left side of 
 application's window. At the center of the window, the folder's selected basicImage is displayed.
 
 ![fileInfo](https://sc-cdn.scaleengine.net/i/8d08b3251ea1a31baaa10d3e3b0317b81.png)
   
  
  Above the Extensions Checklist there is a File Information section which displays metadata about currently displayed file:
  
  ![fileInfo](https://sc-cdn.scaleengine.net/i/e2f1bbeaa8cad9dee4ecb70a07c592ad4.png)
  
  
  Bottom of th window contains function buttons and fields providing additional navigation information:
  
  ![fileInfo](https://sc-cdn.scaleengine.net/i/99abe815b80bf1f8dac49dcd620ccfde.png)
  
  * `Pev` and `Next` buttons used for switching between loaded images
  * `Rotate` button - basicImage rotation
  * `Add To Folder` - adds currently displayed basicImage to subfolder
  * `Enter Folder Name` - invokes new window inside which a user types subfolder name. 
  Default name is `ChosenFiles`
  * At the end there is a progress Bar indicating at which position in the folder the displayed basicImage is.
    
  
 Keyboard's arrows can be used to navigate between the folder's images :
 
 * :arrow_down: :arrow_left: list's next basicImage
 * :arrow_up: :arrow_right: previous basicImage
 * `spacebar` rotates basicImage 90 degrees clockwise 



---

To generate a jar file of this project using maven:
  1. after cloning the project, open terminal in the project's main directory and type:

  `mvn package`
  
  2. Next go to generated `target` directory:
  
  `cd target`
  
  3. To run generated jar file enter:
  
  `java -jar SimplePicBrowser.jar`
