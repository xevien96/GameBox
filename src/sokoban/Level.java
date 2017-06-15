package sokoban;

import java.io.*;
import java.util.ArrayList;

public class Level implements Serializable {
    private String worldName;
    private int levelNr;
    private String levelName;
    private ArrayList<ArrayList<Character>> level = new ArrayList<>();
    private int posZeile;
    private int posSpalte;

    public Level(String worldName, int levelNr, String levelName, String levelString) {
        this.worldName = worldName;
        this.levelNr = levelNr;
        this.levelName = levelName;
        int posZ = 0;
        int posS = 0;
        ArrayList<Character> zeile = new ArrayList<>();
        for (int i = 0; i < levelString.length(); i++) {
            switch (levelString.charAt(i)) {
                case ' ':
                    zeile.add(' ');
                    posS++;
                    break;
                case '#':
                    zeile.add('#');
                    posS++;
                    break;
                case '@':
                    zeile.add('@');
                    posZeile = posZ;
                    posSpalte = posS;
                    break;
                case '$':
                    zeile.add('$');
                    posS++;
                    break;
                case '.':
                    zeile.add('.');
                    posS++;
                    break;
                case '*':
                    zeile.add('*');
                    posS++;
                    break;
                case '+':
                    zeile.add('+');
                    posZeile = posZ;
                    posSpalte = posS;
                    break;
                case 'n':
                    level.add(zeile);
                    zeile = new ArrayList<>();
                    posZ++;
                    posS = 0;
                    break;
                default:
                    break;
            }
        }
    }

    public Character getLevel(int zeile, int spalte) {
        return level.get(zeile).get(spalte);
    }

    public void setLevel(int zeile, int spalte, Character c) {
        level.get(zeile).set(spalte, c);
    }

    public void moveUp() {
        if (getLevel(posZeile - 1, posSpalte).equals(' ')) {    //über uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile--;
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile--;
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile - 1, posSpalte).equals('.')) { //über uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile--;
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile--;
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile - 1, posSpalte).equals('$')) { //über uns ist ein Geldsack
            if (getLevel(posZeile - 2, posSpalte).equals(' ')) {    //über dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile - 2, posSpalte).equals('.')) { //über dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '*');
                }
            }
        } else if (getLevel(posZeile - 1, posSpalte).equals('*')) { //über uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile - 2, posSpalte).equals(' ')) {    //über dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile - 2, posSpalte).equals('.')) { //über dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '*');
                }
            }
        }
    }

    public void moveDown() {

    }

    public void moveLeft() {

    }

    public void moveRight() {

    }
}
