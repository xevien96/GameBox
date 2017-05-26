package sokoban;

import java.io.Serializable;
import java.util.List;

public class Level implements Serializable{
    private String worldName;
    private int levelNr;
    private String levelName;
    private List<List<Character>> level;

    public Level(String worldName, int levelNr, String levelName, String levelString){
        this.worldName = worldName;
        this.levelNr = levelNr;
        this.levelName = levelName;

    }
}
