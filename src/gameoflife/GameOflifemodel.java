package gameoflife;

import java.util.Observable;

/**
 * @author Johann Helbig, Albert Renz, Marc Brandt
 */

class GameOfLifeModel extends Observable {
    private boolean[][] cells;
    private int rows;
    private int columns;
    private long sleeptime = 250;
    private boolean läuft = false;


    public GameOfLifeModel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new boolean[this.rows][this.columns];
    }

    public GameOfLifeModel(GameOfLifeModel model) {
        rows = model.getRows();
        columns = model.getColumns();
        cells = new boolean[rows][columns];
        for (int i = 0; i < rows; i++)
            cells[i] = model.getCellRow(i).clone();
    }

    public boolean[] getCellRow(int i) {
        return cells[i];
    }

    public boolean getCell(int row, int column) {
        return cells[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public long getSleeptime() {
        return sleeptime;
    }

    public void setSleeptime(long sleeptime) {
        this.sleeptime = sleeptime;
    }

    public void setAlive(int zeile, int spalte) {
        cells[zeile][spalte] = true;
        setChanged();
        notifyObservers();
    }

    public boolean isLäuft() {
        return läuft;
    }

    public void setLäuft(boolean läuft) {
        this.läuft = läuft;
    }

    /**
     * Gibt das Model auf der Konsole aus
     * Wird aber nicht mehr verwendet
     *
     * @return
     */
    public String toString() {
        String out = "";
        for (boolean[] b : cells) {
            out += "\n";
            for (boolean cell : b) {
                if (!cell) {
                    out += "[ ]";
                } else {
                    out += "[o]";
                }
            }
        }
        return out;
    }

    /**
     * Diese Methode verringert die Sleeptime
     */
    public void lowerSleeptime() {
        if (sleeptime >= 1000) {
            sleeptime -= 500;
        } else if (sleeptime > 100 && sleeptime < 1000) {
            sleeptime -= 100;
        }
    }

    /**
     * Diese Methode erhoeht die Sleeptime
     */
    public void higherSleeptime() {
        if (sleeptime >= 1000) {
            sleeptime += 500;
        } else if (sleeptime < 1000) {
            sleeptime += 100;
        }
    }

    /**
     * Ändert den boolean Wert der Zelle, welche uebergeben wird
     *
     * @param row
     * @param column
     */
    public void changeCell(int row, int column) {
        cells[row][column] = !cells[row][column];
        setChanged();
        notifyObservers();
    }

    /**
     * Erstellt  die nächste Generation
     */
    public void generateNextGen() {
        boolean[][] nextGen = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            nextGen[i] = cells[i].clone();
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!cells[i][j] && lifeNeighbors(i, j) == 3) {
                    nextGen[i][j] = true;
                } else if (cells[i][j] && (lifeNeighbors(i, j) < 2 || lifeNeighbors(i, j) > 3)) {
                    nextGen[i][j] = false;
                }
            }
        }
        cells = nextGen;
        setChanged();
        notifyObservers();
    }

    /**
     * Das ganze Fenster wird mit toten Zellen dagestellt
     */
    public void clearAll() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = false;
            }
        }
    }

    /**
     * ueberprueft die Nachbarn einer Zelle und gibt Anzahl der lebendigen Zellen zurueck
     *
     * @param row
     * @param column
     * @return
     */
    private int lifeNeighbors(int row, int column) {
        int out = 0;
        if (cells[row][(column + columns - 1) % columns]) { //teste links
            out++;
        }
        if (cells[row][(column + columns + 1) % columns]) { //teste rechts
            out++;
        }
        if (cells[(row + rows - 1) % rows][column]) { //teste oben
            out++;
        }
        if (cells[(row + rows + 1) % rows][column]) { //teste unten
            out++;
        }
        if (cells[(row + rows - 1) % rows][(column + columns - 1) % columns]) { //teste links oben
            out++;
        }
        if (cells[(row + rows - 1) % rows][(column + columns + 1) % columns]) { //teste rechts oben
            out++;
        }
        if (cells[(row + rows + 1) % rows][(column + columns - 1) % columns]) { //teste links unten
            out++;
        }
        if (cells[(row + rows + 1) % rows][(column + columns + 1) % columns]) { //teste rechts unten
            out++;
        }
        return out;
    }
}
