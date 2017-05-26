package sokoban;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

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
            BufferedReader reader = new BufferedReader(input);
            int levelNr;
            String levelName = "";
            String level = "";
            String temp;
            while((temp = reader.readLine()) != null){
                if(temp.startsWith("Level")){
                    Scanner levelNrScanner = new Scanner(temp);
                    levelNr = levelNrScanner.nextInt();
                    temp = reader.readLine();
                    levelName = temp;
                    while(!(temp = reader.readLine()).equals("")){
                        level += temp+"\n";
                    }

                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
