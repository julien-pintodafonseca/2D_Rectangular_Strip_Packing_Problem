package com.ensimag.Models;

import java.io.*;
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
        boolean end = false; // vrai si l'on ne peut plus placer de pièces ou que l'on a placer toutes les pièces
        int nbPieces; // nombre de pièces à découper dans la ligne actuelle

        while (hRest > 0 && !end) {
            end=true;
            for (Rectangle p : pieces.keySet()) {
                if (p.getH() <= hRest && p.getW() <= getLineWRest(lineW) && pieces.get(p) > 0) {
                    end=false;
                    if (newLine) {
                        newLine = false;
                        hRest -= p.getH();
                        lineW += p.getW();
                        pieces.put(p, pieces.get(p)-1);
                        System.out.println("Nouvelle ligne, une pièce a été placée !");
                    }
                    nbPieces = getLineWRest(lineW) / p.getW();
                    if (nbPieces > 0) {
                        if (pieces.get(p) - nbPieces > 0) {
                            // On place toutes les pièces que l'on peut placer (nbPieces)
                            lineW += nbPieces * p.getW();
                            pieces.put(p, pieces.get(p) - nbPieces);
                            System.out.println(nbPieces+" ont été découpées (toutes les pièces pouvant etre" +
                                    "placées sur la ligne).");
                        } else {
                            // On place toutes les pièces qu'il nous reste à placer (pieces.get(p))
                            lineW += pieces.get(p) * p.getW();
                            pieces.put(p, 0);
                            System.out.println(pieces.get(p)+" ont été découpées (toutes les pièces restantes).");
                        }
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

    private int getLineWRest(int lineW) {
        return this.getW() - lineW;
    }
}
