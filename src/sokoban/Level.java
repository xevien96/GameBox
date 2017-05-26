package sokoban;

import java.io.*;
import java.util.ArrayList;

public class Level implements Serializable {
    private String worldName;
    private int levelNr;
    private String levelName;
    private ArrayList<ArrayList<Character>> level = new ArrayList<>();

    public Level(String worldName, int levelNr, String levelName, String levelString) {
        File directory = new File(System.getProperty("user.dir") + "\\src\\sokoban\\Levels\\" + worldName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.worldName = worldName;
        this.levelNr = levelNr;
        this.levelName = levelName;
        ArrayList<Character> zeile = new ArrayList<>();
        for (int i = 0; i < levelString.length(); i++) {
            switch (levelString.charAt(i)) {
                case ' ':
                    zeile.add(' ');
                    break;
                case '#':
                    zeile.add('#');
                    break;
                case '@':
                    zeile.add('@');
                    break;
                case '$':
                    zeile.add('$');
                    break;
                case '.':
                    zeile.add('.');
                    break;
                case '*':
                    zeile.add('*');
                    break;
                case '+':
                    zeile.add('+');
                    break;
                case 'n':
                    level.add(zeile);
                    zeile = new ArrayList<>();
                    break;
                default:
                    break;
            }
        }
        try {
            FileOutputStream fs = new FileOutputStream(directory + "\\" + String.valueOf(levelNr) + ".ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            fs.flush();
            os.flush();
            os.writeObject(this);
            os.close();
            fs.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
