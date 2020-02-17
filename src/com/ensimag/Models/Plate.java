package com.ensimag.Models;

import java.util.Map;

/**
 * Created by houdee on 2/17/20.
 */
public class Plate extends Rectangle {
    private int hRest;

    public Plate(int _h, int _w) {
        super(_h, _w);
        this.hRest = _h;
    }

    public void BL(Map<Rectangle, Integer> pieces) {
        int lineH = 0;
        int lineW = 0;
        boolean newLine = true;
        boolean end = false;
        int nbPieces; // nombre de pièces à découper dans la ligne actuelle

        while (hRest > 0 && !end){
            end=true;
            for (Rectangle p : pieces.keySet()) {
                if (p.getH() <= hRest && p.getW() <= this.getW() - lineW && pieces.get(p) > 0) {
                    end=false;
                    if (newLine) {
                        newLine = false;
                        hRest -= p.getH();
                        lineW += p.getW();
                        pieces.put(p, pieces.get(p)-1);
                    }
                    nbPieces = getLineWRest(lineW) / p.getW();
                    if (nbPieces > 0) {
                        lineW += nbPieces * p.getW();
                        pieces.put(p, pieces.get(p) - nbPieces);
                    }
                }
            }
            if (!end) {
                newLine = true;
                lineW = 0;
                lineH = this.getH() - hRest;
            }
        }

    }

    public int getLineWRest(int lineW) {
        return this.getW() - lineW;
    }

}
