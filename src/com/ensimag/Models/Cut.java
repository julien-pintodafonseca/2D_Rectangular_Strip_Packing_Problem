package com.ensimag.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by solokwal on 2/20/20.
 */
public class Cut {
    private int lost;
    private List<PlateWithCoords> info;

    public Cut(int _lost, PlateWithCoords _info) {
        this.lost = _lost;
        this.info = new ArrayList<>();
        if(_info != null) {
            this.info.add(_info);
        }
    }
    public Cut() {
        this.lost = 0;
        this.info = new ArrayList<>();
    }

    public Cut(Cut clone) {
        this.lost = clone.getLost();
        this.info = new ArrayList<>();
        this.info.addAll(clone.getInfo());
    }

    public int getLost() {
        return this.lost;
    }

    public void addLost(int yes) { this.lost += yes; }

    public List<PlateWithCoords> getInfo() {
        return this.info;
    }

    public void addInfo(PlateWithCoords newInfo) {
        this.info.add(newInfo);
    }

    public void setInfo(List<PlateWithCoords> info) { this.info = info; }

    public List<String> toString(Map<Rectangle, Integer> pieces, int plateNumber, int previousLost){
        List<String> endResult = new ArrayList<>(toStringLimited(pieces, plateNumber));
        endResult.add("Pièces restantes à couper :");
        String information = "";
        for (Rectangle p : pieces.keySet()) {
            if (pieces.get(p) > 0) {
                for (int i = 0; i < pieces.get(p); i++) {
                    information = information + p.getH() + " " + p.getW() + ", ";
                }
            }
        }
        if (information.equals("")) {
            information = "Aucune.";
        }
        endResult.add(information);
        endResult.add("Chutes :");
        endResult.add("" + (this.getLost() + previousLost));
        return endResult;
    }

    public List<String> toStringLimited(Map<Rectangle, Integer> pieces, int plateNumber){
        List<String> endResult = new ArrayList<>();
        if (this.getLost() == 0) {
            endResult.add("Plaque " + plateNumber + " :");
            endResult.add("Pas utilisée.");
        } else {
            String information = "Plaque " + plateNumber + " :";
            int yCurrent = -1;
            for (PlateWithCoords plate : this.info) {
                if (plate.getY() > yCurrent) {
                    endResult.add(information);
                    endResult.add("LS=" + plate.getY());
                    yCurrent = plate.getY();
                    information = "";
                }
                information = information + plate.getX() + " " + plate.getH() + " " + plate.getW() + ", ";
            }
            endResult.add(information);
        }
        return endResult;
    }

    public void fusion(Cut toFusion){
        this.info.addAll(toFusion.getInfo());
        this.lost = this.lost + toFusion.getLost();
    }
}
