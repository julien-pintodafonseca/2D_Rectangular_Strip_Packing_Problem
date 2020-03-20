package com.ensimag.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CutPlate extends Rectangle {
    private int lost;
    private Map<Integer, Map<Integer, Plate>> xPieces;
    private Map<Integer, Map<Integer, Plate>> yPieces;
    private ArrayList<Integer> xList;
    private ArrayList<Integer> yList;

    public CutPlate(int _h, int _w) {
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

    public int getLost() {
        return this.lost;
    }
    public void setLost(int _lost) {
        this.lost = _lost;
    }

    public void addLost(int _lost) {
        this.lost += _lost;
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

        //compléter arrayList
    }

    public boolean nextX(int x, int y, Plate piece) {
        int x_index = this.xList.indexOf(x);
        if (x_index == this.xList.size() - 1) {
            return true;
        } else {
            Map<Integer, Plate> subXPiecesY = this.xPieces.get(x_index + 1);
            int y_index = this.yList.indexOf(y);
            int y_index_max = this.yList.indexOf(y + piece.getH());
            Plate p_prec;
            while (y_index < y_index_max) {
                p_prec = subXPiecesY.get(y_index);
                if (p_prec != null) {
                        return false;
                }
                y_index += 1;
            }
            return true;
        }
    }
}
