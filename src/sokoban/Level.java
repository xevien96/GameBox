package sokoban;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

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
    private boolean solved = false;

    private Level previous;

    public Level(String worldName, int levelNr, String levelName, String levelString) {
        this.worldName = worldName;
        this.levelNr = levelNr;
        this.levelName = levelName;
        int posZ = 0;
        int posS = 0;

        ArrayList<Character> zeile = new ArrayList<>();
        for (int i = 0; i < levelString.length(); i++) {    //Level in einen String einlesen
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

    public Level(Level previous) {
        worldName = previous.worldName;
        levelNr = previous.levelNr;
        levelName = previous.levelName;
        for (ArrayList<Character> zeile : previous.level) {
            level.add((ArrayList<Character>) zeile.clone());
        }
        maxSpalte = previous.maxSpalte;
        posZeile = previous.posZeile;
        posSpalte = previous.posSpalte;
    }

    public void setPrevious(Level previous) {
        if (this.previous == null) {
            this.previous = previous;
        } else {
            previous.previous = this.previous;
            this.previous = previous;
        }
    }

    public int getLevelNr() {
        return levelNr;
    }

    public String getWorldName() {
        return worldName;
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

    public void move(Character move) {
        switch (move) {
            case 'u':
                moveUp();
                checkSolved();
                break;
            case 'd':
                moveDown();
                checkSolved();
                break;
            case 'l':
                moveLeft();
                checkSolved();
                break;
            case 'r':
                moveRight();
                checkSolved();
                break;
            default:
                break;
        }
    }   //Switch case für move

    private void moveUp() {
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
        setChanged();
        notifyObservers();
    }

    private void moveDown() {
        if (getLevel(posZeile + 1, posSpalte).equals(' ')) {    //unter uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile++;
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile++;
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile + 1, posSpalte).equals('.')) { //unter uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posZeile++;
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posZeile++;
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile + 1, posSpalte).equals('$')) { //unter uns ist ein Geldsack
            if (getLevel(posZeile + 2, posSpalte).equals(' ')) {    //unter dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile + 2, posSpalte).equals('.')) { //unter dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile + 1, posSpalte, '*');
                }
            }
        } else if (getLevel(posZeile + 1, posSpalte).equals('*')) { //unter uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile + 2, posSpalte).equals(' ')) {    //unter dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '$');
                }

            } else if (getLevel(posZeile + 2, posSpalte).equals('.')) { //unter dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posZeile++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    private void moveRight() {
        if (getLevel(posZeile, posSpalte + 1).equals(' ')) {    //rechts neben uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte++;
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte++;
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile, posSpalte + 1).equals('.')) { //rechts neben uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte++;
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte++;
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile, posSpalte + 1).equals('$')) { //rechts neben uns ist ein Geldsack
            if (getLevel(posZeile, posSpalte + 2).equals(' ')) {    //rechts neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte + 2).equals('.')) { //rechts neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte + 1, '*');
                }
            }
        } else if (getLevel(posZeile, posSpalte + 1).equals('*')) { //rechts neben uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile, posSpalte + 2).equals(' ')) {    //rechts neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte + 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte + 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte + 2).equals('.')) { //rechts neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte + 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte++;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile + 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    private void moveLeft() {
        if (getLevel(posZeile, posSpalte - 1).equals(' ')) {    //links neben uns ist frei
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte--;
                setLevel(posZeile, posSpalte, '@');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte--;
                setLevel(posZeile, posSpalte, '@');
            }

        } else if (getLevel(posZeile, posSpalte - 1).equals('.')) { //links neben uns ist ein Ziel
            if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                setLevel(posZeile, posSpalte, ' ');
                posSpalte--;
                setLevel(posZeile, posSpalte, '+');
            } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                setLevel(posZeile, posSpalte, '.');
                posSpalte--;
                setLevel(posZeile, posSpalte, '+');
            }

        } else if (getLevel(posZeile, posSpalte - 1).equals('$')) { //links neben uns ist ein Geldsack
            if (getLevel(posZeile, posSpalte - 2).equals(' ')) {    //links neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte - 2).equals('.')) { //links neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '@');
                    setLevel(posZeile, posSpalte - 1, '*');
                }
            }
        } else if (getLevel(posZeile, posSpalte - 1).equals('*')) { //links neben uns befindet sich ein Geldsack auf einem Ziel
            if (getLevel(posZeile, posSpalte - 2).equals(' ')) {    //links neben dem Geldsack ist ein freies Feld
                if (getLevel(posZeile, posSpalte).equals('@')) {    //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte - 1, '$');
                } else if (getLevel(posZeile, posSpalte).equals('+')) { //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte - 1, '$');
                }

            } else if (getLevel(posZeile, posSpalte - 2).equals('.')) { //links neben dem Geldsack ist ein Ziel
                if (getLevel(posZeile, posSpalte).equals('@')) {  //wir befinden uns auf einem leeren Feld
                    setLevel(posZeile, posSpalte, ' ');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile, posSpalte - 1, '*');
                } else if (getLevel(posZeile, posSpalte).equals('+')) {  //wir befinden uns auf einem Ziel
                    setLevel(posZeile, posSpalte, '.');
                    posSpalte--;
                    setLevel(posZeile, posSpalte, '+');
                    setLevel(posZeile - 1, posSpalte, '*');
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void undo() {
        if (previous != null) {
            level = previous.level;
            posZeile = previous.posZeile;
            posSpalte = previous.posSpalte;
            if (previous.previous != null) {
                previous = previous.previous;
            } else {
                previous = null;
            }
            setChanged();
            notifyObservers();
        }
    }

    public void checkSolved() {
        int counter = 0;
        for (ArrayList<Character> ar : level) {
            for (Character c : ar) {
                if (c.equals('.') || c.equals('+')) {
                    counter++;
                }
            }
        }
        if (counter < 1) solved = true;
    }

    public boolean isSolved() {
        return solved;
    }

    public String toString() {
        return "Level " + levelNr + ": " + levelName;
    }
}
