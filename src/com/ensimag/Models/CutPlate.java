package com.ensimag.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CutPlate {
    private int lost;
    private Map<Integer, Map<Integer, Plate>> xPieces;
    private Map<Integer, Map<Integer, Plate>> yPieces;
    private ArrayList<Integer> xList;
    private ArrayList<Integer> yList;

    public CutPlate() {
        this.xPieces = new HashMap<>();
        this.yPieces = new HashMap<>();
        this.xList = new ArrayList<>();
        this.yList = new ArrayList<>();
    }

    public void addPiece(PlateWithCoords piece) {
        if(!this.xPieces.containsKey(piece.getX())) {
            this.xPieces.put(piece.getX(), new HashMap<Integer, Plate>());
        }
        if(!this.yPieces.containsKey(piece.getY())) {
            this.yPieces.put(piece.getY(), new HashMap<Integer, Plate>());
        }
        Map<Integer, Plate> subXPieces = this.xPieces.get(piece.getX());
        subXPieces.put(piece.getY(), new Plate(piece.getH(), piece.getW()));
        System.out.println(this.xPieces.get(piece.getX()));
        Map<Integer, Plate> subYPieces = this.xPieces.get(piece.getY());
        subXPieces.put(piece.getX(), new Plate(piece.getH(), piece.getW()));
        System.out.println(this.xPieces.get(piece.getX()));
    }

    public PlateWithCoords nextX(int x, int y, Plate piece) {
        Map<Integer, Plate> subYPiecesX = this.yPieces.get(y);
        this.xList.indexOf(x);
        int indexNextX = this.xList.indexOf(x) + 1;
        int sizeXList = this.xList.size();
        while(indexNextX <= sizeXList && !subYPiecesX.containsKey(this.xList.get(indexNextX))) {
            indexNextX ++;
        }
        if(indexNextX > sizeXList) {
            return null;
        }
        else {
            return new PlateWithCoords(subYPiecesX.get(x), indexNextX, y);
        }
    }

    public PlateWithCoords nextY(int x, int y, Plate piece) {
        Map<Integer, Plate> subXPiecesY = this.xPieces.get(x);
        this.yList.indexOf(y);
        int indexNextY = this.yList.indexOf(y) + 1;
        int sizeYList = this.yList.size();
        while(indexNextY <= sizeYList && !subXPiecesY.containsKey(this.yList.get(indexNextY))) {
            indexNextY ++;
        }
        if(indexNextY > sizeYList) {
            return null;
        }
        else {
            return new PlateWithCoords(subXPiecesY.get(y), x, indexNextY);
        }
    }
}
