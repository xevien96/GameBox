package sokoban;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LevelMaker {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir")+"\\src\\sokoban\\Levels\\TextFileLevels");
        int returnVal = jfc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File levelFile = jfc.getSelectedFile();
            makeLevelsFromFile(levelFile);
        }
    }

    public static void makeLevelsFromFile(File levelFile){
        try {
            FileReader input = new FileReader(levelFile);

        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }
}
