package sokoban;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelMaker {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir") + "\\src\\sokoban\\Levels\\TextFileLevels");
        int returnVal = jfc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File levelFile = jfc.getSelectedFile();
            makeLevelsFromFile(levelFile);
        }
    }

    public static void makeLevelsFromFile(File levelFile) {
        try {
            FileReader input = new FileReader(levelFile);
            BufferedReader reader = new BufferedReader(input);
            String worldName = levelFile.getName().replace(".txt", "");
            int levelNr;
            String levelName = "";
            String level = "";
            String temp;
            while ((temp = reader.readLine()) != null) {
                if (temp.startsWith("Level")) {
                    levelNr = Integer.parseInt(temp.replaceAll("[\\D]", ""));
                    temp = reader.readLine();
                    if (temp.startsWith("'")) {
                        levelName = temp.replaceAll("[']", "");
                        temp = reader.readLine();
                    }
                    while (!temp.equals("")) {
                        level += temp + "n";
                        temp = reader.readLine();
                    }
                    new Level(worldName, levelNr, levelName, level);
                    level = "";
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
