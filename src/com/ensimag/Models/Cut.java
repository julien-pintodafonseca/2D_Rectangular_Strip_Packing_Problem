package com.ensimag.Models;

import java.util.ArrayList;
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
        return lost;
    }

    public List<PlateWithCoords> getInfo() {
        return info;
    }

    public void addInfo(PlateWithCoords newInfo) {
        this.info.add(newInfo);
    }

    public List<String> toString(Map<Rectangle, Integer> pieces){
        List<String> endResult = new ArrayList<>();
        String information = "Plaque 0 :";
        int xCurrent=-1;
        for(PlateWithCoords plate:this.info) {
            if(plate.getX()>xCurrent) {
                endResult.add(information);
                endResult.add("LS=" + plate.getX());
                xCurrent = plate.getX();
                information = "";
            }
            information = information + plate.getY() + " " + plate.getH() + " " + plate.getW()+", ";
        }
        endResult.add(information);
        endResult.add("Pièces restantes à couper :");
        information = "";
        for(Rectangle p : pieces.keySet()) {
            if (pieces.get(p) > 0) {
                for (int i = 0; i < pieces.get(p); i++) {
                    information = information + p.getH() + " " + p.getW() + ", ";
                }
            }
        }
        if(information.equals("")) {
            information = "Aucune.";
        }
        endResult.add(information);
        endResult.add("Chutes :");
        endResult.add("" + this.getLost());
        return endResult;
    }

    public void fusion(Cut toFusion){
        this.info.addAll(toFusion.getInfo());
        this.lost = this.lost + toFusion.getLost();
    }
}
