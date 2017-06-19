package sokoban;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

/**
 * @author Johann Helbig, Marc Brandt, Albert Renz
 */
public class Level extends Observable implements Serializable {
    private String worldName;
    private int levelNr;
    private String levelName;
    private ArrayList<ArrayList<Character>> level = new ArrayList<>();
    private int maxSpalte = 0;
    private int posZeile;
    private int posSpalte;
    private Stack<Character> moves = new Stack<>();

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
                    if (posS > maxSpalte) {
                        maxSpalte = posS;
                    }
                    posS = 0;
                    break;
                default:
                    break;
            }
        }
        for (ArrayList<Character> ar : level) {
            int temp = ar.size();
            for (int i = 0; i < maxSpalte - temp; i++) {
                ar.add(' ');
            }
        }
    }

    public Stack<Character> getMoves() {
        return moves;
    }

    public int getZeilenAnzahl() {
        return level.size();
    }

    public int getMaxSpalte() {
        return maxSpalte;
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
                moves.push('u');
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile--;
                moves.push('u');
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile - 1, posSpalte).equals('.')) { //über uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile--;
                moves.push('u');
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile--;
                moves.push('u');
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile - 1, posSpalte).equals('$')) { //über uns ist ein Geldsack
            if (getLevel(posZeile - 2, posSpalte).equals(' ')) {    //über dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile - 2, posSpalte).equals('.')) { //über dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile - 1, posSpalte, '*');
                }
            }
        } else if (getLevel(posZeile - 1, posSpalte).equals('*')) { //über uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile - 2, posSpalte).equals(' ')) {    //über dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile - 2, posSpalte).equals('.')) { //über dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile--;
                    moves.push('u');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void moveDown() {
        if (getLevel(posZeile + 1, posSpalte).equals(' ')) {    //unter uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile++;
                moves.push('d');
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile++;
                moves.push('d');
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile + 1, posSpalte).equals('.')) { //unter uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile++;
                moves.push('d');
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile++;
                moves.push('d');
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile + 1, posSpalte).equals('$')) { //unter uns ist ein Geldsack
            if (getLevel(posZeile + 2, posSpalte).equals(' ')) {    //unter dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile + 2, posSpalte).equals('.')) { //unter dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '*');
                }
            }
        } else if (getLevel(posZeile + 1, posSpalte).equals('*')) { //unter uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile + 2, posSpalte).equals(' ')) {    //unter dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile + 2, posSpalte).equals('.')) { //unter dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    moves.push('d');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void moveRight() {
        if (getLevel(posZeile, posSpalte + 1).equals(' ')) {    //rechts neben uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte++;
                moves.push('r');
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte++;
                moves.push('r');
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile, posSpalte + 1).equals('.')) { //rechts neben uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte++;
                moves.push('r');
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte++;
                moves.push('r');
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile, posSpalte + 1).equals('$')) { //rechts neben uns ist ein Geldsack
            if (getLevel(posZeile, posSpalte + 2).equals(' ')) {    //rechts neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte + 2).equals('.')) { //rechts neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '*');
                }
            }
        } else if (getLevel(posZeile, posSpalte + 1).equals('*')) { //rechts neben uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile, posSpalte + 2).equals(' ')) {    //rechts neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte + 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte + 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte + 2).equals('.')) { //rechts neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte + 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    moves.push('r');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void moveLeft() {
        if (getLevel(posZeile, posSpalte - 1).equals(' ')) {    //links neben uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte--;
                moves.push('l');
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte--;
                moves.push('l');
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile, posSpalte - 1).equals('.')) { //links neben uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte--;
                moves.push('l');
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte--;
                moves.push('l');
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile, posSpalte - 1).equals('$')) { //links neben uns ist ein Geldsack
            if (getLevel(posZeile, posSpalte - 2).equals(' ')) {    //links neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte - 2).equals('.')) { //links neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '*');
                }
            }
        } else if (getLevel(posZeile, posSpalte - 1).equals('*')) { //links neben uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile, posSpalte - 2).equals(' ')) {    //links neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte - 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte - 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte - 2).equals('.')) { //links neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte - 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    moves.push('l');
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void undo() {
        if (moves.size() > 0) {
            switch (moves.peek()) {
                case 'u':
                    undoUp();
                    moves.pop();
                    break;
                case 'd':
                    undoDown();
                    moves.pop();
                    break;
                case 'l':
                    undoLeft();
                    moves.pop();
                    break;
                case 'r':
                    undoRight();
                    moves.pop();
                    break;
                default:
                    break;
            }
            setChanged();
            notifyObservers();
        }
    }

    public void undoUp() {

    }

    public void undoDown(){

    }

    public void undoLeft(){

    }

    public void undoRight(){

    }

    public boolean checkSolved() {
        for (ArrayList<Character> ar : level) {
            for (Character c : ar) {
                if (c.equals('.') || c.equals('+')) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        return "Level " + levelNr + ": " + levelName;
    }
}
