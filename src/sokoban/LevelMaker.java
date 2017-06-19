package sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class LevelMaker {
    public static Vector<Level> makeLevelsFromFile(File levelFile) {
        Vector<Level> out = new Vector<>();
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
                    out.add(new Level(worldName, levelNr, levelName, level));
                    level = "";
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return out;
    }
}
