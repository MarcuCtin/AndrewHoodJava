package Game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
public class LoadSave {
    public static final String PLAYER_ATLAS = "archerSheet.png";
    public static final String LEVEL_ONE_ATLAS = "lvl_one_sprites_terrain.png";
    public static final String MENU_BUTTONS = "button_sheet.png";
    //public static final String LEVEL_ONE_METADATA = "1.png";
    public static final String LEVEL_ONE_METADATA = "1.png";

    public static final String MAIN_MENU_BG = "menu_background.png";
    public static final String PAUSE_BG = "lvl1_bg.png";
     public static final String PLAYING_BG = "lvl1_bg.png";
     public static final String PLAYING_BG2 = "lvl1_bg.png";
    public static final String PAUSE_MENU_BG = "pause_bg.png";
    public static final String TREES = "trees.png";
    public static final String HEALTH_BAR = "HealthBar.png";
    public static final String GHOUL_SPRITE = "ghoul2.png";
    public static final String SKELETON_SPRITE = "skeleton.png";
    public static final String PAUSE_BTN = "PauseBtn.png";
    public static final String SETTINGS_BTN = "SettingsBtn.png";
    public static final String HOME_BTN = "HomeBtn.png";
    public static final String RESUME_BTN = "ExitBtn.png";
//    public static final String EXIT_PAUSE_BTN = "ExitBtn.png";
    public static final String RESTART_BTN = "RestartLvlBtn.png";
    public static final String COMPLETED_LVL_IMG = "LvlCompleted.png";
    public static final String NEXTLVL_BTN = "NextLvlBtn.png";
    public static final String COIN = "coin.png";
    public static final String STAR = "Star.png";
    public static BufferedImage LoadImage(String filename){
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/res/sprites/"+filename);
        try{

            image = ImageIO.read(is);
        }
        catch(IOException e){
            e.printStackTrace();
        } finally{
            try{
                if(is != null)
                    is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
    public static BufferedImage[] GetAllLevels(){
        URL url = LoadSave.class.getResource("/res/lvls");
        System.out.println(url);
        File file = null;
        try{
            file = new File(url.toURI());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];
        for(int i = 0 ; i<filesSorted.length;i++)
            for (int j=0;j<files.length;j++){
                if(files[j].getName().equals(""+(i+1)+".png"))
                    filesSorted[i] = files[j];
            }
        BufferedImage[] images = new BufferedImage[filesSorted.length];
        for(int i = 0;i<filesSorted.length;i++){
            try{
                images[i] = ImageIO.read(filesSorted[i]);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return images;
    }


    //1 tile = 1 pixel in metadata file
    //getRed returneaza valoarea red din rgb care va fi practic = spriteId din sheet

}
