package com.ensimag.Models;

import java.util.*;

public class CutChecker extends Piece {
    private int lost;
    private Map<Integer, Map<Integer, Plate>> xPieces;
    private Map<Integer, Map<Integer, Plate>> yPieces;
    private List<Integer> xList;
    private List<Integer> yList;

    public CutChecker(int _h, int _w) {
        super(_h, _w);
        this.xPieces = new HashMap<>();
        this.yPieces = new HashMap<>();
        this.xList = new ArrayList<>();
        this.yList = new ArrayList<>();
        this.lost = 0;
    }

    public Map<Integer, Map<Integer, Plate>> getxPieces() {
        return this.xPieces;
    }

    public Map<Integer, Map<Integer, Plate>> getyPieces() {
        return this.yPieces;
    }

    public List<Integer> getxList() {
        return this.xList;
    }

    public List<Integer> getyList() {
        return this.yList;
    }

    public int getLost() {
        return this.lost;
    }

    public void setLost(int _lost) {
        this.lost = _lost;
    }

    public void addLost(int yes) {
        this.lost += yes;
    }

    public void addPiece(PieceWithCoords piece) {
        if (!this.xPieces.containsKey(piece.getX())) {
            this.xPieces.put(piece.getX(), new HashMap<>());
        }
        if (!this.yPieces.containsKey(piece.getY())) {
            this.yPieces.put(piece.getY(), new HashMap<>());
        }

        Map<Integer, Plate> subXPieces = this.xPieces.get(piece.getX());
        if (subXPieces.containsKey(piece.getY())) {
            System.out.println("Erreur deux pièces à la même position - CutPlate.java");
        }
        subXPieces.put(piece.getY(), new Plate(piece.getH(), piece.getW()));
        this.xPieces.put(piece.getX(), subXPieces);
        //System.out.println(this.xPieces.get(piece.getX()));

        Map<Integer, Plate> subYPieces = this.yPieces.get(piece.getY());
        subYPieces.put(piece.getX(), new Plate(piece.getH(), piece.getW()));
        //System.out.println(this.xPieces.get(piece.getX()));

        if (!this.xList.contains(piece.getX())) {
            this.xList.add(piece.getX());
        }
        if (!this.yList.contains(piece.getY())) {
            this.yList.add(piece.getY());
        }
    }

    public boolean suivX(int x, int y, Plate piece) {
        int x_index = this.xList.indexOf(x);
        if (x_index == this.xList.size() - 1) {
            return true;
        } else {
            x_index += 1;
            int x_index_max;
            if (this.xList.contains(x + piece.getW())) {
                x_index_max = this.xList.indexOf(x + piece.getW());
            } else {
                x_index_max = x_index;
                while (x_index_max < this.xList.size() && this.xList.get(x_index_max) < x + piece.getW()) {
                    x_index_max += 1;
                }
                if (x_index_max < this.xList.size()) {
                    x_index_max += 1;
                }
            }
            int y_index = this.yList.indexOf(y) + 1;
            int y_index_max;
            if (this.yList.contains(y + piece.getH())) {
                y_index_max = this.yList.indexOf(y + piece.getH());
            } else {
                y_index_max = y_index;
                while (y_index_max < this.yList.size() && this.yList.get(y_index_max) < y + piece.getH()) {
                    y_index_max += 1;
                }
                if (y_index_max < this.yList.size()) {
                    y_index_max += 1;
                }
            }
            Map<Integer, Plate> subXPiecesY;
            while (x_index < x_index_max) {
                subXPiecesY = this.xPieces.get(this.xList.get(x_index));
                while (y_index < y_index_max) {
                    if (subXPiecesY.get(y_index) != null) {
                        return false;
                    }
                    y_index += 1;
                }
                x_index += 1;
                y_index = this.yList.indexOf(y) + 1;
            }
            return true;
        }
    }

    public int nextX(int x, int y) {
        Map<Integer, Plate> subYPiecesX = this.yPieces.get(y);
        this.xList.indexOf(x);
        int indexNextX = this.xList.indexOf(x) + 1;
        int sizeXList = this.xList.size();
        while (indexNextX < sizeXList && !subYPiecesX.containsKey(this.xList.get(indexNextX))) {
            indexNextX ++;
        }
        if (indexNextX >= sizeXList) {
            return -1;
        } else {
            return this.xList.get(indexNextX);
        }
    }

    public int nextY(int x, int y) {
        Map<Integer, Plate> subXPiecesY = this.xPieces.get(x);
        this.yList.indexOf(y);
        int indexNextY = this.yList.indexOf(y) + 1;
        int sizeYList = this.yList.size();
        while (indexNextY < sizeYList && !subXPiecesY.containsKey(this.yList.get(indexNextY))) {
            indexNextY ++;
        }
        if (indexNextY >= sizeYList) {
            return -1;
        } else {
            return this.yList.get(indexNextY);
        }
    }

    public void sort() {
        Collections.sort(this.xList);
        Collections.sort(this.yList);
    }
}
